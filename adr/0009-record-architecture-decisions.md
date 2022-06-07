# 9. Performance and scalability

Date: 2022-06-07

## Status

Accepted

## Context

Currently, our applications run on a single Kafka broker, and all topics have only one partition. This both does not exploit Kafka’s ability to scale horizontally, and does ignore future scaling problems. We expect the ACAS topic and the ACAS topology to be a bottleneck, as they deliver and process most events. 

## Decision

We decided to make changes to our application in order to make it more performant and scalable (see `multi` branch).

## Consequences

* We introduced a second Kafka broker, set the replication factor of all topics to 2 and allow the acas topic to have two partitions
* We implemented a custom partitioner to delegate incoming acas events to a desired partition. This opens the possibility to balance incoming events more evenly. For example, we could create stream processing instances specialised of particular flight numbers, airlines or locations.
