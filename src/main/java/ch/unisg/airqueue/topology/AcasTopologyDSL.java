package ch.unisg.airqueue.topology;

import ch.unisg.airqueue.aggregates.AcasAggregate;
import ch.unisg.airqueue.extractor.AcasTimestampExtractor;
import ch.unisg.airqueue.model.AcasEvent;
import ch.unisg.airqueue.model.Flight;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class AcasTopologyDSL {

        private static final Logger LOGGER = LoggerFactory.getLogger(AcasTopologyDSL.class);

        public static Topology buildTopology() {
                StreamsBuilder builder = new StreamsBuilder();

                KStream<String, AcasEvent> events = builder
                                .stream("acas", Consumed.with(Serdes.ByteArray(), JsonSerdes.AcasEvent())
                                                .withTimestampExtractor(new AcasTimestampExtractor()))
                                .selectKey((k, v) -> v.getFlight());

                KStream<String, AcasEvent> filteredEvents = events
                        // Filter out events that have both coordinates 0
                        .filterNot( (key, value) -> {
                                return (value.getLat() == 0.0 && value.getLat() == 0.0);
                        });

                // We assume that after receiving no events for 15 minutes, the plane has either
                // landed or left the observed airspace
                SessionWindows flightWindow = SessionWindows.with(Duration.ofMinutes(15)).grace(Duration.ofSeconds(5));

                Initializer<AcasAggregate> acasInitializer = AcasAggregate::new;

                Aggregator<String, AcasEvent, AcasAggregate> aggregateAdder = (key, value, aggregate) -> aggregate
                                .add(value);

                Merger<String, AcasAggregate> acasAggregateMerger = (key, aggregate1, aggregate2) -> aggregate1
                                .mergeWith(aggregate2);

                KStream<String, Flight> flights = filteredEvents
                                .groupByKey(Grouped.with(Serdes.String(), JsonSerdes.AcasEvent()))
                                .windowedBy(flightWindow)
                                .aggregate(acasInitializer, aggregateAdder, acasAggregateMerger,
                                                Materialized.with(Serdes.String(), JsonSerdes.AcasAggregate()))
                                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                                .toStream()
                                .mapValues(
                                                (value) -> value.toFlight())
                                .selectKey((k, v) -> k.key())
                        .peek((key, value) -> {
                                LOGGER.info(key + " has been created");
                        });


                flights.to("flights-pseudo", Produced.with(Serdes.String(), JsonSerdes.Flight()));

                return builder.build();
        }
}
