package ch.unisg.airqueue;


import ch.unisg.airqueue.TimestampExtractors.AcasTimestampExtractor;
import ch.unisg.airqueue.model.AcasEvent;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcasTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcasTopology.class);

    public static Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, AcasEvent> flights =
                builder.stream("acas", Consumed.with(Serdes.ByteArray(), JsonSerdes.AcasEvent())
                        .withTimestampExtractor(new AcasTimestampExtractor()))
                        // stream is unkeyed, we select airline as key to meet co-partitioning requirements
                        .selectKey((k, v) -> v.getFlight());

        return null;
    }
}
