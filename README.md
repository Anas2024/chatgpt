README.md
# Generate the application


## Decisions made :
```dockerfile

1.OS used to test the app: Ubuntu 22.04.2 LTS
2.Framework - We will use Spring Boot 3.0.3, as it provides a robust and efficient framework for building microservices.
3.Precompiler - We will not use any precompilers.
4.Project architecture - We will follow a layered architecture, with a controller layer for handling requests, a service layer for business logic, and a data access layer for accessing the CSV file.

Instructions to run the code:

1. Clone the Git repository: git clone https://github.com/Anas2024/chatgpt.git
2. Navigate to the project directory: cd chatgpt
3. You will need to create an account with ChatGPT (you can use your gmail account)
	Url: https://beta.openai.com/
	After you signup and sign in create an API key that you will use to communicate with the AI’s
	API endpoint (https://beta.openai.com/account/api-keys)

4. go to application.properties and modify the property 'apiKey' and enter your key (example: apiKey=yourApiKey)
5. Build the Docker image: docker build -t chatgpt .
6. Run the Docker container: docker run --rm -v "$PWD":/home/app/ -p 8080:8080 -it chatgpt:latest 
   Note: you can replace the current path "$PWD" with the path to the folder where you want to store the CSV file on your host machine after the docker container is stopped(so that the file.csv survives after the container is stopped).
7. Access the Swagger UI by navigating to http://localhost:8080/swagger-ui/index.html
8. Send a POST request to the /chatgpt endpoint with the following JSON payload: { "question": "What is gluten sensitivity?" }
   The response will contain the answer provided by ChatGPT and the pair Question/answer is stored in file.csv.
9. If you want to test the application on IDE intellej or eclipse you can remove the block in comment in the main of the class IzicapChatGptProject1Application.java

Notes:

We have used the OpenAI Java SDK to interact with the ChatGPT API.
We have provided a Dockerfile for building the Docker image.
We have provided unit and integration tests for the microservice.
```

## Dockerfile

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
## Change Api key 
Go to src/main/resources/application.properties and modify the property 'apiKey' and enter your key (Example: apiKey=yourApiKey)
###application.properties
```sh
# server port
server.port = 8080

# chatGpt api key 
apiKey=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

#api url 
apiUrl=https://api.openai.com/v1/completions

#api model
apiModel=text-davinci-003

#path of csv file
csvPath=/home/app/file.csv

#actuator server port
management.server.port = 9000

#disable security for endpoints of actuator 
management.security.enabled = false

#include all endpoints of actuator
management.endpoints.web.exposure.include=*

```
Note

This example uses a multi-stage build. The first stage is used to build the code. The second stage only contains the built jar and a JRE to run it (note how jar is copied between stages).

## Build the image

```sh
docker build -t chatgpt .
```

## Run the image
```sh
docker run --rm -v "$PWD":/home/app/ -p 8080:8080 -it chatgpt:latest
```
## Unit Tests
```sh
To execute tests, simply go to src/test/java in the project and run as Junit test the class QuestionAnswerControllerTest.java
```

## Actuator
```sh
Actuator run on port 9000, to acces to actuator endpoints use http://localhost:9000/actuator
Example:  http://localhost:9000/actuator/health
```

