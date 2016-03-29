package com.javaasc.util;

public class JascException extends RuntimeException {
    public JascException(String message) {
        super(message);
    }

    public JascException(String message, Throwable cause) {
        super(message, cause);
    }
}
