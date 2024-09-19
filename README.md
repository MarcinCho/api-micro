# Api-Proxy

A project that aims to create an app with microservices in backend and single page application as a client. 
The goal of the project is to explore microservice architecture and get to know popular frameworks like Spring Boot and Quarkus without any guidance, as well as practice exception handling, logging and configuration.
[The project as a whole is still under development]
### Api-pull

A Quarkus microservice utilizing the GitHub API to perform CRUD operations on users and repositories.

The main objectives of this project were to:
- Utilize the Quarkus framework
- Implement Java's virtual threads
- Experiment with testing frameworks
- Utilize Jakarta's HTTP client
- Practice building microservices

## Tech Stack

- **ApiPull**: Quarkus, Maven
- **SecondMS**: Spring Boot, Maven
- **Client**: Vue, Primefaces, Tailwind

## Run Locally

### Requirements
- Docker 
- JDK 21+

### Run Everything
Coming soon...
For now, refer to the individual projects.

#### Api-pull

1. Clone the project:

    ```bash
    git clone https://github.com/MarcinCho/api-micro
    ```

2. Go to the project directory:

    ```bash
    cd api-pull
    ```

3. Start the Postgres container:

    ```bash
    sudo docker run --name apipull -p 5434:5432 -e POSTGRES_PASSWORD=apipullservice -e POSTGRES_USER=apipull -d postgres
    ```

4. Run the app with Maven:

    ```bash
    ./mvnw clean compile quarkus:dev
    ```

    **There might be an issue with permissions for running the Maven wrapper:**

    ```bash
    sudo chmod 777 mvnw
    ```

    Try to run the project once more.

    The project starts on port 8080.  
    The Dev UI with configuration will be available at [http://localhost:8080/q/dev/](http://localhost:8080/q/dev/).

## Features

### Api-pull
- GitHub API proxy
- Saving users to DB
- Multithreading

## API Reference

### Quick Start

#### Get user

```
http://localhost:8080/user/marcincho
```

#### Get repo

```
http://localhost:8080/repo/api-micro
```

Refer to http://localhost:8080/q/dev-ui/io.quarkus.quarkus-smallrye-openapi/swagger-ui to get all the functions for api-pull.

## Lessons Learned

I learned how to use Quarkus, the differences between it and Spring Boot, how to write tests, and how to implement virtual threads. I also learned how to use Jackson to get JSON data and map it to DTO/Entity, as well as how to use the HTTP client and Response instead of RESTResponse for more customizable responses.


## Roadmap

### Api-pull (MS)

- [ ] Add one-to-many relationship users → repos.
- [ ] postman collection
- [ ] extend tests

### SecondMs (MS)
- [ ] Create project in Spring Boot and connect it with chosen API.
- [ ] Add DB connection
- [ ] Try to utilize TDD

### gh-api (Client)
- [ ] Create project in Vue using primeFaces
- [ ] Implement CRUD operations for both services.
- [ ] Work on authentication.

### Other
- [ ] API gateway
- [ ] K3s working prototype
- [ ] E2E testing
- [ ] work on module naming.
- [ ] create docker compose file




