package com.tiptappay.store.app.service.app;

import com.tiptappay.store.app.constant.AppConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class AppService {

    @Value("${netsuite.environment}")
    private String netsuiteEnvironment;

    @Value("${app.environment}")
    private String environment;

    @Value("${athena.api.prefix}")
    private String athenaApiPrefix;

    @Value("${athena.api.key}")
    private String athenaApiKey;

    public String getBaseUrl() {
        return environment.equals(AppConstants.AppEnvironmentOptions.DEVELOPMENT) ?
                AppConstants.AthenaAppBaseUrl.DEVELOPMENT : AppConstants.AthenaAppBaseUrl.PRODUCTION;
    }
}
