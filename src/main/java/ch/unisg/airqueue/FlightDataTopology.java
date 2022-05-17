package ch.unisg.airqueue;

import airqueue.model.Airline;
import airqueue.model.Flight;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlightDataTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightDataTopology.class);

    public static Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Flight> flights =
                builder.stream("flights", Consumed.with(Serdes.ByteArray(), JsonSerdes.Flight()))
                // stream is unkeyed, now we choose airline, as we join with that
                .selectKey((k, v) -> v.getAirline().toString());
        LOGGER.info("KStream flights prepared");

        // TODO: Airlines and Airports
        return null;


    }
}
