# University Directory & Platform

## Technology Stack

### Backend
- **Maven**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Hibernate**
- **PostgreSQL**
- **Google Maps API** (for displaying university locations)
- **OpenAI API** (for content generation)
- **Grafana, Loki, Prometheus, OpenTelemetry** (for monitoring and observability)

### Frontend
- **Next.js**
- **TanStack Query**
- **Tailwind CSS**
- **Shadcn/ui**

## Features

### Users
- Sign up and log in with JWT authentication
- Data validation for authentication and registration
- User session management

### University Directory
- Displays all universities in Romania
- Search and filter by city, field of study, etc.
- Google Maps API integration for university locations
- OpenAI API for content generation
- Every user can writes reviews

### Dashboard & Admin Features
- View **user statistics** and search trends
- Graphs and analytics on user interaction
- Monitoring via **Grafana, Loki, Prometheus, and OpenTelemetry**

## Setup Backend

1. Install dependencies using Maven:
   ```sh
   mvn clean install
   ```
2. Start required services using Docker:
   ```sh
   docker compose up -d
   ```
3. Configure `application.properties` with the necessary database and API settings.
4. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```

## Setup Frontend

1. Install dependencies:
   ```sh
   npm install
   ```
2. Start the development server:
   ```sh
   npm run dev
   ```

## Monitoring & Observability

- **Grafana**: Visualizes application metrics.
- **Loki**: Centralized logging.
- **Prometheus**: Metrics collection and alerting.
- **OpenTelemetry**: Distributed tracing and telemetry data.


