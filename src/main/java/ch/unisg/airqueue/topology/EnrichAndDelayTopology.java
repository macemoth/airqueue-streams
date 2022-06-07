package ch.unisg.airqueue.topology;

import ch.unisg.airqueue.aggregates.Average;
import ch.unisg.airqueue.extractor.FlightTimestampExtractor;
import ch.unisg.airqueue.model.*;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;

import java.time.Duration;

public class EnrichAndDelayTopology {

        public static Topology buildTopology() {
                StreamsBuilder builder = new StreamsBuilder();

                KStream<String, Flight> flights = builder
                                .stream("flights", Consumed.with(Serdes.ByteArray(), JsonSerdes.Flight())
                                                .withTimestampExtractor(new FlightTimestampExtractor()))
                                // stream is unkeyed, we select airline as key to meet co-partitioning
                                // requirements
                                .selectKey((k, v) -> v.getAirline());

                // Airlines has IATA code as key (same as airline in flight)
                KTable<String, Airline> airlines = builder.table("airlines",
                                Consumed.with(Serdes.String(), JsonSerdes.Airline())); // Deliberately used JsonSerdes, as AvroSerdes.get(Airline.class)) throws error

                // Airports has IATA code as key
                GlobalKTable<String, Airport> airports = builder.globalTable("airports",
                                Consumed.with(Serdes.String(), JsonSerdes.Airport()));

                // Join Flights with Airlines
                Joined<String, Flight, Airline> flightAirlineJoined = Joined.with(Serdes.String(), JsonSerdes.Flight(),
                                JsonSerdes.Airline());

                ValueJoiner<Flight, Airline, FlightWithAirline> flightAirlineJoiner = (flight,
                                airline) -> new FlightWithAirline(flight, airline);

                KStream<String, FlightWithAirline> flightsWithAirlines = flights.join(airlines, flightAirlineJoiner,
                                flightAirlineJoined);

                // Create mapper to prevent rekeying for next two joins (therefore we use a
                // GlobalKTable)
                KeyValueMapper<String, FlightWithAirline, String> originAirportMapper = (leftKey,
                                flightWithAirline) -> {
                        return flightWithAirline.getFlight().getOriginAirport();
                };

                // Join with origin Airport
                ValueJoiner<FlightWithAirline, Airport, FlightWithOriginAirport> originAirportJoiner = (
                                flightWithAirline, airport) -> new FlightWithOriginAirport(
                                                flightWithAirline.getFlight(),
                                                flightWithAirline.getAirline(),
                                                airport);

                KStream<String, FlightWithOriginAirport> flightsWithOriginAirport = flightsWithAirlines.join(airports,
                                originAirportMapper, originAirportJoiner);

                // Join with destination airport to create final FlightEnriched in the same
                // manner as with origin airport
                KeyValueMapper<String, FlightWithOriginAirport, String> destinationAirportMapper = (leftKey,
                                flightWithAirline) -> {
                        return flightWithAirline.getFlight().getDestinationAirport();
                };

                ValueJoiner<FlightWithOriginAirport, Airport, FlightEnriched> enrichedFlightJoiner = (
                                flightWithOriginAirport, airport) -> new FlightEnriched(
                                                flightWithOriginAirport.getFlight(),
                                                flightWithOriginAirport.getAirline(),
                                                flightWithOriginAirport.getOriginAirport(),
                                                airport);

                KStream<String, FlightEnriched> flightsEnriched = flightsWithOriginAirport.join(airports,
                                destinationAirportMapper, enrichedFlightJoiner);

                // Write to tracked using custom serialiser
                flightsEnriched.to("flights-enriched", Produced.with(Serdes.String(), JsonSerdes.FlightEnriched()));

                // Create tumbling window of 30 min above flights
                TimeWindows hoppingWindow = TimeWindows.of(Duration.ofMinutes(30)).advanceBy(Duration.ofSeconds(10));

                Initializer<Average> averageInitializer = Average::new;

                Aggregator<String, FlightEnriched, Average> averageAdder = (key, value, aggregate) -> aggregate
                                .add(value);

                // Group by destination airport, put into windows and aggregate over every
                // window
                KTable<String, Average> windowedDestinationAverages = flightsEnriched
                                .groupBy((key, value) -> value.getDestinationAirportCode(),
                                                Grouped.with(Serdes.String(), JsonSerdes.FlightEnriched()))
                                .windowedBy(hoppingWindow)
                                .aggregate(averageInitializer, averageAdder,
                                                Materialized.with(Serdes.String(), JsonSerdes.Average()))
                                .toStream()
                                .selectKey((k, v) -> k.key())
                                .toTable(Materialized.with(Serdes.String(), JsonSerdes.Average()));

                // Do the same, but for origin airports
                KTable<String, Average> windowedOriginAverages = flightsEnriched
                                .groupBy((key, value) -> value.getOriginAirportCode(),
                                                Grouped.with(Serdes.String(), JsonSerdes.FlightEnriched()))
                                .windowedBy(hoppingWindow)
                                .aggregate(averageInitializer, averageAdder,
                                                Materialized.with(Serdes.String(), JsonSerdes.Average()))
                                .toStream()
                                .selectKey((k, v) -> k.key())
                                .toTable(Materialized.with(Serdes.String(), JsonSerdes.Average()));

                // Finally, join the two tables to get origin and destination delays by airport.
                Joined<String, Average, Average> airportDelayJoined = Joined.with(Serdes.String(), JsonSerdes.Average(),
                                JsonSerdes.Average());

                ValueJoiner<Average, Average, AirportDelay> airportDelayJoiner = (averageOrigin,
                                averageDestination) -> new AirportDelay(
                                                averageOrigin.getDepartureAirport(),
                                                averageOrigin.getDepartureAverage(),
                                                averageDestination.getArrivalAverage());

                // TODO: if time allows, do outer join
                KTable<String, AirportDelay> delaysByAirport = windowedOriginAverages.join(windowedDestinationAverages,
                                airportDelayJoiner,
                                Materialized.<String, AirportDelay, KeyValueStore<Bytes, byte[]>>as("delays")
                                                .withKeySerde(Serdes.String())
                                                .withValueSerde(JsonSerdes.AirportDelay()));

                delaysByAirport.toStream().to("airport-delay",
                                Produced.with(Serdes.String(), JsonSerdes.AirportDelay()));

                return builder.build();
        }
}
