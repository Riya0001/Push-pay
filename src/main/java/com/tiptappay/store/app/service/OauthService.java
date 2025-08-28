package com.tiptappay.store.app.service;

import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;

import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthService {

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

    @Value("${netsuite.restlet.http-method}")
    private String httpMethod;

    @Value("${netsuite.restlet.script}")
    private String script;

    @Value("${netsuite.restlet.deployment-id}")
    private String deploymentId;

    private final CustomLoggerService logger;
    private final CustomHttpService customHttpService;

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

    public String getManuallySortedOAuthParameters(String oauthNonce, String timestamp) {
        return String.format("deploy=%s&oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s&script=%s",
                deploymentId, consumerKey, oauthNonce, signatureMethod, timestamp, token, version, script);
    }

    public String generateOAuthSignature(String sortedParams)
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

    public CustomHttpResponse postRequest(String requestUrl, String authHeaderValue, String payload) {
        Map<String, String> headers = Map.of(
                HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                HTTP_HEADER_NAME_AUTHORIZATION, authHeaderValue,
                HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true",
                HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_ORIGIN, "*",
                HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_METHODS, "GET,DELETE,PATCH,POST,PUT",
                HTTP_HEADER_NAME_ACCESS_CONTROL_ALLOW_HEADERS, "X-CSRF-Token, X-Requested-With, Accept, Accept-Version, Content-Length, Content-MD5, Content-Type, Date, X-Api-Version"
        );
        return customHttpService.postRequest(requestUrl, headers, payload);
    }

    public CustomHttpResponse runApiRequest(String payload) {
        try {
            logger.logInfo("Preparing OAuth NetSuite Details...");
            String oauthNonce = generateOAuthNonce();
            String timestamp = generateTimestamp();

            String sortedParams = getManuallySortedOAuthParameters(oauthNonce, timestamp);
            String oauthSignature = generateOAuthSignature(sortedParams);
            String authHeaderValue = generateAuthHeaderValue(oauthSignature, oauthNonce, timestamp);

            String requestUrl = String.format("%s?script=%s&deploy=%s", baseUrl, script, deploymentId);

            CustomHttpResponse customHttpResponse = postRequest(requestUrl, authHeaderValue, payload);
            logger.logInfo("Ended POST request to NetSuite");

            return customHttpResponse;
        } catch (Exception exception) {
            logger.logError("Exception occurred while communicating with NetSuite", exception);
            return new CustomHttpResponse(500, "{\"error\": \"NetSuite communication failed\"}");
        }
    }
}
