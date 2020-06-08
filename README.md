## Candidate

**Post Applied:** Head of Engineering Asia / Technical Lead - IT Engineering

#### Contact
**Name:** Thompson Shi

**Email:** thompsonshi@gmail.com

**Mobile No.:** 852 9720 9034

------------------------------------------------------------------------


## Quick Summary
Realize the market trade processor which consumes trade messages via an endpoint, processes those messages in some way and delivers a frontend of processed information based on the consumed messages, includes:
  
  **i) Message Consumption**

  - The controller in Spring Boot as Singleton pattern to provide the endpoint for the API clients.
   
  - The messages are consumed in RabbitMQ, in fact, the Strategy pattern is used, different brands of MQ services can be pluggable, e.g. ActiveMQ, Hazelcast, Kafka, RabbitMQ, ZeroMQ, etc.

  - Also, the Factory pattern is used about the determining what MQ services is created in run-time.

  - As extensibility and scalability, the exchange component as gateway in ActiveMQ is ready to extend. For all the Post endpoint clients from different countries who send messages to the application, the exchange is responsible for sending the messages to the dedicated queue of each originating county.

  **ii) Message Processor**

  - The messages are validated by specific logic and stored in MongoDB.
   
  - 3 types of dimensions of trade results as a service is implemented. For presenting the different dimensions of trade results to UI, mobile app, the Strategy pattern is used. So, any additional dimension of trade result as a service can be implemented later on.

  - Also, the Factory pattern is used about the determining what dimensions of trade result service is created in run-time.

  **iii) Message Frontend**

  - The mentioned 3 types of dimensions are shown on frontend as below:

    - Summary of Top 5 Country Trades
    - Summary of Top 10 Country Trades with Trading Pairs
    - Summary of Latest 10 Trades

  - Web Socket is implemented as Publisher-Subscriber pattern. the web socket clients as subscriber to be notified by the new messages, that is, any messages sent by any other users are aggregated and processed in the application, as publisher then pushs to the subscriber on frontend.


## Technology Used
```
  Java 11
  Spring boot 2.2.6
  RabbitMQ 3.8.3
  MongoDB 4.2.5
  WebSocket 2.3.3
  jQuery 3.1.1-1
  Maven 4.0.0
  Swagger2 2.9.2
```  


## How to Get Start

#### Frontend Endpoint

<http://15.165.158.139:8080/index.html>



#### POST Endpoint

```
http://15.165.158.139:8080/fair/trade 
```

    E.g. by curl:

    curl -i -X POST -H 'Content-Type: application/json' --header 'Accept: application/json' -d '{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"} ' 'http://15.165.158.139:8080/fair/trade'


#### API Doc

<http://15.165.158.139:8080/swagger-ui.html#/>


#### Assumptions
Different validation rules of all the pararmeters should be implemented. The POST endpoint now is just validated all parameters are mandatory.

The below "originatingCountry" are assumed to support:
  ```
  "NO", "AU", "NL", "US", "NZ", "CA", "IE", "LI", "DE", "SE",
  "CH", "JP", "HK", "IS", "KR", "DK", "IL", "BE", "AT", "FR",
  "SI", "FI", "ES", "IT", "LU", "SG", "CZ", "AE", "GR", "GB",
  "CY", "AD", "BN", "EE", "SK", "MT", "QA", "HU", "PL", "LT",
  "PT", "BH", "LV", "CL", "AR", "HR", "BB", "UY", "PW", "RO"
  ```
The below "currencyTo" or "currencyFrom" are assumed to support:
  ```
  "AED", "AUD", "CAD", "EUR", "EGP", "GBP", "IDR", "INR", "USD", "JPY",
  "CNY", "CHF", "SGD", "MYR", "DKK", "SAR", "RUB", "QAR", "TRY", "VEF"
  ```


## Local Env Build and Run

### Get the MongoDB from Docker:

``` 
docker pull mongo
```

### Get the RabbitMQ from Docker:

```
docker pull rabbitmq:3-management
```

### Get the application source from github:

```
git clone https://github.com/thompsonshi2020/currencyfair.git
```

Use the dev tool (e.g. VS Code) or command line to open the Maven project

- Install and Build:
```
mvn clean install
mvn clean package
```

- Run it:

```
./mvnw spring-boot:run
```