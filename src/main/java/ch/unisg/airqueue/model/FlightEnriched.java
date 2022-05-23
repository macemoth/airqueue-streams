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
}
