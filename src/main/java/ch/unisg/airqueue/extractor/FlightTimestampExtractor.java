package ch.unisg.airqueue.extractor;

import ch.unisg.airqueue.model.Flight;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.Instant;

/**
 * Freely taken from lab14
 */
public class FlightTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        Flight flight = (Flight) consumerRecord.value();
        if (flight != null && flight.getTime() != null) {
            return Instant.parse(flight.getTime()).toEpochMilli();
        }
        return l;
    }
}
