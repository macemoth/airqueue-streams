package ch.unisg.airqueue.model;

public class Flight {

    private String time;
    private String airline;
    private int flightNumber;
    private String tailNumber;
    private String originAirport;
    private String destinationAirport;
    private double arrivalDelay;

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

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
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

    public double getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(double arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }
}
