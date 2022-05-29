package ch.unisg.airqueue.model;

public class AcasEvent {
    // private String hex; // ICAO flight identifier
    private String flight; // callsign
    private double lat;
    private double lon;
    private String unixTimestamp;
    private double groundSpeed;

    public AcasEvent(String flight, double lat, double lon, String unixTimestamp, double groundSpeed) {
        this.flight = flight;
        this.lat = lat;
        this.lon = lon;
        this.unixTimestamp = unixTimestamp;
        this.groundSpeed = groundSpeed;
    }

    @Override
    public String toString() {
        return "{"
                + " flight='"
                + flight
                + "'"
                + ", lat='"
                + lat
                + "'"
                + ", lon='"
                + lon
                + "'"
                + ", unixTimestamp='"
                + unixTimestamp
                + "'"
                + ", groundSpeed='"
                + groundSpeed
                + "'"
                + "}";
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setUnixTimestamp(String unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public double getGroundSpeed() {
        return groundSpeed;
    }

    public void setGroundSpeed(double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }
}
