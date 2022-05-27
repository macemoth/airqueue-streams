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

        // Local key-value store query: all entries
        javalinApp.get("/averages", this::getAll);

        // Render all as html
        javalinApp.get("averagesList", this::getAllHtml);
    }

    public void getAll(Context context) {
         KeyValueIterator<String, Average> averages = getAverages().all();
         Map<String, Double> averageDelays = new HashMap<>();

         while(averages.hasNext()) {
             KeyValue<String, Average> average = averages.next();
             averageDelays.put(average.key, average.value.getArrivalAverage());
         }

         averages.close();
         context.json(averageDelays);
    }

    public void getAllHtml(Context context) {
        KeyValueIterator<String, Average> averages = getAverages().all();
        StringBuilder page = new StringBuilder();
        page.append("<html> <head><title>airqueue: Average delay by airport</title>");
        page.append("<style type=\"text/css\">p font-family: Comic Sans MS;</style></head>");
        page.append("<body><h1>Average delay by airport</h1>");
        page.append("<table><thead><th>Airport</th><th>Avgerage Delay (minutes)</th></thead>");
        page.append("<tbody>");
        while(averages.hasNext()) {
            KeyValue<String, Average> average = averages.next();
            page.append("<tr><td>" + average.key + "</td><td>" + average.value.getArrivalAverage() + "</td></tr>");
        }
        averages.close();

        page.append("</tbody></table></body></html>");
        context.html(page.toString());
    }



}
