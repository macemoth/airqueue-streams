package ch.unisg.airqueue.processor;

import ch.unisg.airqueue.Utils;
import ch.unisg.airqueue.model.AcasEvent;
import ch.unisg.airqueue.model.Flight;
import ch.unisg.airqueue.model.IncompleteFlight;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class AcasFlightProcessor implements Processor<Byte, AcasEvent, String, Flight> {

    private ProcessorContext<String, Flight> context;
    private KeyValueStore<String, IncompleteFlight> store;
    private static final Logger LOGGER = LoggerFactory.getLogger(AcasFlightProcessor.class);

    @Override
    public void init(ProcessorContext<String, Flight> context) {
        this.context = context;
        this.store = (KeyValueStore) context.getStateStore("acas-flights");
    }

    @Override
    public void process(Record<Byte, AcasEvent> record) {
        String key = record.value().getFlight();
        String time = Instant.ofEpochMilli(record.timestamp()).toString();
        IncompleteFlight value = store.get(key);

        // If flight has never been seen before
        if (value == null) {
            IncompleteFlight incomplete = new IncompleteFlight(time, record.value().getFlight(), record.value().getRegistration());

            // If acas event is from the ground, we consider the flight to start at that moment
            if (record.value().isOnGround() == 1) {
                // For brevity, we omit matching with a flight database to determine the airport and delays
                incomplete.setOriginAirport(Utils.getGeoUri(record.value().getLat(), record.value().getLon()));
            } // if acas event is not on the ground, it already started before. Then we don't know the origin airport
            else {
                incomplete.setOriginAirport("Unknown");
            }
            store.put(incomplete.getFlight(), incomplete);
        // flight has already been seen recently, check whether it has landed or is still going
        } else {
            // the flight has landed, finish it and emit new event
            if (record.value().isOnGround() == 1) {
                value.setDestinationAirport(Utils.getGeoUri(record.value().getLat(), record.value().getLon()));
                Flight f = value.toFlight();
                Record<String, Flight> event = new Record<>(value.getFlight(), f, Instant.parse(f.getTime()).toEpochMilli());
                store.delete(key); // delete from flight state
                context.forward(event);
                LOGGER.info("Created new flight " + key);
            } else {
                // do nothing
            }
        }
    }

    @Override
    public void close() {

    }
}
