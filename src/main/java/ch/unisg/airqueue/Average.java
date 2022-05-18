package ch.unisg.airqueue;

import ch.unisg.airqueue.model.FlightWithAirline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class Average {

    private final TreeSet<FlightWithAirline> averages = new TreeSet<>();

    public Average add(final FlightWithAirline flight) {
        averages.add(flight);

        return this;
    }

    public List<FlightWithAirline> toList() {
        Iterator<FlightWithAirline> iterator = averages.iterator();
        List<FlightWithAirline> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }
}
