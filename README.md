README.md## Generate an application

Use the spring initializer to generate a chatgpt app

https://start.spring.io/

IMAGE

Extract the zip archive locally

## Create a Dockerfile

```dockerfile
#
# Build stage
#
FROM maven:3.6-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-ea-17-jre-slim
COPY --from=build /home/app/target/chatgpt-0.0.1-SNAPSHOT.jar /usr/local/lib/chatgpt.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/chatgpt.jar"]

```
Note

This example uses a multi-stage build. The first stage is used to build the code. The second stage only contains the built jar and a JRE to run it (note how jar is copied between stages).

## Build the image

```sh
docker build -t chatgpt .
```

## Run the image
```sh
docker run --rm -v "$PWD":/home/app/file.csv -p 8080:8080 -it chatgpt:latest
```

