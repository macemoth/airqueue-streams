# airqueue-streams
```
              .------,
              =\      \
 .---.         =\      \
 | C~ \         =\      \
 |     `----------'------'----------,
.'     LI.-.LI LI LI LI LI LI LI.-.LI`-.
\ _/.____|_|______.------,______|_|_____)
                 /      /
               =/      /
              =/      /
             =/      /
      jgs    /_____,'
```

## Running Instructions

1. `git clone` this project

2. Open in your IDE (we use IntelliJ) and import as Maven Project, if not already done.

3. `cd` into the base directory and run `docker-compose up`

4. Run the main classes `AcasApp`, `DelayApp` or `MoodApp` from the IDE (they can be run independently, except `MoodApp`'s input stream is dependent on `DelayApp`).

5. Ingest test data by running the scripts `./scripts/test-acas.sh`, (for `AcasApp`) or `./scripts/test-delays.sh` (for `DelayApp` and `MoodApp`), respectively. For Windows, use the test scripts that have the `-win` postfix. 

To observe the topics, messages and consumers, open Kafka UI on [localhost:8080](http://localhost:8080). Individual messages can be examined by topic.


## Endpoints

- A Web UI showing results of the *FlightDataAnalysis* application is available at [localhost:7000/averagesList](http://localhost:7000/averagesList).

- A Web UI showing the results of the *AirportsSentiment* application is available at [localhost:7001/moodList](http://localhost:7001/moodList), which displays the mood of each airport.

-  To further inspect the above sentiment results, _the mood-o-meter_ for a specific airport can be called at [localhost:7001/airport/:airport](http://localhost:7001/airport/:airport) by specifiying the _:airport_. 

## Troubleshooting

Frequently observed errors:

- `Exception in thread "main" java.lang.NoClassDefFoundError: org/eclipse/jetty/security/SecurityHandler` when starting any application using Jetty.
We solved this problem by regenerating maven sources and reloading the Maven project.
  
- When using a Mac with ARM architecture, the containers sometimes needed restarts and we experienced best performance when using the x64 OpenJDK JVM (not ARM).

- When using Windows without WSL, you may need to execute `create-topics.sh` manually on the container running Kafka.
Furthermore, use the test scripts that have the `-win` postfix.
  
