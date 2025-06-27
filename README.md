# Document Processor

Document Processor service to process text files from a specific directory.


## Run with Docker

Make sure the app is built first:

``` bash
mvn clean package
```

Build the image:

``` bash
docker build -t doc-proc-app .
```

Run the container:
``` bash
docker run -p 8080:8080 doc-proc-app
```

**Or use Docker Compose:**

``` bash
docker-compose up --build
```