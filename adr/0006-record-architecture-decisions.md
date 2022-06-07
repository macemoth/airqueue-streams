# 1. SessionWindows for DSL Topology

Date: 2022-06-07

## Status

Accepted

## Context

ACAS events are received by the hundreds to thousands and we assume that a flight event is “landed” or ended by observing that the `on_ground` flag is set on the last event. However, it can occur that we do not observe such an event.

## Decision

As we have no way to determine the destination airport with the data given, we name the unknown origin- or destination airport “Unknown airport”. It can still be found with the time and flight number afterwards.

## Consequences

    - Clear distinction of the cases depending on the `on_ground` flag
    - Not using the longitude and latitude of events that have no `on_ground` flag on first or last time seen.
