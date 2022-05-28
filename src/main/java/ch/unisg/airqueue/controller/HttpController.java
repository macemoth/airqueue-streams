package ch.unisg.airqueue.controller;

import ch.unisg.airqueue.model.AirportDelay;
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

    ReadOnlyKeyValueStore<String, AirportDelay> getDelays() {
        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        // state store name
                        "delays",
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
         KeyValueIterator<String, AirportDelay> delays = getDelays().all();
         Map<String, Double> averageDelays = new HashMap<>();

         while(delays.hasNext()) {
             KeyValue<String, AirportDelay> average = delays.next();
             averageDelays.put(average.key, average.value.getGeneralDelay());
         }

        delays.close();
         context.json(averageDelays);
    }

    public void getAllHtml(Context context) {
        KeyValueIterator<String, AirportDelay> averages = getDelays().all();
        StringBuilder page = new StringBuilder();
        page.append("<html> <head><title>airqueue: Average delay by airport</title>");
        page.append("<style type=\"text/css\">p font-family: Comic Sans MS;</style></head>");
        page.append("<body><h1>Average delay by airport</h1>");
        page.append("<table><thead><th>Airport</th><th>Avgerage Delay (minutes)</th></thead>");
        page.append("<tbody>");
        while(averages.hasNext()) {
            KeyValue<String, AirportDelay> delay = averages.next();
            page.append("<tr><td>" + delay.key + "</td><td>" + delay.value.getGeneralDelay() + "</td></tr>");
        }
        averages.close();

        page.append("</tbody></table></body></html>");
        context.html(page.toString());
    }



}
