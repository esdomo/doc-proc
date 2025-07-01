# Document Processor

Document Processor service to process text files from a specific directory.
Files are processed asynchronously and aggregated results are persisted once the job completes.

Aggregated statistics are stored in a database with word frequencies serialized as JSON.
The development profile uses an in-memory H2 database.
The production profile uses PostgreSQL. Docker Compose will build and start both the
application and the database.
Setup the DB connection through the provided `.env` file or environment variables.
Database schema migrations are handled automatically with Flyway when the application starts

## File Input Directory

Server reads `.txt` files from a configurable folder on the filesystem.

### Configure via `application.yml`:
```yaml
config:
  input-folder: ./input
```

## Trigger Processing

Start file processing via HTTP:

```bash
curl -X POST http://localhost:8080/process/start
```
The endpoint returns the started job's ID immediately while processing continues asynchronously.

# API Documentation

After starting the application, navigate to `http://localhost:8080/swagger-ui.html` to explore the available endpoints using Swagger UI. 
The raw OpenAPI specification is available at `http://localhost:8080/v3/api-docs`.

## Run with Docker

The easiest way to launch the service in production mode is via Docker Compose.
It builds the application image and starts a PostgreSQL database container.

``` bash
docker-compose up --build
```

Compose reads variables such as database credentials from the provided `.env`
file. The API will be available on port `8080` and PostgreSQL on port `5555`.