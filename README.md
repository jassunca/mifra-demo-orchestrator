# Mifra Demo Application

A support Java application to demonstrate the use of the **[Mifra Microservice Framework](https://github.com/jassunca/mifra-core)** in a real-world scenario. Mifra is a framework designed to provide the foundation to a Saga-pattern microservice application.

This repository showcases how the framework manages decoupled component orchestration, utilizing Maven for dependency management and Docker for containerized deployment.

## Scenario

This demo simulates a simple orders system that receives and processes the order of a fictional item. To avoid stocking issues, a limit of 100 items per request is applied. This example is purposely kept simple so that the focus is the implementation of the Mifra framework.

The system presented in version 0.1.0 is composed of one request approval orchestrator and three participants that manage the business logic: 
- An analysis participant that verifies the amount of items ordered as valid, or in need of adjustment.
- A treatment participant that is called only if the order needs adjustment to correct it to the max valid value.
- An approval participant that validates the order and marks if it was adjusted or not.

## Documentation

This project's Javadocs, as well as detailed comments inside the project, explain how to use Mifra's resources for any project that uses it.

## How To Run

#### Requirements:
- git

To execute the demo application and see it in action, first clone this project locally using:

```
git clone https://github.com/jassunca/mifra-demo-orchestrator
```

The project includes configuration to run either locally or via a Docker container.

### Locally
#### Requirements:
- Java 21
- Maven

On a terminal window, navigate inside the local project folder and execute:

```
mvn compile exec:java
```

### Container
#### Requirements:
- Docker

The project includes a multi-stage Dockerfile that compiles the application and creates a ready-to-run container. On a terminal window, navigate inside the local project folder and execute:

```
docker build -t mifra-demo-orchestrator:latest .
```

Followed by:

```
docker run -p 8080:8080 mifra-demo-orchestrator:latest
```

## How to Use

Using a REST client of choice, send a GET or a POST request to the URL ``http://localhost:8080/order/request`` with a body in JSON format that contains only the key ``noOfOrders`` and a positive numeric value, as per the format:

```
{
  "noOfOrders": 10
}
```
(replace 10 with the amount you wish to test).

For a quick test, a curl command can be executed to obtain a quick response, via:

```
curl -X POST http://localhost:8080/order/request -H "Content-Type: application/json" -d '{"noOfOrders": 10}'
```
(replace 10 with the order amount you wish to test).

Any order of 100 items or fewer will return this type of response:

```
{
    "decision": "Approved",
    "details": "The order of 10 units has been approved."
}
```

If the value ordered is greater than 100, this is the obtained response:

```
{
    "decision": "Approved",
    "details": "The order of 101 units is not possible, 100 units were ordered instead."
}
```