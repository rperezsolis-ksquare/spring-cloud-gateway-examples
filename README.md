# Spring Cloud Gateway Examples

This project is a compilation of examples of almost all [Spring Cloud Gateway](https://cloud.spring.io/spring-cloud-gateway/reference/html/#gateway-starter) filters and predicates. The project is integrated by two servers, gs-gateway which contains the filters and predicates and demo-service which handles all the requests. 

Before you run the servers you must have [Consul](https://www.consul.io/) running, as it is necessary for service discovery.

The project has two [Rate limiting](https://en.wikipedia.org/wiki/Rate_limiting) implementations, one is builded with [token-bucket](https://github.com/bbeck/token-bucket) library, but it is kind of old, the other make use of [bucket4j](https://github.com/vladimir-bukhtoyarov/bucket4j) which is more recent.
