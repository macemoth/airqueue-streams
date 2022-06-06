docker compose exec kafka1 bash -c "
  kafka-console-producer \
  --bootstrap-server kafka-1:9092 \
  --topic acas < acas.json"

