package ch.unisg.airqueue.model;

public class FlightWithOriginAirport {

    private Flight flight;
    private Airline airline;
    private Airport originAirport;

    public FlightWithOriginAirport(Flight flight, Airline airline, Airport startAirport) {
        this.flight = flight;
        this.airline = airline;
        this.originAirport = startAirport;
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
                + ", originAirport='"
                + originAirport
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

    public Airport getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Airport originAirport) {
        this.originAirport = originAirport;
    }
}
