package com.hacisimsek.orderservice.service;

import com.hacisimsek.orderservice.circuitbreaker.CustomCircuitBreaker;
import com.hacisimsek.orderservice.client.InventoryServiceClient;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final InventoryServiceClient inventoryServiceClient;

    public OrderService(InventoryServiceClient inventoryServiceClient) {
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @CustomCircuitBreaker(name = "createOrder")
    public String createOrder(String orderId) {
        String inventoryResponse = inventoryServiceClient.checkInventory(orderId);
        return "Order created: " + orderId + ", Inventory status: " + inventoryResponse;
    }
}