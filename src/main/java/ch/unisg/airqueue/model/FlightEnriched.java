package ch.unisg.airqueue.model;

public class FlightEnriched {

    private String flightNumber;
    private String airlineName;
    private String originAirportCode;
    private String originAirportName;
    private String destinationAirportCode;
    private String destinationAirportName;
    private double departureDelay;
    private double arrivalDelay;

    public FlightEnriched(Flight flight, Airline airline, Airport startAirport, Airport destinationAirport) {
        this.flightNumber = flight.getAirline() + flight.getFlightNumber();
        this.airlineName = airline.getAirlineName();
        this.originAirportCode = startAirport.getIataCode();
        this.originAirportName = startAirport.getAirportName();
        this.destinationAirportCode = destinationAirport.getIataCode();
        this.destinationAirportName = destinationAirport.getAirportName();
        this.departureDelay = flight.getDepartureDelay();
        this.arrivalDelay = flight.getArrivalDelay();
    }

    @Override
    public String toString() {
        return "{"
                + " flightNumber='"
                + flightNumber
                + "'"
                + ", airlineName='"
                + airlineName
                + "'"
                + ", startAirportCode='"
                + originAirportCode
                + "'"
                + ", startAirportName='"
                + originAirportName
                + "'"
                + ", destinationAirportCode='"
                + destinationAirportCode
                + "'"
                + ", destinationAirportName='"
                + destinationAirportName
                + "'"
                + ", departureDelay='"
                + departureDelay
                + "'"
                + ", arrivalDelay='"
                + arrivalDelay
                + "'"
                + "}";
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getOriginAirportName() {
        return originAirportName;
    }

    public void setOriginAirportName(String originAirportName) {
        this.originAirportName = originAirportName;
    }

    public String getDestinationAirportName() {
        return destinationAirportName;
    }

    public void setDestinationAirportName(String destinationAirportName) {
        this.destinationAirportName = destinationAirportName;
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

    public String getOriginAirportCode() {
        return originAirportCode;
    }

    public void setOriginAirportCode(String originAirportCode) {
        this.originAirportCode = originAirportCode;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }
}
