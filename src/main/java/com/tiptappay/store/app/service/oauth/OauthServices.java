package com.tiptappay.store.app.service.oauth;

import com.tiptappay.store.app.util.URLBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

import static com.tiptappay.web.constant.TTPConstant.HttpConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthServices{

    @Value("${netsuite.restlet.consumer-key}")
    private String consumerKey;

    @Value("${netsuite.restlet.consumer-secret}")
    private String consumerSecret;

    @Value("${netsuite.restlet.token}")
    private String token;

    @Value("${netsuite.restlet.token-secret}")
    private String tokenSecret;

    @Value("${netsuite.restlet.signature-method}")
    private String signatureMethod;

    @Value("${netsuite.restlet.version}")
    private String version;

    @Value("${netsuite.restlet.account-id}")
    private String accountId;

    @Value("${netsuite.restlet.base-url}")
    private String baseUrl;

    @Value("${netsuite.restlet.deployment-id}")
    private String deploymentId;

    private final int TIMEOUT_SEC = 90;

    public String generateOAuthNonce() {
        int length = 32;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder nonce = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            nonce.append(characters.charAt(randomIndex));
        }

        return nonce.toString();
    }

    public String generateTimestamp() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    public String getSortedOAuthParameters(String script, String oauthNonce, String timestamp, Map<String, String> params) {
        log.info("OauthService.getSortedOAuthParameters > Started");
        Map<String, String> sortedParams = getStringStringMap(script, oauthNonce, timestamp);
        log.info("OauthService.getSortedOAuthParameters > OAuth parameters added to the sorted parameters");

        log.info("OauthService.getSortedOAuthParameters > BEFORE params check");
        // Add additional parameters from the map
        if (params != null && !params.isEmpty()) {
            log.info("OauthService.getSortedOAuthParameters > Additional parameters added to the sorted parameters");
            sortedParams.putAll(params);
        }
        log.info("OauthService.getSortedOAuthParameters > Sorted parameters : {}", sortedParams);

        // Build the sorted parameter string
        StringBuilder sortedParamsString = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (!sortedParamsString.isEmpty()) {
                sortedParamsString.append("&");
            }
            sortedParamsString.append(entry.getKey()).append("=").append(entry.getValue());
        }

        return sortedParamsString.toString();
    }

    @NotNull
    private Map<String, String> getStringStringMap(String script, String oauthNonce, String timestamp) {
        Map<String, String> sortedParams = new TreeMap<>();

        // Add OAuth parameters
        sortedParams.put("deploy", deploymentId);
        sortedParams.put("oauth_consumer_key", consumerKey);
        sortedParams.put("oauth_nonce", oauthNonce);
        sortedParams.put("oauth_signature_method", signatureMethod);
        sortedParams.put("oauth_timestamp", timestamp);
        sortedParams.put("oauth_token", token);
        sortedParams.put("oauth_version", version);
        sortedParams.put("script", script);
        return sortedParams;
    }

    public String generateOAuthSignature(String sortedParams, String httpMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {

        // Encode URI components
        String uriEncodedSortedParams = encodeURIComponent(sortedParams);
        String encodedBaseUrl = encodeURIComponent(baseUrl);

        // Concatenate values
        String concatenatedData = httpMethod + "&" + encodedBaseUrl + "&" + uriEncodedSortedParams;

        // Concatenate secrets
        String finalSecret = consumerSecret + "&" + tokenSecret;

        // Sign concatenated data
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(finalSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        hmacSha256.init(secretKey);
        byte[] signedBytes = hmacSha256.doFinal(concatenatedData.getBytes(StandardCharsets.UTF_8));

        // Get Base64 encoded data from signed data
        String base64EncodedData = Base64.getEncoder().encodeToString(signedBytes);

        // URI encode and return signature
        return encodeURIComponent(base64EncodedData);
    }

    public String encodeURIComponent(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public String generateAuthHeaderValue(String oauthSignature, String oauthNonce, String timestamp) {
        return String.format("OAuth oauth_signature=\"%s\", oauth_version=\"%s\", oauth_nonce=\"%s\", oauth_signature_method=\"%s\", oauth_consumer_key=\"%s\", oauth_token=\"%s\", oauth_timestamp=\"%s\", realm=\"%s\"",
                oauthSignature, version, oauthNonce, signatureMethod, consumerKey, token, timestamp, accountId);
    }

    public String postRequest(String requestUrl, String authHeaderValue, String bodyData) {
        log.info("OauthService.postRequest(): Timeout set {}", TIMEOUT_SEC);
        try {
            HttpRequest request = createHttpRequest(requestUrl, authHeaderValue, HTTP_METHOD_POST, bodyData);
            return sendRequest(request);
        } catch (Exception exception) {
            log.error("OauthService.postRequest > Exception occurred while communicating with SuiteScript (NetSuite) : {}", exception.getMessage());
            return null;
        }
    }

    public String getRequest(String requestUrl, String authHeaderValue) {
        log.info("OauthService.getRequest(): Timeout set {}", TIMEOUT_SEC);
        try {
            HttpRequest request = createHttpRequest(requestUrl, authHeaderValue, HTTP_METHOD_GET, null);
            return sendRequest(request);
        } catch (Exception exception) {
            log.error("OauthService.getRequest > Exception occurred while communicating with SuiteScript (NetSuite) : {}", exception.getMessage());
            return null;
        }
    }

    private HttpRequest createHttpRequest(String requestUrl, String authHeaderValue, String httpMethod, String bodyData) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header(HTTP_HEADER_NAME_CONTENT_TYPE, "application/json")
                .header(HTTP_HEADER_NAME_AUTHORIZATION, authHeaderValue)
                .header(HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
                .header(HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                .header(HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_METHODS, "GET,DELETE,PATCH,POST,PUT")
                .header(HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_HEADERS, "X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version")
                .timeout(Duration.ofSeconds(TIMEOUT_SEC));

        if (HTTP_METHOD_POST.equalsIgnoreCase(httpMethod)) {
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(bodyData));
        } else if (HTTP_METHOD_GET.equalsIgnoreCase(httpMethod)) {
            requestBuilder.GET();
        }

        return requestBuilder.build();
    }

    private String sendRequest(HttpRequest request) throws Exception {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SEC))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("OauthService.sendRequest > Status Code : {}", response.statusCode());
        log.info("OauthService.sendRequest > Response Body : {}", response.body());

        if (response.statusCode() == 200) {
            log.info("OauthService.sendRequest > Successful communication with SuiteScript (NetSuite)");
            return response.body();
        } else {
            log.error("OauthService.sendRequest > Error while communicating with SuiteScript (NetSuite)");
            return null;
        }
    }

    public String runApiRequest(String httpMethod, String script, Map<String, String> params, String bodyData) {
        try {
            log.info("OauthService.runApiRequest > Started SuiteScript (NetSuite) request");
            // Prepare dynamic values
            String oauthNonce = generateOAuthNonce();
            String timestamp = generateTimestamp();

            log.info("OauthService.runApiRequest > BEFORE Sorted Params");

            // Process Token-Based Authentication logic in RESTlet
            String sortedParams = getSortedOAuthParameters(script, oauthNonce, timestamp, params);
            log.info("OauthService.runApiRequest > Sorted Params : {}", sortedParams);
            String oauthSignature = generateOAuthSignature(sortedParams, httpMethod);
            log.info("OauthService.runApiRequest > Signature : {}", oauthSignature);
            String authHeaderValue = generateAuthHeaderValue(oauthSignature, oauthNonce, timestamp);
            log.info("OauthService.runApiRequest > Auth Header Value : {}", authHeaderValue);

            // Add common key-value pairs to the Map
            params.put("script", script);
            params.put("deploy", deploymentId);

            log.info("OauthService.runApiRequest > New Params : {}", params);

            // Prepare Full Url with dynamic parameters
            URLBuilder urlBuilder = new URLBuilder(baseUrl);
            String requestUrl = urlBuilder.addQueryParams(params).build();
            log.info("OauthService.runApiRequest > Request Url : {}", requestUrl);

            String response = null;

            if (HTTP_METHOD_POST.equalsIgnoreCase(httpMethod)) {
                // POST api request & return response
                log.info("OauthService.runApiRequest > POST {}", requestUrl);
                response = postRequest(requestUrl, authHeaderValue, bodyData);
            } else if (HTTP_METHOD_GET.equalsIgnoreCase(httpMethod)) {
                // GET api request & return response
                log.info("OauthService.runApiRequest > GET {}", requestUrl);
                response = getRequest(requestUrl, authHeaderValue);
            }

            log.info("OauthService.runApiRequest > Completed SuiteScript (NetSuite) request");
            return response;
        } catch (Exception exception) {
            log.error("OauthService.runApiRequest > Exception Occurred SuiteScript (NetSuite) request : {}", exception.getMessage());
            return null;
        }
    }

}