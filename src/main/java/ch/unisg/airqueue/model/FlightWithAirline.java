package ch.unisg.airqueue.model;

public class FlightWithAirline {

    private Flight flight;
    private Airline airline;

    public FlightWithAirline(Flight flight, Airline airline) {
        this.flight = flight;
        this.airline = airline;
    }

    @Override
    public String toString() {
        return "{"
                + " flight='"
                + flight
                + "'"
                + ", airline='"
                + airline
                + "'"
                + "}";
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
