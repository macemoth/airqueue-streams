package ch.unisg.airqueue.controller;

import ch.unisg.airqueue.Average;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Similar to lab13s LeaderboardService class (why reinvent the wheel?)
 */
public class HttpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpController.class);

    private final HostInfo hostInfo;
    private final KafkaStreams kafkaStreams;

    public HttpController(String host, String port, KafkaStreams kafkaStreams) {
        this.hostInfo = new HostInfo(host, Integer.parseInt(port));
        this.kafkaStreams = kafkaStreams;
    }

    ReadOnlyKeyValueStore<String, Average> getAverages() {
        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        // state store name
                        "averages",
                        // state store type
                        QueryableStoreTypes.keyValueStore()));
    }

    public void start() {
        Javalin javalinApp = Javalin.create().start(hostInfo.port());

        /** Local key-value store query: all entries */
        javalinApp.get("/averages", this::getAll);
    }

    public void getAll(Context context) {
         KeyValueIterator<String, Average> averages = getAverages().all();
         Map<String, Double> averageDelays = new HashMap<>();

         while(averages.hasNext()) {
             KeyValue<String, Average> average = averages.next();
             averageDelays.put(average.key, average.value.getAverage());
         }

         averages.close();
         context.json(averageDelays);
    }



}
