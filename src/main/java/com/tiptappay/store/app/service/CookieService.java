package com.tiptappay.store.app.service;

import com.tiptappay.store.app.util.AESUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
public class CookieService {

    @Value("${app.cookie.path}")
    private String cookiePath;

    @Value("${app.cookie.exp}")
    private int cookieExpirationInSec;

    @Value("${app.cookie.http-only}")
    private boolean cookieHttpOnly;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @Value("${app.cookie.encryption-key}")
    private String cookieEncryptionKey;

    public String readCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    try {
                        byte[] decodedData = Base64.getDecoder().decode(cookie.getValue());
                        String decryptedData = AESUtil.decrypt(decodedData, cookieEncryptionKey);
                        return decryptedData;
                    } catch (Exception exception) {
                        log.error("CookieService.readCookie > Error occurred while reading cookie named {} : {}", cookieName, exception.getMessage());
                    }
                }
            }
        }
        return null;
    }

    public void writeCookie(HttpServletResponse response, String cookieName, String jsonData) {
        try {
            // Encrypt data
            byte[] encryptedData = AESUtil.encrypt(jsonData, cookieEncryptionKey);
            String encodedData = Base64.getEncoder().encodeToString(encryptedData);

            // Set cookie
            Cookie cookie = new Cookie(cookieName, encodedData);
            cookie.setHttpOnly(cookieHttpOnly);
            cookie.setSecure(cookieSecure);
            cookie.setMaxAge(cookieExpirationInSec);
            cookie.setPath(cookiePath);
            response.addCookie(cookie);
        } catch (Exception exception) {
            log.error("CookieService.writeCookie > Error occurred while writing cookie named {} : {}", cookieName, exception.getMessage());
        }
    }

    public void deleteCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(cookiePath);
        response.addCookie(cookie);
    }
}
