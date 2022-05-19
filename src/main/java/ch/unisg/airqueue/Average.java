package ch.unisg.airqueue;

import ch.unisg.airqueue.model.Flight;

import java.util.ArrayList;

public class Average {

    private final ArrayList<Flight> flights = new ArrayList<>();

    public Average add(final Flight flight) {
        flights.add(flight);

        return this;
    }

    public double getAverage() {
        double sum = 0;
        for (Flight flight : flights) {
            sum += flight.getArrivalDelay();
        }
        return sum / flights.size();
    }
}
