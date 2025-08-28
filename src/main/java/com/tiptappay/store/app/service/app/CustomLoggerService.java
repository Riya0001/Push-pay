package com.tiptappay.store.app.service.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomLoggerService {

    @Value("${app.developer.debug.enabled}")
    private boolean debugEnabled;

    public void logInfo(String message) {
        log.info(message);
    }

    public void logDebug(String message) {
        if (debugEnabled) {
            log.debug(message);
        }
    }

    public void logWarn(String message) {
        log.warn(message);
    }

    public void logError(String message) {
        log.error(message);
    }

    // ✅ Fix: Accept Throwable to log stack trace
    public void logError(String message, Throwable throwable) {
        log.error(message, throwable);
    }

    public void logInfo(String s, String contactFirstName, String contactLastName) {

    }
}
