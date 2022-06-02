package ch.unisg.airqueue.topology;

import ch.unisg.airqueue.aggregates.Prediction;
import ch.unisg.airqueue.model.*;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.avro.data.Json;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MoodTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoodTopology.class);

    public static Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        // Already keyed by airport IATA code
        KStream<String, AirportDelay> delays =
                builder.stream("airport-delay", Consumed.with(Serdes.String(), JsonSerdes.AirportDelay()));

        Initializer<Prediction> predictionInitializer = Prediction::new;

        Aggregator<String, AirportDelay, Prediction> predictionAggregator =
                (key, value, aggregate) -> aggregate.add(value);

        KTable<String, Double> predictions = delays
                .groupByKey()
                .aggregate(predictionInitializer, predictionAggregator,
                        Materialized.with(Serdes.String(), JsonSerdes.Prediction()))
                .mapValues(
                    (value) -> value.predictNextAverage(),
                    Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>
                    as("mood")
                    .withKeySerde(Serdes.String())
                    .withValueSerde(Serdes.Double())
                );

        return builder.build();

    }

}
