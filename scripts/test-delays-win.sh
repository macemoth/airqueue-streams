# Use when using Windows without WSL
winpty docker-compose exec kafka bash -c "
  kafka-console-producer \
  --bootstrap-server kafka:9092 \
  --topic airports \
  --property 'parse.key=true' \
  --property 'key.separator=|' < airports.json"
winpty docker-compose exec kafka bash -c "
  kafka-console-producer \
  --bootstrap-server kafka:9092 \
  --topic airlines \
  --property 'parse.key=true' \
  --property 'key.separator=|' < airlines.json"
winpty docker-compose exec kafka bash -c "
  kafka-console-producer \
  --bootstrap-server kafka:9092 \
  --topic flights < flights.json"