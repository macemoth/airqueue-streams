package ch.unisg.airqueue.model;

public class FlightEnriched {

    private String flightNumber;
    private String airlineName;
    private String startAirportName;
    private String destinationAirportName;
    private double departureDelay;
    private double arrivalDelay;

    public FlightEnriched(FlightWithAirline flightWithAirline, Airport startAirport, Airport destinationAirport) {
        this.flightNumber = flightWithAirline.getFlight().getAirline() + flightWithAirline.getFlight().getFlightNumber();
        this.airlineName = flightWithAirline.getAirline().getAirlineName();
        this.startAirportName = startAirport.getAirportName();
        this.destinationAirportName = destinationAirport.getAirportName();
        // TODO Marc: bring in departure delay from data
        this.departureDelay = 0.0;
        this.arrivalDelay = flightWithAirline.getFlight().getArrivalDelay();
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
                + ", startAirportName='"
                + startAirportName
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

    public String getStartAirportName() {
        return startAirportName;
    }

    public void setStartAirportName(String startAirportName) {
        this.startAirportName = startAirportName;
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
}
