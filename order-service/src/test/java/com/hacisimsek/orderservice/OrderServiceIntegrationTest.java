package com.hacisimsek.orderservice;

import com.hacisimsek.orderservice.client.InventoryServiceClient;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryServiceClient inventoryServiceClient;

    @Test
    public void testCreateOrder() throws Exception {
        when(inventoryServiceClient.checkInventory(anyString())).thenReturn("In stock");

        mockMvc.perform(post("/orders")
                        .content("123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string(containsString("Order created: 123")));
    }
}