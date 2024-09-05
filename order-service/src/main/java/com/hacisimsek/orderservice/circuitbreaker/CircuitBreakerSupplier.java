package com.hacisimsek.orderservice.circuitbreaker;

@FunctionalInterface
public interface CircuitBreakerSupplier<T> {
    T get() throws Exception;
}
