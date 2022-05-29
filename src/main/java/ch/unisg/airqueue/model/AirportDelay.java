package ch.unisg.airqueue.model;

public class AirportDelay {

    private Airport airport;
    private double originDelay;
    private double destinationDelay;

    public AirportDelay(double originDelay, double destinationDelay) {
        this.airport = new Airport();
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
        return (originDelay+destinationDelay) / 2.0;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
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
