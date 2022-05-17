package ch.unisg.airqueue.serialisation;

import airqueue.model.Flight;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.common.serialization.Serde;


import java.util.Collections;
import java.util.Map;

public class AvroSerdes {

    public static Serde<Flight> Flight(String url, boolean isKey) {
        Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", url);
        Serde<Flight> serde = new SpecificAvroSerde<>();
        serde.configure(serdeConfig, isKey);
        return serde;
    }
}
