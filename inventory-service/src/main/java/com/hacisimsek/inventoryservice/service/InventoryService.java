package com.hacisimsek.inventoryservice.service;

import com.hacisimsek.inventoryservice.circuitbreaker.CustomCircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @CustomCircuitBreaker(name = "checkInventory")
    public String checkInventory(String productId) {
        // Simulated inventory control
        return "In stock";
    }
}