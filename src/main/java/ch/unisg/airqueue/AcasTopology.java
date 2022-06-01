package ch.unisg.airqueue;


import ch.unisg.airqueue.TimestampExtractors.AcasTimestampExtractor;
import ch.unisg.airqueue.model.IncompleteFlight;
import ch.unisg.airqueue.processor.AcasFlightProcessor;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcasTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcasTopology.class);

    public static Topology buildTopology() {
        Topology builder = new Topology();

        builder.addSource(
            Topology.AutoOffsetReset.LATEST,
        "ACAS events",
            new AcasTimestampExtractor(),
            Serdes.ByteArray().deserializer(),
            JsonSerdes.AcasEvent().deserializer(),
            "acas");

        builder.addProcessor("ACAS processor",
                AcasFlightProcessor::new,
                "ACAS events");

        StoreBuilder<KeyValueStore<String, IncompleteFlight>> storeBuilder =
                Stores.keyValueStoreBuilder(
                        Stores.persistentKeyValueStore("acas-flights"),
                        Serdes.String(),
                        JsonSerdes.IncompleteFlight());

        builder.addStateStore(storeBuilder, "ACAS processor");

        builder.addSink("Flight sink",
                "flights",
                Serdes.String().serializer(),
                JsonSerdes.Flight().serializer(),
                "ACAS processor");

        return builder;
    }
}
