package ch.unisg.airqueue.aggregates;

import ch.unisg.airqueue.model.AirportDelay;

import java.util.LinkedList;
import java.util.List;

public class Prediction {

    private final List<AirportDelay> lastDelays = new LinkedList<>();

    public Prediction add(final AirportDelay delay) {
        lastDelays.add(delay);

        if(lastDelays.size() >= 3) {
            lastDelays.remove(0);
        }

        return this;
    }

    public String getAirport(){
        if(lastDelays.size() == 0) {
            return "Unknown airport";
        } else return lastDelays.get(0).getAirport();
    }

    public double predictNextAverage() {
        double first = lastDelays.get(0).getGeneralDelay();
        double last = lastDelays.get(lastDelays.size()-1).getGeneralDelay();

        double delta = (last-first)/3.0;
        double deltaHat = (delta + averageDelay())/2;
        return last + deltaHat;
    }

    public double averageDelay() {
        double sum = 0.0;
        for (int i = 0; i < lastDelays.size(); i++) {
            sum += lastDelays.get(i).getGeneralDelay();
        }
        return sum / ((double) lastDelays.size());
    }

}
