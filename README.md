# Document Processor

Document Processor service to process text files from a specific directory.

## 📁 File Input Directory

Server reads `.txt` files from a configurable folder on the filesystem.

### 🔧 Configure via `application.yml`:
```yaml
config:
  input-folder: ./input
```

## 🐳 Run with Docker

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