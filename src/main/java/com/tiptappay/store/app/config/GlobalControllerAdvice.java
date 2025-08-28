package com.tiptappay.store.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Value("${app.version}")
    private String appVersion;

    @Value("${netsuite.environment}")
    private String netsuiteEnvironment;

    @ModelAttribute("appVersion")
    public String addAppVersionToModel() {
        return appVersion;
    }

    @ModelAttribute("netsuiteEnvironment")
    public String addNetSuiteEnvironmentToModel() {
        return netsuiteEnvironment;
    }
}