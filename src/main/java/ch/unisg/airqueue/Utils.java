package ch.unisg.airqueue;

public class Utils {

    /**
     * Transforms two coordinates to a geo URI
     * (https://en.wikipedia.org/wiki/Geo_URI_scheme)
     */
    public static String getGeoUri(Double lat, Double lon) {
        return "geo:" + lat.toString() + ", " + lon.toString();
    }
}
