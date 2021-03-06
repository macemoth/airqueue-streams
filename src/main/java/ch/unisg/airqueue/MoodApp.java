package ch.unisg.airqueue;

import ch.unisg.airqueue.controller.MoodHttpController;
import ch.unisg.airqueue.topology.MoodTopology;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MoodApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ch.unisg.airqueue.DelayApp.class);
    private static final String APP_HOST = "localhost";
    private static final String APP_PORT = "7001";
    private static final String STATE_DIR = "/tmp/kafka-streams";

    public static void main(String[] args) {
        LOGGER.info("Building topology...");
        Topology topology = MoodTopology.buildTopology();
        LOGGER.info("Topology built");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "mood_consumer");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(StreamsConfig.APPLICATION_SERVER_CONFIG, APP_HOST + ":" + APP_PORT);
        props.put(StreamsConfig.STATE_DIR_CONFIG, STATE_DIR);

        LOGGER.info("Creating Kafka Streams instance");
        KafkaStreams streams = new KafkaStreams(topology, props);
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

        // Cleaning up while in development
        LOGGER.info("Cleaning up State dir");
        streams.cleanUp();

        LOGGER.info("Starting up Kafka Streams");
        streams.start();

        LOGGER.info("Starting up HTTP Controller");
        MoodHttpController controller = new MoodHttpController(APP_HOST, APP_PORT, streams);
        controller.start();
    }

}