package ch.unisg.airqueue.serialisation;

import airqueue.model.Airline;
import airqueue.model.Airport;
import airqueue.model.Flight;
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
}
