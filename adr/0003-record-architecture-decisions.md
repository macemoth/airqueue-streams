# 1. Streams and Tables for data topics 

Date: 2022-06-07

## Status

Accepted

## Context

The topics airlines and airports use the IATA code as key and only the most recent value of each key is relevant. The flight topic is keyed by airline through the joins.

## Decision

We decided to handle the data as follows: 
    - flights: is handled as a stream
    - airlines: is handled as a KTable
    - airports: is handled as a GlobalKTable

## Consequences

    - Sharding of the airlines KTable is no problem as the co-partitioning requirements are fulfilled (stream is keyed by airline).
    - No re-keying necessary for the join with airports (thanks to the GlobalKTable), but a mapper needs to be implemented.  
    -  As the key-value pairs of the airport and airline topics do not change frequently, the missing time synchronisation of the GlobalKTable is acceptable.
    - Although there are more airports than airlines, their keyspace is sufficiently small to remain on a GlobalKTable.

