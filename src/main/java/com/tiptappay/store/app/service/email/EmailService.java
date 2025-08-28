package com.tiptappay.store.app.service.email;

import java.util.Map;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageUsingThymeleafTemplate(Map<String, Object> templateModel);
}