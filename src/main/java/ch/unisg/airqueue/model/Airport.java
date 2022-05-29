package ch.unisg.airqueue.model;

public class Airport {

    private String iataCode;
    private String airportName;
    private String city;
    private String state;
    private String country;
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return "{"
                + " iataCode='"
                + iataCode
                + "'"
                + ", airportName='"
                + airportName
                + "'"
                + ", city='"
                + city
                + "'"
                + ", state='"
                + state
                + "'"
                + ", country='"
                + country
                + "'"
                + ", longitude='"
                + longitude
                + "'"
                + ", latitude='"
                + latitude
                + "'"
                + "}";
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
