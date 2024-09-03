package com.hacisimsek.inventoryservice.circuitbreaker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CircuitBreakerAspect {

    private final CircuitBreakerImpl circuitBreaker;

    public CircuitBreakerAspect(CircuitBreakerImpl circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }

    @Around("@annotation(customCircuitBreaker)")
    public Object applyCircuitBreaker(ProceedingJoinPoint joinPoint, CustomCircuitBreaker customCircuitBreaker) throws Throwable {
        return circuitBreaker.execute(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }
}