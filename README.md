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

Before calling any of the endpoints, you should look to add some known malware domains to the database beforehand to be able to perform checks.
You can find the endpoint below to do this.

### Endpoints:
The project works by using Rest endpoints defined in Spring Boot. These can be reached by either a simple curl or using something like Postman.


```
GET
/api/urlinfo/1/{hostname_port}/{original_query}

Example: /urlinfo/1/www.google.com:443/https://www.google.com/flower.jpeg
Returns: {'Domain': "Google.com", 'Malware': "false"}
```

```
GET
/api/get_all_known_domains

Example: /api/get_all_known_domains
Returns: [{'ID': "123456789", 'Hostname': "malware.io", 'Port': "80"},
          {'ID': "109876543", 'Hostname': "malware.com", 'Port': "8080"},
          {'ID': "345783456", 'Hostname': "malware.co.uk", 'Port': "22"}]
```

```
GET
/api/add_malware_domain

Example: /api/add_malware_domain
Request Body: {"hostname": "www.Ransomeware.com/q?=35/image.gif", "port": "443"}
Returns: {'ID': "123456789", 'Hostname': "Ransomeware.com", 'Port': "443"}
```

```
GET
/actuator

Returns: {"_links":{"self":{"href":"http://localhost:8080/actuator","templated":false},"health":{"href":"http://localhost:8080/actuator/health","templated":false},"health-path":{"href":"http://localhost:8080/actuator/health/{*path}","templated":true},"info":{"href":"http://localhost:8080/actuator/info","templated":false}}}
```

```
GET
/actuator/health

Returns: {"status":"UP"}
```

### Running JUnit Tests:
The tests involve both functional unit tests and integration tests. Therefore, we need to set up a mongodb database either locally or 
using the official MongoDB docker image from DockerHub. For the sake of network config simplicity I have decided to go with running the MongoDB instance locally.

#### Running MongoDB Instance On Base O.S
- Install MongoDB on your existing operating system from the official website
- Ensure the value of `spring.data.mongodb.host` is set to `spring.data.mongodb.host=localhost`

To Run Tests: `./mvnw test`

