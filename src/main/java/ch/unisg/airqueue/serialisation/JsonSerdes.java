package ch.unisg.airqueue.serialisation;

import ch.unisg.airqueue.aggregates.AcasAggregate;
import ch.unisg.airqueue.aggregates.Average;
import ch.unisg.airqueue.aggregates.Prediction;
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

    public static Serde<AcasEvent> AcasEvent() {
        JsonSerialiser<AcasEvent> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<AcasEvent> deserialiser = new JsonDeserialiser<>(AcasEvent.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<IncompleteFlight> IncompleteFlight() {
        JsonSerialiser<IncompleteFlight> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<IncompleteFlight> deserialiser = new JsonDeserialiser<>(IncompleteFlight.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<AcasAggregate> AcasAggregate() {
        JsonSerialiser<AcasAggregate> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<AcasAggregate> deserialiser = new JsonDeserialiser<>(AcasAggregate.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

    public static Serde<Prediction> Prediction() {
        JsonSerialiser<Prediction> serialiser = new JsonSerialiser<>();
        JsonDeserialiser<Prediction> deserialiser = new JsonDeserialiser<>(Prediction.class);
        return Serdes.serdeFrom(serialiser, deserialiser);
    }

}
