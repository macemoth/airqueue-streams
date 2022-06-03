package ch.unisg.airqueue.model;

public class AirportDelay {

    private String airport;
    private double originDelay;
    private double destinationDelay;

    public AirportDelay(String airport, double originDelay, double destinationDelay) {
        this.airport = airport;
        this.originDelay = originDelay;
        this.destinationDelay = destinationDelay;
    }

    @Override
    public String toString() {
        return "{"
                + " airport='"
                + airport
                + "'"
                + ", originDelay='"
                + originDelay
                + "'"
                + ", destinationDelay='"
                + destinationDelay
                + "'"
                + "}";
    }

    public double getGeneralDelay() {
        return (originDelay + destinationDelay) / 2.0;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public double getOriginDelay() {
        return originDelay;
    }

    public void setOriginDelay(double originDelay) {
        this.originDelay = originDelay;
    }

    public double getDestinationDelay() {
        return destinationDelay;
    }

    public void setDestinationDelay(double destinationDelay) {
        this.destinationDelay = destinationDelay;
    }
}
