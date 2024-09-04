package com.hacisimsek.orderservice.circuitbreaker;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class CircuitBreakerImpl {

    private final AtomicReference<State> state = new AtomicReference<>(State.CLOSED);
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private final AtomicReference<LocalDateTime> lastFailureTime = new AtomicReference<>(null);

    private static final int FAILURE_THRESHOLD = 5;
    private static final int RESET_TIMEOUT_SECONDS = 30;

    public enum State {
        OPEN, HALF_OPEN, CLOSED
    }

    public <T> T execute(CircuitBreakerSupplier<T> supplier) throws Exception {
        if (state.get() == State.OPEN) {
            if (LocalDateTime.now().isAfter(lastFailureTime.get().plusSeconds(RESET_TIMEOUT_SECONDS))) {
                state.set(State.HALF_OPEN);
            } else {
                throw new CircuitBreakerOpenException("Circuit is OPEN");
            }
        }

        try {
            T result = supplier.get();
            reset();
            return result;
        } catch (Exception e) {
            handleFailure();
            throw e;
        }
    }

    private void handleFailure() {
        failureCount.incrementAndGet();
        lastFailureTime.set(LocalDateTime.now());

        if (failureCount.get() >= FAILURE_THRESHOLD) {
            state.set(State.OPEN);
        }
    }

    private void reset() {
        failureCount.set(0);
        state.set(State.CLOSED);
    }

    public State getState() {
        return state.get();
    }
}

