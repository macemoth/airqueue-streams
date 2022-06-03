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

4. Run the main classes `AcasApp`, `DelayApp` and `MoodApp` from the IDE.

To observe at the topics, messages and consumers, open [localhost:8080](http://localhost:8080). Individual messages can be examined by topic.


## Endpoints

- A Web UI showing results of the *FlightDataAnalysis* application is available at [localhost:7000/averagesList](http://localhost:7000/averagesList).

- A Web UI showing the results of the *AirportsSentiment* application is available at [localhost:7001/moodList](http://localhost:7001/moodList), which displays the mood of each airport. Further, _the mood-o-meter_ for a specific airport can be called at [localhost:7001/airport/:airport](http://localhost:7001/airport/:airport) by specifiying the _:airport_. 
