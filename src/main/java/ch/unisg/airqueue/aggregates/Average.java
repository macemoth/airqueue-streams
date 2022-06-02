package ch.unisg.airqueue.aggregates;

import ch.unisg.airqueue.model.FlightEnriched;

import java.util.ArrayList;

public class Average {

    private final ArrayList<FlightEnriched> flights = new ArrayList<>();

    public Average add(final FlightEnriched flightEnriched) {
        flights.add(flightEnriched);

        return this;
    }

    public double getArrivalAverage() {
        if(flights.size() == 0) {
            return Double.NaN;
        }
        double sum = 0;
        for (FlightEnriched flight : flights) {
            sum += flight.getArrivalDelay();
        }
        return sum / flights.size();
    }

    public double getDepartureAverage() {
        if(flights.size() == 0) {
            return Double.NaN;
        }
        double sum = 0;
        for (FlightEnriched flight: flights) {
            sum += flight.getDepartureDelay();
        }
        return sum / ((double) flights.size());
    }
}
