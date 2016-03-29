package com.javaasc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JascLogger {
    private final Logger logger;

    public static JascLogger getLogger(Class clazz) {
        return new JascLogger(clazz);
    }

    private JascLogger(Class clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void debug(String message, Object... parameters) {
        logger.debug(message, parameters);
    }

    public void warn(String message, Throwable e) {
        logger.warn(message, e);
    }

    public void info(String message) {
        logger.info(message);
    }
}
