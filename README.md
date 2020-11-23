# URL Lookup Service

This application works by using `Spring Boot 2.4.1`, `Maven`, `Junit4` & `MongoDB`

### Running Application:

To run this application, it's advised to use the Docker-Compose file to build the necessary containers automatically.
Please run the following commands:
```
./mvnw clean install
docker-compose up 
```

If you make any changes to the project and would like your containers to reflect them changes run the following:
```
./mvnw clean install
docker-compose up --build
```


### Running JUnit Tests:
The tests involve both functional unit tests and integration tests. Therefore, we need to set up a mongodb database either locally or 
using the official MongoDB docker image from DockerHub.

#### Running MongoDB Instance using Docker Container
To run tests using a MongoDB Docker container, run the following:
`docker run --name mongo -d mongo:latest`

Make sure the value of `spring.data.mongodb.host` within the application properties file in `src\test\resources\application.properties`
is:
 
 `spring.data.mongodb.host=mongo`

#### Running MongoDB Instance On Base O.S
- Install MongoDB on your existing operating system from the official website
- Change the value of `spring.data.mongodb.host=mongo` to `spring.data.mongodb.host=localhost`
