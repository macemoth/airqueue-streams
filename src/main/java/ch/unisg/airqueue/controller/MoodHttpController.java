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

        // Get meter for specific airport
        javalinApp.get("/airport/:airport", this::getForAirport);
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
        page.append("<style type=\"text/css\">body {font-family: \"Comic Sans MS\";}</style></head>");
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

    public void getForAirport(Context context) {
        String airportCode = context.pathParam("airport");
        Double mood = 50 + 1.2 * getMood().get(airportCode);
        if (mood == null) {
            mood = 0.0;
            airportCode = "Unknown airport";
        }
        context.html(getHtmlForMoodScore(airportCode, mood.intValue()));
    }

    private String getHtmlForMoodScore(String airport, int value) {
        return
                "<html>\n" +
                        "<head>\n" +
                        "    <title>Mood score for + " + airport + "</title>\n" +
                "    <style type=\"text/css\">body {font-family: \"Comic Sans MS\";</style>\n" +
                "    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js\"></script>\n" +
                "    <script src=\"https://code.highcharts.com/highcharts.js\"></script>\n" +
                "    <script src=\"https://code.highcharts.com/highcharts-more.js\"></script>\n" +
                "    <script src=\"https://code.highcharts.com/modules/solid-gauge.js\"></script>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <h1>Mood score for " + airport + "</h1>\n" +
                "    <div id=\"container\" style=\"height: 300px;\">\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "<script>\n" +
                "    $(function() {\n" +
                "\n" +
                "var rawData = " + value + ",\n" +
                "  data = getData(rawData);\n" +
                "\n" +
                "function getData(rawData) {\n" +
                "  var data = [],\n" +
                "    start = Math.round(Math.floor(rawData / 10) * 10);\n" +
                "  data.push(rawData);\n" +
                "  for (i = start; i > 0; i -= 10) {\n" +
                "    data.push({\n" +
                "      y: i\n" +
                "    });\n" +
                "  }\n" +
                "  return data;\n" +
                "}\n" +
                "\n" +
                "Highcharts.chart('container', {\n" +
                "  chart: {\n" +
                "    type: 'solidgauge',\n" +
                "    marginTop: 10\n" +
                "  },\n" +
                "  \n" +
                "  title: {\n" +
                "    text: ''\n" +
                "  },\n" +
                "  \n" +
                "  subtitle: {\n" +
                "    text: rawData,\n" +
                "    style: {\n" +
                "      'font-size': '60px'\n" +
                "    },\n" +
                "    y: 200,\n" +
                "    zIndex: 7\n" +
                "  },\n" +
                "\n" +
                "  tooltip: {\n" +
                "    enabled: false\n" +
                "  },\n" +
                "\n" +
                "  pane: [{\n" +
                "    startAngle: -120,\n" +
                "    endAngle: 120,\n" +
                "    background: [{ // Track for Move\n" +
                "      outerRadius: '100%',\n" +
                "      innerRadius: '80%',\n" +
                "      backgroundColor: Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0.3).get(),\n" +
                "      borderWidth: 0,\n" +
                "      shape: 'arc'\n" +
                "    }],\n" +
                "    size: '120%',\n" +
                "    center: ['50%', '65%']\n" +
                "  }, {\n" +
                "    startAngle: -120,\n" +
                "    endAngle: 120,\n" +
                "    size: '95%',\n" +
                "    center: ['50%', '65%'],\n" +
                "    background: []\n" +
                "  }],\n" +
                "\n" +
                "  yAxis: [{\n" +
                "    min: 0,\n" +
                "    max: 100,\n" +
                "    lineWidth: 2,\n" +
                "    lineColor: 'white',\n" +
                "    tickInterval: 10,\n" +
                "    labels: {\n" +
                "      enabled: false\n" +
                "    },\n" +
                "    minorTickWidth: 0,\n" +
                "    tickLength: 50,\n" +
                "    tickWidth: 5,\n" +
                "    tickColor: 'white',\n" +
                "    zIndex: 6,\n" +
                "    stops: [\n" +
                "      [0, '#fff'],\n" +
                "      [0.101, '#0f0'],\n" +
                "      [0.201, '#2d0'],\n" +
                "      [0.301, '#4b0'],\n" +
                "      [0.401, '#690'],\n" +
                "      [0.501, '#870'],\n" +
                "      [0.601, '#a50'],\n" +
                "      [0.701, '#c30'],\n" +
                "      [0.801, '#e10'],\n" +
                "      [0.901, '#f03'],\n" +
                "      [1, '#f06']\n" +
                "    ]\n" +
                "  }, {\n" +
                "    linkedTo: 0,\n" +
                "    pane: 1,\n" +
                "    lineWidth: 5,\n" +
                "    lineColor: 'white',\n" +
                "    tickPositions: [],\n" +
                "    zIndex: 6\n" +
                "  }],\n" +
                "  \n" +
                "  series: [{\n" +
                "    animation: false,\n" +
                "    dataLabels: {\n" +
                "      enabled: false\n" +
                "    },\n" +
                "    borderWidth: 0,\n" +
                "    color: Highcharts.getOptions().colors[0],\n" +
                "    radius: '100%',\n" +
                "    innerRadius: '80%',\n" +
                "    data: data\n" +
                "  }]\n" +
                "});\n" +
                "});\n" +
                "</script>\n" +
                "\n" +
                "</html>\n" +
                "\n";
    }


}
