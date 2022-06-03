package ch.unisg.airqueue.model;

public class IncompleteFlight {

    private String time;
    private String flight;
    private String tailNumber;
    private String originAirport;
    private String destinationAirport;
    private double departureDelay;
    private double arrivalDelay;

    public IncompleteFlight(String time, String flight, String registration) {
        this.time = time;
        this.flight = flight;
        this.tailNumber = registration;
        this.originAirport = "Unknown";
        this.destinationAirport = "Unknown";
        this.departureDelay = 0.0;
        this.arrivalDelay = 0.0;
    }

    @Override
    public String toString() {
        return "{"
                + " time='"
                + time
                + "'"
                + " flight='"
                + flight
                + "'"
                + " tailNumber='"
                + tailNumber
                + "'"
                + ", originAirport='"
                + originAirport
                + "'"
                + ", destinationAirport='"
                + destinationAirport
                + "'"
                + ", departureDelay='"
                + departureDelay +
                "'"
                + ", arrivalDelay='"
                + arrivalDelay +
                "'"
                + "}";
    }

    /**
     * Makeshift method for creating a real flight object
     * @return
     */
    public Flight toFlight() {
        Flight f = new Flight(
                this.time,
                "XX", // this would have to be looked up
                this.tailNumber, // we don't have the flight number, but it is often the tail number and could be looked up
                this.tailNumber,
                this.originAirport,
                this.destinationAirport,
                this.departureDelay,
                this.arrivalDelay
        );
        return f;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(String originAirport) {
        this.originAirport = originAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public double getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(double departureDelay) {
        this.departureDelay = departureDelay;
    }

    public double getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(double arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }
}
