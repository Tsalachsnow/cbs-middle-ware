## Mocked Payment Gateway Middleware API Documentation

Date: Feb 25th 2024

Git: GitHub - Tsalachsnow/cbs-middle-ware:cbs-middle-ware technical

### Project Description:
This project is a mocked RESTful API designed to simulate a simple payment gateway middleware. It facilitates transactions between a
mock retail application and a banking service.

It mimicks how a retail application witll consume a payment gateway (bank) service when making payment and how the payment gateway
(bank) sends feedback in a callback url after processing payment.

### Requirements
* Implement endpoints for initiating a transaction, checking transaction status, and receiving webhook notifications for transaction updates.

* Use an in-memory database or mock data to simulate transaction records and banking service responses.

* Incorporate basic authentication to secure the API endpoints.

* Document the API endpoints using an open specification like Swagger.

### Access API Documentation:
Run the apllication locally on your IDE terminal using “
./mvnw spring-boot:run“.

After the application is started, Open your web browser and navigate to http://localhost:9052/swagger-ui/index.html to view the Swagger
documentation for the API endpoints.

Open your web browser and navigate to http://localhost:9052/h2-console/ to view the H2 Console

Jdbc url: jdbc:h2:mem:cbspaymentgatewaydb

User name: sa

Password: test

General Request Mapping: /api/transaction

#### Retail API Endpoints:
* POST /initiate : Initiates a new transaction.

* GET /get-transaction-status/{paymentReference : Retrieves the status of a transaction.

* POST /webhook : Receives webhook notifications for transaction updates.

#### Payment Gateway Endpoint:
* POST /process

#### Authentication
* Basic authentication is implemented to secure the API endpoints.

* Api-Key is required to access the endpoints.

#### Api-Key: ER09876546789ER098
To be added as a header on swagger or postman before initiating any call to any endpoint.

### Validations:
* PaymentReference must be unique for every payment else get a duplicate transaction exception

* Amount must be in the positive value else throw an invalid amount exception

* PaymentDescription must not be empty else throw an paymentDescription is required exception

* DebitAccountNo and creditAccountNo account number must be less than 4 in length else throw and invalid accountNo exception.

* currencyCode , countryCode , creditAccountNo , debitAccountNo , amount , tranType , exchRate , paymentReference are all
required if Empty will throw field-required exception.

Please refer to the swagger doc for detailed instructions and Payload samples.