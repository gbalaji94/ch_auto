# ch_auto

### Summary: 
A web automation project to automate Amazon.in and select a television with second-highest cost.

### Framework features:
1. BDD using cucumber.
2. Integrated with 2 reporters - Extent and Cucumber Cloud.
3. Loggers.
4. Multi-browser support, fully test driven.
5. Platform independent
6. Docker with selenium Grid 4
7. Video session integration in the selenium grid dashboard.

### Result
The second highly priced Samsung TV will be printed on the console as well as the same will be attached as a screenshot to the cucumber report 

### Libraries used:
1. TestNG
2. Cucumber
3. Spring
4. Selenium 4

### Build Tool: 
 Maven

### Getting Started ###

### Prerequisites

What you need to install before running the project.
```
1. Java 11
2. Maven
3. Docker
```

### Plugins to be installed in Intellij
```
1. Cucumber for Java
2. Lombok
```

### Starting Selenium Hub through docker
* To execute this docker-compose yml file use `docker-compose -f docker-compose-ch-auto.yml up`
* To stop the execution, hit Ctrl+C, and then `docker-compose -f docker-compose-ch-auto.yml down`
* Hit `http://localhost:4444/` on your favorite browser.

### To run your project
1. Through maven `mvn clean install`
2. Alternatively, you can also run by executing the `testNG.xml`

### To view the test executions
1. Go to `http://localhost:4444/`
2. Click on sessions.
3. Click on the video camera icon.
4. Password will be `secret`

### To get detailed logs for the executions
1. Open the `src/test/resources/log4j.properties` file
2. In line no. 3, update `INFO` to `DEBUG`

### To view the reports
This automation supports 2 reporters
1. Extent report - It can be found after the execution under the test-output directory.
2. Cloud Cucumber report - A link will be generated and printed on the console, click on the link to view the report.
