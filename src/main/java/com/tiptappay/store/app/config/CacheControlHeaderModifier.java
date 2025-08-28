package com.tiptappay.store.app.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CacheControlHeaderModifier extends ResponseEntityExceptionHandler {

    @ModelAttribute
    public void setCacheControlHeader(HttpServletResponse response) {
        // Set Cache Control header to no-cache for all responses
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        // Set Expires header to 0 to ensure that the response is not cached by intermediate caches
        response.setHeader("Expires", "0");
        // Set Pragma header to no-cache to ensure compatibility with HTTP/1.0 caches
        response.setHeader("Pragma", "no-cache");
    }
}