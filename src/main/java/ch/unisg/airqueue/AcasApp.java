package ch.unisg.airqueue;

import ch.unisg.airqueue.partitioner.AcasPartitioner;
import ch.unisg.airqueue.topology.AcasTopologyDSL;
import ch.unisg.airqueue.topology.AcasTopologyProcessorAPI;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class AcasApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcasApp.class);
    private static final String APP_HOST = "localhost";
    private static final String APP_PORT = "7000";
    private static final String STATE_DIR = "/tmp/kafka-streams";

    public static void main(String[] args) {
        LOGGER.info("Building topology...");
        // The following two topologies can be freely interchanged and illustrate two approaches to the same problem
        // Topology topology = AcasTopologyDSL.buildTopology();
        Topology topology = AcasTopologyDSL.buildTopology();
        LOGGER.info("Topology built");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "acas_consumer");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(StreamsConfig.APPLICATION_SERVER_CONFIG, APP_HOST + ":" + APP_PORT);
        props.put(StreamsConfig.STATE_DIR_CONFIG, STATE_DIR);
        // Our custom partitioner
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, AcasPartitioner.class.getName());

        LOGGER.info("Creating Kafka Streams instance");
        KafkaStreams streams = new KafkaStreams(topology, props);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        // Cleaning up while in development
        LOGGER.info("Cleaning up State dir");
        streams.cleanUp();

        LOGGER.info("Starting up Kafka Streams");
        streams.start();
        LOGGER.info("Done");
    }
}
