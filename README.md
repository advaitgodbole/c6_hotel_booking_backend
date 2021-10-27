# SweetHome hotel booking system implementation using a Java microservices-based architecture
A toy project to learn microservices architecture based backend development using Java Spring Boot

## System requirements
This project was developed on an Ubuntu machine. Therefore some of the steps to set up the environment will be different on Windows/MacOS. This document does not describe the setup and execution instructions for those platforms. However, in the author's experience it is straightforward to set it up on other OSs as well.

## Ubuntu env setup/commands that may be required to get the project to compile and work with Maven Wrapper

To start off, add the following environment variables:

Edit the `/etc/environment/` file and add the environment variables that store the user name and password of the MySQL instance:

Open the text editor: `sudo -H nano /etc/environment`

Then, add the following lines to the end of the file: 

`MYSQL_DB_USERNAME="_your_username_"`
`MYSQL_DB_PASSWORD="_your_password_"`

---
For every fresh compile and run step, stop and start the mysql service:

`sudo service mysql stop` 

and then

`sudo service mysql start`

It may be required to first kill existing running `mysql` processes. This can be done as below:

Find the PID of the running process:

`pgrep mysql`

This will output the PIDs of the processes to the command line. These processes will be killed as below.

`sudo kill _pid_`

---
Then, one may need to add `execute` permissions to the Maven Wrapper

`chmod +x mvnw`

Afterwards, use `sudo` to compile and run the project, otherwise it may not work. For example:

`sudo ./mvnw spring-boot:run`

To run the `notification-service` package, it needs to be packaged as an executable `jar`. The required `maven-assembly` plugin has been added to the project `pom`. To do so, from the project root, run the following `Maven` command:

`sudo mvn clean compile package`

Then to run the service, execute the following command:

`jar -cp _path_to_jar_ "consumer"`.

**NOTE:** The project folder is a git repo with a `main` branch and a `dev` branch. Please use the `main` branch code. If there are problem running the project, please download a clean copy from <https://github.com/advaitgodbole/c6_hotel_booking_backend>.
## Running the app(s)

The project folder contains individual microservices, one each for the services described in the problem statement given to us. To run the project, the above command will need to be executed in the root project folder of each individual project. **Ensure that the Eureka server service "`eureka-discovery-service`" is running before the "`booking-service`" and "`payment-service`" are started**.

### Steps to run the project
#### Assumptions

1. AWS RDS (and AWS EC2 if needed) have been set up and access lists/security groups properly configured.
2. Apache Kafka has been properly configured on the host machine. Apache Zookeeper and the Kafka server have been started up using the recommended command line tools (shell scripts).
3. The microservices are run on the ports given in the exercise document. Their individual configurations are self-explanatory and given in their resective `application.properties` files as always.
4. The database is configured as per the configuration given in the `application.properties` file of each microservice app. The main thing is that the MySQL database should be named `sweethome`.
5. The `notification-service` app that logs messages using Kafka is configured as per the KafkaConfig Java class in the code.

#### Design approach
1. **Eureka discovery server microservice**
    
    This microservice is straightforward so will not be described here.
2. **Booking and Payment services**

    The structure of these two microservices follows the more-or-less standard approach to app design. The project packages are in `/src/main/java/com/sweethome/bookingservice/` and `/src/main/java/com/sweethome/paymentservice/`. both services have some common packages - `controller`, `entity`, `repository`, `service` and `VO`. In addition, the `booking-service` has the `config` and `exceptions` packages. The `controller`, `entity`, `repository`, `service` packages each contain Java classes that have the respective controller, entity, repository and service logic embedded. 
    
    The `VO` package contains a dedicated `Value Object` which contains the entity definition for the `RestTemplate` object and its data - the `bookingId` and associated payment details. This `value object` is returned by the `POST` method that posts the payment details of a particular booking to the `transaction` endpoint of the `payment-service` and gets back the same in its response.

    All business logic is implemented in the service layer, in the `service` package of both microservices.

    **Exception handling:** This has been implemented in the `booking-service` microservice as per the requirements outlined in the exercise document. The design of this logic follows the steps teaught in class and therefore will not be explained in detail here. Two exceptions are thrown, one each for the two types of error required as per the exercise document; `Bad Request` and `Resource Not Found`. The exceptions are injected using the `AOP` design paradigm.

    The `config` package in the `booking-service` configures the URI of the Kafka Producer.
3. **Notification service**
    
    This is a regular Java project with Apache Kafka Consumer defined in the `Consumer.java` class. 
    
    This Consumer reads messages published to the `message` topic as defined in the `../service/BookingService.java` class in the `sendPaymentDetailsAndSaveBooking()` method.

    The consumer keeps running until the Kafka queue has unconsumed messages. Once the messages get consumed, the service exits after the time interval hardcoded in the `giveUp` variable (currently at 100 seconds). After this, it needs to be restarted manually, but the way it is designed, only new messages will start being read.