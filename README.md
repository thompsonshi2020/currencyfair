## Quick Summary
Realize the market trade processor which consumes trade messages via an endpoint, processes those messages in some way and delivers a frontend of processed information based on the consumed messages, includes:
  
  i) Message Consumption

  The controller in Spring Boot as Singleton pattern to provide the endpoint for clients.
   
  The messages are consumed in RabbitMQ, in fact, the Strategy pattern is used, different brands of MQ services can be pluggable, e.g. ActiveMQ, Hazelcast, Kafka, RabbitMQ, ZeroMQ, etc.

  ii) Message Processor

  The messages are validated by specific logic and stored in MongoDB.
   
  3 types of dimensions of trade results are implemented. For presenting the different dimensions of trade results to UI, mobile app, the Strategy pattern is used. So, any additional dimension of trade result can be implemented later on.

  iii) Message Frontend

  The mentioned 3 types of dimensions are shown on frontend as below
    Summary of Top 5 Country Trades
    Summary of Top 10 Country Trades with Trading Pairs
    Summary of Latest 10 Trades

  Web Socket is implemented. So, any messages sent by any users will be aggregated and pushed to the frontend


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

  [http://15.165.158.139:8080/index.html]


#### POST Endpoint

  [http://15.165.158.139:8080/fair/trade]

    E.g. by curl:

    curl -i -X POST -H 'Content-Type: application/json' --header 'Accept: application/json' -d '{"userId": "134256", "currencyFrom": "EUR", "currencyTo": "GBP", "amountSell": 1000, "amountBuy": 747.10, "rate": 0.7471, "timePlaced" : "24-JAN-18 10:27:44", "originatingCountry" : "FR"} ' 'http://15.165.158.139:8080/fair/trade'


#### API Doc

  [http://15.165.158.139:8080/swagger-ui.html#/]

#### Assumptions

The below "originatingCountry" are supported:
  ```
  "NO", "AU", "NL", "US", "NZ", "CA", "IE", "LI", "DE", "SE",
  "CH", "JP", "HK", "IS", "KR", "DK", "IL", "BE", "AT", "FR",
  "SI", "FI", "ES", "IT", "LU", "SG", "CZ", "AE", "GR", "GB",
  "CY", "AD", "BN", "EE", "SK", "MT", "QA", "HU", "PL", "LT",
  "PT", "BH", "LV", "CL", "AR", "HR", "BB", "UY", "PW", "RO"
  ```
The below "currencyTo" or "currencyFrm" are supported:
  ```
  "AED", "AUD", "CAD", "EUR", "EGP", "GBP", "IDR", "INR", "USD", "JPY",
  "CNY", "CHF", "SGD", "MYR", "DKK", "SAR", "RUB", "QAR", "TRY", "VEF"};
  ```

