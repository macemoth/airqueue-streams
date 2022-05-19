package ch.unisg.airqueue;


import ch.unisg.airqueue.model.Airline;
import ch.unisg.airqueue.model.Airport;
import ch.unisg.airqueue.model.Flight;
import ch.unisg.airqueue.model.FlightWithAirline;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class FlightDataAnalysisTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightDataAnalysisTopology.class);

    public static Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        // TODO: ADR to describe time and partition time fallback
        KStream<String, Flight> flights =
                builder.stream("flights", Consumed.with(Serdes.ByteArray(), JsonSerdes.Flight())
                        .withTimestampExtractor(new FlightTimestampExtractor()))
                // stream is unkeyed, now we choose airline, as we join with that
                .selectKey((k, v) -> v.getAirline());
        LOGGER.info("KStream flights created");

        // TODO: Consider and discuss filtering

        // Airlines has IATA code as key
        // TODO: ADR on whether to use KTable or GlobalKTable
        KTable<String, Airline> airlines =
                builder.table("airlines", Consumed.with(Serdes.String(), JsonSerdes.Airline()));
        LOGGER.info("KTable airlines created");

        // Airports has IATA code as key
        KTable<String, Airport> airports =
                builder.table("airports", Consumed.with(Serdes.String(), JsonSerdes.Airport()));

        // Join Flights with Airlines
        Joined<String, Flight, Airline> flightAirlineJoined =
                Joined.with(Serdes.String(), JsonSerdes.Flight(), JsonSerdes.Airline());

        ValueJoiner<Flight, Airline, FlightWithAirline> flightAirlineJoiner =
                (flight, airline) -> new FlightWithAirline(flight, airline);

        KStream<String, FlightWithAirline> flightsWithAirlines =
                flights.join(airlines, flightAirlineJoiner, flightAirlineJoined);

        // TODO: Join with Start and destination airports

        // Create tumbling window of 30 min above flights
        TimeWindows tumblingWindow = TimeWindows.of(Duration.ofMinutes(5)).grace(Duration.ofSeconds(10));

        // Group by destination airport
        KGroupedStream<String, Flight> flightsByAirport = flights
                .groupBy((key, value) -> value.getDestinationAirport(),
                Grouped.with(Serdes.String(), JsonSerdes.Flight()));
                //.windowedBy(tumblingWindow)

        Initializer<Average> averageInitializer = Average::new;

        Aggregator<String, Flight, Average> averageAdder =
            (key, value, aggregate) -> aggregate.add(value);

        KTable<String, Average> groupedFlightsTable = flightsByAirport.aggregate(
            averageInitializer,
            averageAdder,
            Materialized.<String, Average, KeyValueStore<Bytes, byte[]>>
                    as("averages")
                    .withKeySerde(Serdes.String())
                    .withValueSerde(JsonSerdes.Average()));


        groupedFlightsTable.toStream()
                .peek(
                        (key, value) -> {
                            LOGGER.info("Got " + key + " with value " + value.getAverage());
                        }
                )
                .to("tracked");

        return builder.build();
    }
}
