# Hystrix Demo

## Table of Contents
<!-- MarkdownTOC autolink="true" bracket="round" -->

- [Overview](#overview)
- [Getting Started](#getting-started)
	- [Demo Servers](#demo-servers)
	- [Hystrix Dashboard](#hystrix-dashboard)
- [Hystrix Concepts](#hystrix-concepts)
- [API](#api)
	- [Simple API Server](#simple-api-server)
	- [Hystrix Server](#hystrix-server)
- [Resources](#resources)

<!-- /MarkdownTOC -->

## Overview

This is a simple demonstration of Hystrix concepts in Spring Boot applications. It includes two REST API applications implemented using Spring Boot. The first project in the `simple-api` folder is a simple server with a few endpoints with various behaviors. The other project in the `hystrix` folder is another server with a dependency on the `simple-api` server. It provides endpoints that retrieve and return results from the `simple-api` server. Some of these APIs directly delegate the call to the `simple-api` server and others make use of hystrix in the network calls to the `simple-api` server to improve fault tolerance.

## Getting Started

### Demo Servers
Follow the following steps to get the two demo servers up and running.

#### Clone this repository
```
	git clone https://github.com/zfesseha/hystrix-demo
	cd hystrix-demo
```

#### Build and run the `simple-api` server
From the top level `hystrix-demo` directory, run the following:
```
	cd simple-api
	./mvnw clean install
	./mvnw spring-boot:run
```

If all operations succeed, the server will be available at http://localhost:8080/.

#### Build and run the `hystrix` server
From the top level `hystrix-demo` directory, run the following.
```
	cd hystrix
	./mvnw clean install
	./mvnw spring-boot:run
```

If all operations succeed, the server will be available at http://localhost:8181/.

### Hystrix Dashboard
One of the useful features Hystrix provides is a Dashboard to monitor activities in a Hystrix enabled server. The dashboard can be cloned and run from the Netflix repository and can monitor multiple event streams emitted from Hystrix enabled servers. The hystrix server in this project is already configured to emit event streams at http://localhost:8181/hystrix.stream.

#### Clone and run the Hystrix Dashboard
**Note**: Clone this repository to a separate folder from the demo project just to avoid git history mix up.
```
	git clone https://github.com/Netflix/Hystrix.git
	cd Hystrix/hystrix-dashboard
	../gradlew appRun
```
If all operations succeed, the dashboard will be available at http://localhost:7979/hystrix-dashboard. Type in http://localhost:8181/hystrix.stream in the text box, and click "Add Stream" to register the event stream to the dashboard. Click on "Monitor Stream" to display the monitoring dashboard.

## Hystrix Concepts
**TODO**: Describe/Explain hystrix concepts implemented in this project and provide examples.

## API
### Simple API Server
| Endpoint | Description | Link to Endpoint |
| --------------- | ----------------- | -------------------|  
| /api/v1/greeting | A simple endpoint that returns a greeting based on a predefined list of greetings and names. | http://localhost:8080/api/v1/greeting |
| /api/v1/triple-throttled/{number} | A simple endpoint that takes an integer (number) and returns the triple of it (3 * number). This endpoint can only return the triple of a certain number once within a 5 second window. That is, once a request is made to triple a number, all subsequent calls to triple the same number within the next 5 seconds return an error. | http://localhost:8080/api/v1/triple-throttled/6 |
| /api/v1/triple-wait/{number} | An endpoint that also returns a triple of a number but waits before it returns the result. The amount of time it waits is a random amount of milliseconds between 0 and 7000 (7 seconds). | http://localhost:8080/api/v1/triple-wait/7 | 


### Hystrix Server
The Hystrix server has two sets of APIs under `api/v1/base` and `api/v1/hystrix`. Both delegate their calls to the simple API server. The difference is that all endpoints under `api/v1/base` directly delegate calls to the simple-api server without any fault-tolerance or circuit breaking logic. On the other hand, endpoints under `api/v1/hystrix` wrap all calls to the simple-api server in `HystrixCommand`. All responses from the `api/v1/base` endpoints are returned in the same format as they come from the simple-api server. All responses from the `api/v1/hystrix` endpoints are wrapped in a simple `Response` DTO with some hystrix metadata, like whether or not the response was from a fallback strategy.

#### `api/v1/base`
| Endpoint | Description | Link to Endpoint |
| /api/v1/base/greeting | A proxy to the `/api/v1/greeting` endpoint in the simple-api server. | http://localhost:8181/api/v1/base/greeting | 
| api/v1/base/triple-all?numbers={numbers} | An endpoint that takes a comma separated list of numbers as a query parameter and calls the `/api/v1/triple-throttled/{number}` endpoint on the simple-api server and returns a list containing the results. | http://localhost:8181/api/v1/base/triple-all?numbers=1,2,3,4,5,6,7,8 | 
| /api/v1/base/triple-wait/8 | A proxy to the `/api/v1/greeting` endpoint in the simple-api server. | http://localhost:8181/api/v1/base/triple-wait/8 | 

#### `api/v1/hystrix`
| Endpoint | Description | Link to Endpoint |
| TODO | TODO | TODO | 

## Resources
- https://github.com/Netflix/Hystrix
- https://github.com/Netflix/Hystrix/wiki
