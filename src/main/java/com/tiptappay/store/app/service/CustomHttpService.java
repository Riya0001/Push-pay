package com.tiptappay.store.app.service;

import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_METHOD_GET;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.HTTP_METHOD_POST;

@Service
@RequiredArgsConstructor
public class CustomHttpService {
    private final CustomLoggerService loggerService;

    private static final int TIMEOUT_SEC = 90;

    public CustomHttpResponse postRequest(String requestUrl, Map<String, String> headers, String bodyData) {
        loggerService.logInfo("Inside CustomHttpService.postRequest()");
        try {
            HttpRequest request = createHttpRequest(requestUrl, headers, HTTP_METHOD_POST, bodyData);
            return sendRequest(request);
        } catch (Exception exception) {
            loggerService.logError("Exception occurred while communicating with Server (POST)");

            // Debug information for Athena API requests
            if (requestUrl.contains("/api/athena/v1/")) {
                loggerService.logError("❌ ATHENA API ERROR (POST) ❌");
                loggerService.logError("📤 Request URL: " + requestUrl);
                loggerService.logError("📤 Request Headers: " + headers);
                loggerService.logError("📤 Request Body: " + bodyData);
                loggerService.logError("❌ Exception: " + exception.getMessage());
            }

            return null;
        }
    }

    public CustomHttpResponse getRequest(String requestUrl, Map<String, String> headers) {
        loggerService.logInfo(String.format("CustomHttpService.getRequest(): Timeout set %s", TIMEOUT_SEC));
        try {
            HttpRequest request = createHttpRequest(requestUrl, headers, HTTP_METHOD_GET, null);
            return sendRequest(request);
        } catch (Exception exception) {
            loggerService.logError("Exception occurred while communicating with Server (GET)");

            // Debug information for Athena API requests
            if (requestUrl.contains("/api/athena/v1/")) {
                loggerService.logError("❌ ATHENA API ERROR (GET) ❌");
                loggerService.logError("📤 Request URL: " + requestUrl);
                loggerService.logError("📤 Request Headers: " + headers);
                loggerService.logError("❌ Exception: " + exception.getMessage());
            }
            
            return null;
        }
    }

    private HttpRequest createHttpRequest(String requestUrl, Map<String, String> headers, String httpMethod, String bodyData) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .timeout(Duration.ofSeconds(TIMEOUT_SEC));

        // Add headers dynamically
        headers.forEach(requestBuilder::header);

        if (HTTP_METHOD_POST.equalsIgnoreCase(httpMethod)) {
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(bodyData));
        } else if (HTTP_METHOD_GET.equalsIgnoreCase(httpMethod)) {
            requestBuilder.GET();
        }

        return requestBuilder.build();
    }

    private CustomHttpResponse sendRequest(HttpRequest request) throws Exception {
        loggerService.logInfo("Inside CustomHttpService.sendRequest()");
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SEC))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // Debug information for Athena API requests
        boolean isAthenaRequest = request.uri().toString().contains("/api/athena/v1/");

        if (isAthenaRequest) {
            loggerService.logInfo("🔍 ATHENA API DEBUG 🔍");
            loggerService.logInfo("📤 Request URL: " + request.uri());
            loggerService.logInfo("📤 Request Method: " + request.method());
            loggerService.logInfo("📥 Response Status: " + response.statusCode());
            loggerService.logInfo("📥 Response Headers: " + response.headers().map());
            loggerService.logError("📥 Response Body: " + response.body());
        }
        
        if(request.toString().contains("/benevity/causes/search?search_term=")) {
            loggerService.logInfo(String.format("CustomHttpService.sendRequest > Status Code : %s, Response Body : %s", response.statusCode(), "BENEVITY SEARCH (long response removed)"));
        } else {
            loggerService.logInfo(String.format("CustomHttpService.sendRequest > Status Code : %s, Response Body : %s", response.statusCode(), response.body()));
        }

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            loggerService.logInfo("Successful communication with Server");
            return new CustomHttpResponse(response.statusCode(), response.body());
        } else {
            loggerService.logError("Exception occurred");
            return null;
        }
    }
}
