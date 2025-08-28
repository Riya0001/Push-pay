package com.tiptappay.store.app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class URLBuilder {

    private StringBuilder url;

    public URLBuilder(String baseUrl) {
        this.url = new StringBuilder(baseUrl);
    }

    public URLBuilder addPath(String path) {
        if (url.charAt(url.length() - 1) != '/') {
            url.append('/');
        }
        url.append(path);
        return this;
    }

    public URLBuilder addQueryParam(String key, String value) throws UnsupportedEncodingException {
        char separator = url.indexOf("?") == -1 ? '?' : '&';
        url.append(separator)
                .append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                .append('=')
                .append(URLEncoder.encode(value, StandardCharsets.UTF_8));
        return this;
    }

    public URLBuilder addQueryParams(Map<String, String> params) throws UnsupportedEncodingException {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            addQueryParam(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public String build() {
        return url.toString();
    }
}
