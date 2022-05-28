package ch.unisg.airqueue.serialisation;

import ch.unisg.airqueue.Average;
import ch.unisg.airqueue.model.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

/**
 * Inspired from lab13 (there is arguably not much different to do here)
 */
public class JsonSerdes {

    public static Serde<Flight> Flight() {
        JsonSerialiser<Flight> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<Flight> deserialiser = new JsonDeserialiser<>(Flight.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<Airline> Airline() {
        JsonSerialiser<Airline> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<Airline> deserialiser = new JsonDeserialiser<>(Airline.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<Airport> Airport() {
        JsonSerialiser<Airport> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<Airport> deserialiser = new JsonDeserialiser<>(Airport.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<FlightWithAirline> FlightWithAirline() {
        JsonSerialiser<FlightWithAirline> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<FlightWithAirline> deserialiser = new JsonDeserialiser<>(FlightWithAirline.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<FlightWithOriginAirport> FlightWithOriginAirport() {
        JsonSerialiser<FlightWithOriginAirport> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<FlightWithOriginAirport> deserialiser = new JsonDeserialiser<>(FlightWithOriginAirport.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<FlightEnriched> FlightEnriched() {
        JsonSerialiser<FlightEnriched> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<FlightEnriched> deserialiser = new JsonDeserialiser<>(FlightEnriched.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<Average> Average() {
        JsonSerialiser<Average> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<Average> deserialiser = new JsonDeserialiser<>(Average.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<AirportDelay> AirportDelay() {
        JsonSerialiser<AirportDelay> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<AirportDelay> deserialiser = new JsonDeserialiser<>(AirportDelay.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

}
