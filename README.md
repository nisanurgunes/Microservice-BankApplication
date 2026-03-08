# Bank Application Microservices

Spring Boot microservices project with Config Server + Eureka + three business services:
- `accounts`
- `cards`
- `loans`

## Tech Stack
- Java 21
- Spring Boot 3.2.4
- Spring Cloud 2023.0.0
- Maven Wrapper (`mvnw`)
- MySQL
- Docker Compose (optional)

## Project Structure
- `accounts/` - Accounts service
- `cards/` - Cards service
- `loans/` - Loans service
- `configserver/` - Centralized configuration service
- `eurekaserver/` - Service discovery
- `config-repo/` - Externalized environment configs
- `docker-compose/staging/` - Staging compose setup

## Default Ports
- Config Server: `8071`
- Eureka Server: `8070`
- Accounts Service: `8080`
- Loans Service: `8090`
- Cards Service: `9000`

MySQL ports used in local profile:
- `accountsdb`: `3307`
- `cardsdb`: `3308`
- `loansdb`: `3309`

## Run Locally (Recommended for Development)
The service defaults are configured for `local` profile in `accounts`, `cards`, and `loans`.

### 1) Start databases
Start only DB containers:

```bash
docker compose -f docker-compose/staging/docker-compose.yml up -d accountsdb cardsdb loansdb
```

### 2) Start infrastructure services
Run in separate terminals:

```bash
cd configserver
./mvnw spring-boot:run
```

```bash
cd eurekaserver
./mvnw spring-boot:run
```

### 3) Start business services
Run each in separate terminals:

```bash
cd accounts
./mvnw spring-boot:run
```

```bash
cd cards
./mvnw spring-boot:run
```

```bash
cd loans
./mvnw spring-boot:run
```

## Run Full Stack with Docker Compose
From project root:

```bash
docker compose -f docker-compose/staging/docker-compose.yml up -d
```

## Health Checks
- Config Server: `http://localhost:8071/actuator/health`
- Eureka Server: `http://localhost:8070/actuator/health`
- Accounts: `http://localhost:8080/actuator/health`
- Loans: `http://localhost:8090/actuator/health`
- Cards: `http://localhost:9000/actuator/health`

## Common Issue
If you see `UnknownHostException: cardsdb` or `UnknownHostException: loansdb` while running from IDE:
- You are using a Docker hostname outside Docker network.
- Use `local` profile and `localhost` DB URLs (already configured in `application-local.yml` files).
- Make sure MySQL is running on the expected local ports (`3308`, `3309`).

## Build
Build all modules from root:

```bash
./mvnw clean install
```
