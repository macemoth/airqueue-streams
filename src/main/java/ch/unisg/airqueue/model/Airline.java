package ch.unisg.airqueue.model;

public class Airline {
    private String iataCode;
    private String airlineName;

    @Override
    public String toString() {
        return "{"
                + " iataCode='"
                + iataCode
                + "'"
                + ", airlineName='"
                + airlineName
                + "'"
                + "}";
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
