# Device Manger

This project is a Spring Boot application developed in Java. It provides a RESTful API for managing devices.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 11 or higher
- Maven

### Building the Project

A Makefile is provided to build, run, and test the project. You can use the following commands:

- To build the project, run: `make build`
- To run the project, run: `make run`
- To test the project, run: `make test`

### Running the Server

The server will run on `http://localhost:8080/`.

### API Documentation

Swagger API is integrated into the project. You can view the API usage at `http://localhost:8080/swagger-ui/index.html#/`.

### API Endpoints

All API endpoints are protected with Basic Authentication. You need to provide a valid username and password to access them.

Here are the curl requests for each API:

- Get all devices:
  ```
  curl -u username:password -X GET 'http://localhost:8080/devices?page=0&size=10'
  ```

- Get device by ID:
  ```
  curl -u username:password -X GET 'http://localhost:8080/devices/{id}'
  ```

- Add a new device:
  ```
  curl -u username:password -X POST 'http://localhost:8080/devices' -H 'Content-Type: application/json' -d '{ "name": "Device Name", "brand": "Device Brand" }'
  ```

- Update a device:
  ```
  curl -u username:password -X PUT 'http://localhost:8080/devices/{id}' -H 'Content-Type: application/json' -d '{ "name": "New Device Name", "brand": "New Device Brand" }'
  ```

- Delete a device:
  ```
  curl -u username:password -X DELETE 'http://localhost:8080/devices/{id}'
  ```

- Search devices by brand:
  ```
  curl -u username:password -X GET 'http://localhost:8080/devices/search?brand=Brand&page=0&size=10'
  ```

### Roles and Credentials

The application has the following roles defined:

- `ADMIN`: Has access to all GET, POST, PUT, and DELETE endpoints.
    - Username: `admin`
    - Password: `admin`

- `MAINTAINER`: Has access to all GET, POST, and PUT endpoints.
    - Usernames: `maintainer1`, `maintainer2`
    - Password: `maintainer`

- `VIEWER`: Has access to all GET endpoints.
    - Usernames: `viewer1`, `viewer2`, `viewer3`
    - Password: `viewer`

### Postman Collection

postman collection is also added to the project at with name postman_collection.json