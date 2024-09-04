package com.hacisimsek.orderservice;

import com.hacisimsek.orderservice.circuitbreaker.CircuitBreakerImpl;
import com.hacisimsek.orderservice.circuitbreaker.CircuitBreakerOpenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CircuitBreakerImplTest {

    private CircuitBreakerImpl circuitBreaker;

    @BeforeEach
    public void setup() {
        circuitBreaker = new CircuitBreakerImpl();
    }

    @Test
    public void testCircuitBreakerOpens() {
        // Simulate 5 consecutive failures to trigger the circuit breaker
        for (int i = 0; i < 5; i++) {
            try {
                circuitBreaker.execute(() -> {
                    throw new RuntimeException("Simulated failure");
                });
            } catch (Exception ignored) {
                // Ignore exceptions during this phase since we're simulating failures
            }
        }

        // Assert that the circuit breaker is open and an exception is thrown
        assertThrows(CircuitBreakerOpenException.class, () ->
                circuitBreaker.execute(() -> "This should not be called")
        );
    }
}
