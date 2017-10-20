# Hystrix Demo

## Table of Contents
<!-- MarkdownTOC autolink="true" bracket="round" -->

- [Overview](#overview)
- [Getting Started](#getting-started)
	- [Demo Servers](#demo-servers)
	- [Hystrix Dashboard](#hystrix-dashboard)
- [Hystrix Concepts](#hystrix-concepts)
- [API](#api)

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
**TODO**: Document implemented APIs in the `simple-api` and `hystrix` servers.
