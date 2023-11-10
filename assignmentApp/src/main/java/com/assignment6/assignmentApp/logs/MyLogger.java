package com.assignment6.assignmentApp.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyLogger {

    private Logger logger;

    public MyLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public void trace(String message) {
        logger.trace(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Throwable exception) {
        logger.error(message, exception);
    }

    // Add more log methods as needed

    // Usage example:
    public static void main(String[] args) {
        MyLogger customLogger = new MyLogger(MyLogger.class);

        customLogger.info("This is an info message");
        customLogger.error("This is an error message", new Exception("Example exception"));
    }
}
