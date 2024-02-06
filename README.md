# Getting Started

This is a basic Spring Boot application.

##EXECUTION

- Compile the project with mvn clean install
- Generate the docker image using DockerFile: docker build -t test-w2m .
- Run the container using docker-compose file: docker compose up -d
- Check the available endpoints sending request. NOTE: the request must be authenticated, check the credentials in properties file

There are many ways to check the endpoints and send requests:
- Using cURL: curl -X GET "user:password" "http://localhost:8099/super_hero"
- Using the swagger API: http://localhost:8099/swagger-ui/index.html
- Using POSTMAN or similar

