package ch.unisg.airqueue.TimestampExtractors;

import ch.unisg.airqueue.model.AcasEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;


public class AcasTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        AcasEvent event = (AcasEvent) consumerRecord.value();
        if (event != null && event.getUnixTimestamp() != null) {
            return Long.parseLong(event.getUnixTimestamp())*1000; // TimestampExtractor expects ms, not s
        }
        return l;
    }
}
