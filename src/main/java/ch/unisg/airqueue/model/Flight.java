package ch.unisg.airqueue.model;

public class Flight {

    private String time;
    private String airline;
    private String flightNumber;
    private String tailNumber;
    private String originAirport;
    private String destinationAirport;
    private double departureDelay;
    private double arrivalDelay;

    public Flight(String time, String airline, String flightNumber, String tailNumber, String originAirport,
            String destinationAirport, double departureDelay, double arrivalDelay) {
        this.time = time;
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.tailNumber = tailNumber; // equivalent with registration of ACAS
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
        this.departureDelay = departureDelay;
        this.arrivalDelay = arrivalDelay;
    }

    @Override
    public String toString() {
        return "{"
                + " time='"
                + time
                + "'"
                + ", airline='"
                + airline
                + "'"
                + ", flightNumber='"
                + flightNumber
                + "'"
                + ", tailNumber='"
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
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
