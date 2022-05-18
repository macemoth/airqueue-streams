package ch.unisg.airqueue.model;

public class Airline {
    private String IATACode;
    private String airlineName;

    @Override
    public String toString() {
        return "{"
                + " IATACode='"
                + IATACode
                + "'"
                + ", airlineName='"
                + airlineName
                + "'"
                + "}";
    }

    public String getIATACode() {
        return IATACode;
    }

    public void setIATACode(String IATACode) {
        this.IATACode = IATACode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
