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

4. Run the main class `App` from the IDE.

## Endpoints

- Navigate to [localhost:8080](http://localhost:8080) to observe the messages processed by Kafka. Individual messages
  can be examined by topic.
  
- A Web UI showing results of the *FlightDataAnalysis* application is available as [localhost:7000/averagesList](http://localhost:7000/averagesList).