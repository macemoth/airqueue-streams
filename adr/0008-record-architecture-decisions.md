# 1. Avro

Date: 2022-06-07

## Status

Accepted

## Context

Initially, we used Avro for all data models without registry. However, we weren’t able to use nested classes in Avro, which we make heavy use for data models in joins. The schema indicates no possibility for nesting, and solutions on forums did not work with the Java code auto-generation (https://avro.apache.org/docs/current/spec.html\#schema_complex). 

## Decision

All data model classes with the exception of Airline are not offered via Avro schemes, but as plain Java classes.

## Consequences

    - Without avro registry our application scales worse and increased code dupplication is given when other Kafka streams applications are added
    - JsonSerdes must implement se- and deserialisers for the classes
    - In this project, we have fewer dependencies to Avro
    - We can nest data model classes