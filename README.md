# RabbitMq Spring-Boot RPC/Worker-Queue Example
This project is about an aggregation of RPC & Worker-Queue patterns with RabbitMq together.
<br/> This aggregation supports producer with multiple workers.

### Build
    git clone ...
    mvn clean package

### Run
You should have Java 8, Maven, Docker and RabbitMQ 3.6.6 or above.RabbitMQ should be running on localhost:5672<br/>
You could run the project as Spring-Boot with multiple profiles<br/>
    
    mvn spring-boot:run -Dspring-boot.run.profiles=PROFILE_NAME
    
#### Available profiles:
- pub
- sub

### Clustering
You could run `docker-compose.yml` to create RabbitMq single instance locally.<br/>

    docker-compose up

### How to get it working

#### Run Workers
You should create at least two workers, otherwise you could not be able to monitor async works.<br/>
Just run following command multiple times.
    
    mvn spring-boot:run -Dspring-boot.run.profiles=sub

#### Run Workers
Just run following command once to create 'Publisher' instance.

    mvn spring-boot:run -Dspring-boot.run.profiles=pub
