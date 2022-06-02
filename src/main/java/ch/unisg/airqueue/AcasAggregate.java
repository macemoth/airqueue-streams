package ch.unisg.airqueue;

import ch.unisg.airqueue.model.AcasEvent;
import ch.unisg.airqueue.model.Flight;

import java.util.ArrayList;

public class AcasAggregate {

    private final ArrayList<AcasEvent> events = new ArrayList<>();

    public AcasAggregate add(final AcasEvent event) {
        events.add(event);

        return this;
    }

    public Flight toFlight() {
        AcasEvent first = events.get(0);
        AcasEvent last = events.get(events.size()-1);

        String originAirport, destinationAirport;
        if(first.isOnGround() == 1) {
            originAirport = Utils.getGeoUri(first.getLat(), first.getLon());
        } else {
            originAirport = "Unknown";
        }

        if(last.isOnGround() == 1) {
            destinationAirport = Utils.getGeoUri(last.getLat(), last.getLon());
        } else {
            destinationAirport = "Unknown";
        }

        Flight f = new Flight(
                last.getUtc(),
                "XX",
                last.getFlight(),
                last.getFlight(), // we don't have the flight number, but it is often the tail number and could be looked up
                originAirport,
                destinationAirport,
                0.0,
                0.0
        );
        return f;
    }

    public AcasAggregate mergeWith(AcasAggregate other) {
        for (AcasEvent otherEvent : other.events) {
            events.add(otherEvent);
        }
        return this;
    }
}
