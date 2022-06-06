echo "Waiting for Kafka to come online..."
cub kafka-ready -b kafka-1:9092 1 20
# create the flights topic for incoming flight stream
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic flights \
  --replication-factor 2 \
  --partitions 1 \
  --create
# create the flights topic for incoming flight stream
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic flights-pseudo \
  --replication-factor 2 \
  --partitions 1 \
  --create
# create the airlines topic
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic airlines \
  --replication-factor 2 \
  --partitions 1 \
  --create
# create the airports topic
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic airports \
  --replication-factor 2 \
  --partitions 1 \
  --create
  # create the tracked topic for those flights we want to observe
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic tracked \
  --replication-factor 2 \
  --partitions 1 \
  --create
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic airport-delay \
  --replication-factor 2 \
  --partitions 1 \
  --create
kafka-topics \
  --bootstrap-server kafka-1:9092 \
  --topic acas \
  --replication-factor 2 \
  --partitions 2 \
  --create
sleep infinity
