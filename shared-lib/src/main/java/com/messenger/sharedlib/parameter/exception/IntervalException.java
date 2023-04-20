package com.messenger.sharedlib.parameter.exception;

public class IntervalException extends RuntimeException {
    public IntervalException() {
    }

    public IntervalException(String message) {
        super(message);
    }

    public IntervalException(String message, Throwable cause) {
        super(message, cause);
    }

    public IntervalException(Throwable cause) {
        super(cause);
    }
}
