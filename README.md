
# Circuit Breaker Pattern

I have created and enhanced my own Circuit Breaker pattern without using the traditional three-part module.

## Run the following projects in order:

Eureka Server -> 
Inventory Service -> 
Order Service

You can run each project in a separate terminal window with the command

```bash
  mvn spring-boot:run
```


## Check that services are registered with Eureka:

Go to http://localhost:8761 in your browser.
“INVENTORY-SERVICE” and ‘ORDER-SERVICE’ should appear on the Eureka dashboard.

##  Test Inventory Service:

Send the following request using postman or curl:
```bash
  GET http://localhost:8082/inventory/check/123
```

##  Test Order Service:

Send the following request using postman or curl:
```bash
  POST http://localhost:8081/orders
    Body: 123
```

“Order created: 123, Inventory status: In stock” you should get a similar response.
