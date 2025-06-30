# Document Processor

Document Processor service to process text files from a specific directory.
Files are processed asynchronously and aggregated results are persisted once the job completes.

Aggregated statistics are stored in a database with word frequencies serialized as JSON.
The development profile uses an in-memory H2 database.
The production profile uses PostgreSQL which is started via Docker Compose.  
Provide the `DB_PASSWORD` value through a `.env` file or environment variable.

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


## Run with Docker

Make sure the artifact is built first:

``` bash
mvn clean package
```

Build the image:

``` bash
docker build -f docker/Dockerfile -t doc-proc-app .
```

Run the container:
``` bash
docker run -p 8080:8080 doc-proc-app
```

**Or use Docker Compose:**

``` bash
docker-compose up --build
```