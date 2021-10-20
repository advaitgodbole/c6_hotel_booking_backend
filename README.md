# c6_hotel_booking_backend
A toy project to learn microservices architecture based backend development using Java Spring Boot

### Ubuntu env setup/commands that may be required to get the project to compile and work with Maven Wrapper

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
