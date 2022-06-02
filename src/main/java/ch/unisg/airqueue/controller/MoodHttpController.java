package ch.unisg.airqueue.controller;

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

public class MoodHttpController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoodHttpController.class);

    private final HostInfo hostInfo;
    private final KafkaStreams kafkaStreams;

    public MoodHttpController(String host, String port, KafkaStreams kafkaStreams) {
        this.hostInfo = new HostInfo(host, Integer.parseInt(port));
        this.kafkaStreams = kafkaStreams;
    }

    ReadOnlyKeyValueStore<String, Double> getMood() {
        return kafkaStreams.store(
                StoreQueryParameters.fromNameAndType(
                        // state store name
                        "mood",
                        // state store type
                        QueryableStoreTypes.keyValueStore()));
    }

    public void start() {
        Javalin javalinApp = Javalin.create().start(hostInfo.port());

        // Local key-value store query: all entries
        javalinApp.get("/mood", this::getAll);

        // Render all as html
        javalinApp.get("moodList", this::getAllHtml);
    }

    public void getAll(Context context) {
         KeyValueIterator<String, Double> moodIterator = getMood().all();
         Map<String, Double> moodByAirport = new HashMap<>();

         while(moodIterator.hasNext()) {
             KeyValue<String, Double> mood = moodIterator.next();
             moodByAirport.put(mood.key, mood.value);
         }

         moodIterator.close();
         context.json(moodByAirport);
    }

    public void getAllHtml(Context context) {
        KeyValueIterator<String, Double> moodByAirport = getMood().all();
        StringBuilder page = new StringBuilder();
        page.append("<html> <head><title>airqueue: Mood by airport</title>");
        page.append("<style type=\"text/css\">p font-family: Comic Sans MS;</style></head>");
        page.append("<body><h1>Mood by airport</h1>");
        page.append("<table><thead><th>Airport</th><th>Mood score (predicted delay)</th></thead>");
        page.append("<tbody>");
        while(moodByAirport.hasNext()) {
            KeyValue<String, Double> mood = moodByAirport.next();
            page.append("<tr><td>" + mood.key + "</td><td>" + mood.value + "</td></tr>");
        }
        moodByAirport.close();

        page.append("</tbody></table></body></html>");
        context.html(page.toString());
    }



}
