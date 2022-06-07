# 2. Project (architecture) separation from Part I

Date: 2022-06-07

## Status

Accepted

## Context

For the first hand-in for this class we created a Kafka- and Camunda-based booking platform with a pseudo payment service that randomly accepted or rejected a customer’s payment for a flight booking. For the second part we wanted to use real-time streaming data of flights to update the customer’s bookings on current flight delay predictions or other notification worthy flight events. The implementation of the first part was not to the extent that we felt comfortable adding the second part directly to the code base of the booking platform.

## Decision

We decided to keep these two projects separately from each other for the time being and concentrate on the design decisions for the Kafka streams and processing without having to take into account the other project parts.  

## Consequences

This allows us to fully put focus on the quality of the second (separate) project, while still having a connection with the use case from the first project, however, without the setbacks and issues we faced there. 