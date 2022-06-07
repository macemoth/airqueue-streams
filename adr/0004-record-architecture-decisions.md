# 4. Processor API for ACAS events

Date: 2022-06-07

## Status

Accepted

## Context

For the ACAS topology, a flight can consist of hundreds to thousands of ACAS events (in our data, a flight usually only consists of dozens). The subsummation of ACAS events to a flight calls for a windowing-type operation.

## Decision

As the regular window operations work with fixed time frames and even custom extensions of the Windows class are not flexible enough, we decided to use Kafka’s Processor API for this case (AcasTopologyProcessorApi). 

## Consequences

It is challenging to identify the start and end of the flight, but we simplify the problem by assuming that the first event for a flight number received marks the beginning of the observed flight and the last event marks the end.