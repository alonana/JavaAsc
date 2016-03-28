package com.javaasc.util;

public class JascException extends Exception {
    public JascException(String message) {
        super(message);
    }

    public JascException(String message, Throwable cause) {
        super(message, cause);
    }
}
