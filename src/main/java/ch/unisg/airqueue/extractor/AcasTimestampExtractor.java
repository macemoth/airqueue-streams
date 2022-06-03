package ch.unisg.airqueue.extractor;

import ch.unisg.airqueue.model.AcasEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AcasTimestampExtractor implements TimestampExtractor {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH);

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        AcasEvent event = (AcasEvent) consumerRecord.value();
        if (event != null && event.getUtc() != null) {
            try {
                return format.parse(event.getUtc()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return l;
    }
}
