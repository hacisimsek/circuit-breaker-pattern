package com.hacisimsek.orderservice;

import com.hacisimsek.orderservice.client.InventoryServiceClient;
import com.hacisimsek.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private OrderService orderService;
    private InventoryServiceClient inventoryServiceClient;

    @BeforeEach
    public void setup() {
        inventoryServiceClient = mock(InventoryServiceClient.class);
        orderService = new OrderService(inventoryServiceClient);
    }

    @Test
    public void testCreateOrder() {
        when(inventoryServiceClient.checkInventory(anyString())).thenReturn("In stock");
        String result = orderService.createOrder("123");
        assertEquals("Order created: Body: 123, Inventory status: In stock", result);
    }
}