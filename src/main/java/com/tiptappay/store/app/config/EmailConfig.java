package com.tiptappay.store.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import jakarta.mail.MessagingException;
import java.util.Properties;

@Configuration
@Slf4j
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String mailServerHost;

    @Value("${spring.mail.port}")
    private Integer mailServerPort;

    @Value("${spring.mail.username}")
    private String mailServerUsername;

    @Value("${spring.mail.password}")
    private String mailServerPassword;

    @Value("${spring.mail.test-connection}")
    private Boolean mailServerTestConnection;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailServerPropertiesTransferProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private Boolean mailServerPropertiesAuth;

    @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
    private Integer mailServerPropertiesConnectiontimeout;

    @Value("${spring.mail.properties.mail.smtp.timeout}")
    private Integer mailServerPropertiesTimeout;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private Boolean mailServerPropertiesStartTLSEnable;

    @Value("${spring.mail.properties.mail.debug}")
    private String mailServerPropertiesDebug;

//    @Value("${spring.mail.templates.path}")
//    private String mailTemplatesPath;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailServerPropertiesTransferProtocol);
        props.put("mail.smtp.auth", mailServerPropertiesAuth);
        props.put("mail.smtp.connectiontimeout", mailServerPropertiesConnectiontimeout);
        props.put("mail.smtp.timeout", mailServerPropertiesTimeout);
        props.put("mail.smtp.starttls.enable", mailServerPropertiesStartTLSEnable);
        props.put("mail.debug", mailServerPropertiesDebug);

        // Test Mail Server Connection
        if (mailServerTestConnection) {
            try {
                mailSender.testConnection();
            }
            catch (MessagingException exception) {
                exception.printStackTrace();
            }
        }

        return mailSender;
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("This is the test email template for your email:\n%s\n");
        return message;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("mailMessages");
        return messageSource;
    }
}