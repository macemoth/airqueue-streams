package ch.unisg.airqueue.model;

public class AcasEvent {
    // private String hex; // ICAO flight identifier
    private String flight; // first two are airline ICAO, remainder is flight number
    private String registration;
    private double lat;
    private double lon;
    private String utc;
    private double groundSpeed;
    private int onGround;

    public AcasEvent(String flight, String registration, double lat, double lon, String unixTimestamp,
            double groundSpeed, int onGround) {
        this.flight = flight;
        this.registration = registration;
        this.lat = lat;
        this.lon = lon;
        this.utc = unixTimestamp;
        this.groundSpeed = groundSpeed;
        this.onGround = onGround;
    }

    @Override
    public String toString() {
        return "{"
                + " flight='"
                + flight
                + "'"
                + " registration='"
                + registration
                + "'"
                + ", lat='"
                + lat
                + "'"
                + ", lon='"
                + lon
                + "'"
                + ", utc='"
                + utc
                + "'"
                + ", groundSpeed='"
                + groundSpeed
                + "'"
                + ", onGround='"
                + onGround
                + "'"
                + "}";
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
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

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public double getGroundSpeed() {
        return groundSpeed;
    }

    public void setGroundSpeed(double groundSpeed) {
        this.groundSpeed = groundSpeed;
    }

    public int isOnGround() {
        return onGround;
    }

    public void setOnGround(int onGround) {
        this.onGround = onGround;
    }
}
