# tp-load-balancer

Load balancer service is a REST service which expose one endpoint </br>
* /route?id=${userid} - takes userid and return one of the weighted group defined in externally delivered properties file <br>

# Building
For generate jar file run<br>
- mvn package

# Running application

Application is spring boot repacked jar, which can be run using command:
* java -jar TP-LOAD-BALANCER-1.0-SNAPSHOT.jar --spring.config.location=file:{externally.delivered.properties}

# Properties format
Weighted groups should be defined as a touple in format
- groupsWeights={GroupName:'number', .... } - where number is percentage distribution of requests among groups divided by ten <br>
Properties should also specify consistent hashing buckets number, which currently have to has value 10.
- numberOfBuckets=10 <br>
Example of properties file content:<br>
<b>groupsWeights={GroupA:'2',GroupB:'2',GroupC:'4',GroupD:'2'}<br>
numberOfBuckets=10</b>

# Dockerization
Project has Dockerfile delivered. following command creates docker image:
* docker build -t docker_image_tag . <br>

Then run docker image with command:
- docker run -d -p 8080:8080 docker_image_tag <br>

When running on Windows service should be reached using address retrieved by command:
-  docker-machine ip default

# Usage
While service is running it can be reached with wget
- wget http://${ip}:8080/route?id={alphanumeric_id}

# Testing
Project contains functional test which verifies requests distribution among predefined, weighted groups <br>
FunctionalTestRunner - periodicaly reports requests distribution among groups. <br>
When running test it should also have --spring.config.location=file:{externally.delivered.properties} passed as a program parameter.
Example of report:<br>
<b>
functional.config.FakeClient : 20.346608898516912 percentage of traffic goes to group GroupA <br>
functional.config.FakeClient : 20.113314447592067 percentage of traffic goes to group GroupB <br>
functional.config.FakeClient : 39.27678720213298 percentage of traffic goes to group GroupC <br>
functional.config.FakeClient : 20.32994500916514 percentage of traffic goes to group GroupD <br>
</b>

# Performance tests
For performance tests <b>wrk</b> application was used. It can be retrieved from https://github.com/wg/wrk.<br>
In order to achieve different user id for each requests <b>loadtest.lua</b> script is delivered.<br>

Example of usage:
- wrk -t 2 -c 2 -d test_duration -s /path/to/loadtest.lua http://${ip}:8080</br>
    
# Performance tests results

Five test runs was performed on:<br>
CPU: Intel Core i5 72000<br>
     2 Cores 2,5GHz<br>
RAM: 8GB<br>

results:  https://docs.google.com/spreadsheets/d/1XC8VOSM4L2ZlT0494aP55feKyJ5BPECpg0vcNsmh1d4/edit?usp=sharing <br>

- Average throughput was: 11909 Req/sec 
- Average latency was 521.244 micro seconds

  






