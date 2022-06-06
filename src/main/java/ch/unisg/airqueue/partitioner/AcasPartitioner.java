package ch.unisg.airqueue.partitioner;

import ch.unisg.airqueue.model.AcasEvent;
import ch.unisg.airqueue.serialisation.JsonSerdes;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;

public class AcasPartitioner implements Partitioner {

    private Random random;
    Deserializer deserializer;
    DefaultPartitioner defaultPartitioner;

    private static final Logger LOGGER = LoggerFactory.getLogger(AcasPartitioner.class);


    /**
     * Very hacky method to put flights that start with letters up to 'M' into partition 0, those above into partition 1
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if(!topic.startsWith("acas")) {
            return defaultPartitioner.partition(topic, key, keyBytes, value, valueBytes, cluster);
        }
        AcasEvent event = (AcasEvent) deserializer.deserialize("acas", valueBytes);

        if (event.getFlight() == null) {
            // if the input is no AcasEvent
            LOGGER.info("Value was not of class AcasEvent or has empty flight");
            return random.nextInt(2);
        }
        char flightLetter = event.getFlight().charAt(0);
        char distinguisher = 'M';

        if(flightLetter <= distinguisher) {
            LOGGER.info("Acas Event directed to partition 0");
            return 0;
        } else {
            LOGGER.info("Acas Event directed to partition 1");
            return 1;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {
        random = new Random();
        deserializer = JsonSerdes.AcasEvent().deserializer();
        defaultPartitioner = new DefaultPartitioner();
    }
}
