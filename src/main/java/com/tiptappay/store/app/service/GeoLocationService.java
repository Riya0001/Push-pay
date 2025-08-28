package com.tiptappay.store.app.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private static final Duration TIMEOUT        = Duration.ofSeconds(2);
    private static final Duration CACHE_TTL      = Duration.ofMinutes(15);
    private static final String   LOCAL_FALLBACK = "CA";
    private static final String   UNKNOWN        = "UNKNOWN";

    private final WebClient webClient;
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    public String resolveCountryCode(HttpServletRequest request) {
        String ip = extractClientIp(request);
        log.debug("GeoLocationService – client IP resolved to {}", ip);
        if (isLocalhost(ip)) {
            return LOCAL_FALLBACK;
        }

        CacheEntry cached = cache.get(ip);
        if (cached != null && cached.expiry().isAfter(Instant.now())) {
            return cached.country();
        }

        String country = webClient.get()
                .uri("http://ip-api.com/json/{ip}", ip)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .timeout(TIMEOUT)
                .map(ApiResponse::countryCode)
                .defaultIfEmpty(UNKNOWN)
                .onErrorResume(ex -> {
                    log.warn("Geo lookup failed for IP {} → {}", ip, ex.getMessage());
                    return Mono.just(UNKNOWN);
                })
                .block();

        cache.put(ip, new CacheEntry(country, Instant.now().plus(CACHE_TTL)));
        return country;
    }

    private String extractClientIp(HttpServletRequest req) {
        String header = req.getHeader("X-Forwarded-For");
        if (header != null && !header.isBlank()) {
            return header.split(",")[0].trim();
        }
        header = req.getHeader("Forwarded");          // RFC 7239
        if (header != null && header.contains("for=")) {
            return header.split("for=")[1].split(";")[0].replace("\"", "");
        }
        return req.getRemoteAddr();
    }

    private boolean isLocalhost(String ip) {
        return "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip);
    }

    private record ApiResponse(String countryCode) {}

    private record CacheEntry(String country, Instant expiry) {}
}
