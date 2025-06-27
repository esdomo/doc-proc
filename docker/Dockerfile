FROM eclipse-temurin:17.0.6_10-jdk-alpine

COPY target/doc-proc*.jar doc-proc.jar

ENTRYPOINT ["java", "-jar", "doc-proc.jar"]