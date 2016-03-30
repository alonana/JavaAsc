package com.javaasc.util;

import org.apache.commons.lang.exception.ExceptionUtils;

public class JascException extends RuntimeException {
    public JascException(String message) {
        super(message);
    }

    public JascException(String message, Throwable cause) {
        super(message, cause);
    }

    public static String getStackTrace(Throwable e) {
        return ExceptionUtils.getStackTrace(e);
    }
}
