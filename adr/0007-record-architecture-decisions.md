# 7. Grouping for Airport Delay Topology

Date: 2022-06-07

## Status

Accepted

## Context

During the implementation of the airport delay calculation, we figured out that we need to actually differentiate between the two airports a flight data is enriched with, namely the departure and the arrival airport. This is because every flight is used twice for computing the average delay: once for its information about the origin airport, once for the destination airport. For example, a flight from LAX to JFK is used to compute delays of LAX and JFK. For LAX its origin delay is relevant, but the arrival delay is not.

## Decision

We decided to do the window-aggregate operation twice, once for origin delays and once for destination delays.

## Consequences

* The aggregates contain irrelevant information (e.g. the average origin delay of all flights landing in JFK), which can be ignored
* Two tables are produced, which are joined again in order to have only the desired origin and destination delays.