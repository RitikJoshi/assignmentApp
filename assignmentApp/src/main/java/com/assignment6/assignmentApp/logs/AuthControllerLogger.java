package com.assignment6.assignmentApp.logs;

import com.assignment6.assignmentApp.controller.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthControllerLogger {

    private Logger logger;

    public AuthControllerLogger() {
        this.logger = LoggerFactory.getLogger(AuthController.class);
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logError(String message, Throwable exception) {
        logger.error(message, exception);
    }

    public void authenticateUserLogInfo(String login_id) {
        logInfo("User authenticated: " + login_id);
    }

    public void authenticateUserLogError(String login_id, Exception exception) {
        logError("Authentication error for user: " + login_id, exception);
    }
}

