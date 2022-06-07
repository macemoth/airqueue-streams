# 5. Additional DSL ACAS topology

Date: 2022-06-07

## Status

Accepted

## Context

The Processor API solves the ACAS case relatively well. However, for evaluation we would like to solve the use case with the DSL.

## Decision

We decided to implement a DSL version of the topology (AcasTopologyDSL), where we make the assumption that if no ACAS events are received from a specific flight anymore for 15 minutes, the flight is completed. This assumption allows us to operate with a SessionWindow.

## Consequences

DSL topology, which is interchangeable with the Processor API topology.