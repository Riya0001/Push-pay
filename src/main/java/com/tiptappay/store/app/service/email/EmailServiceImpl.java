package com.tiptappay.store.app.service.email;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.benevity.store.MyAccountToken;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.tiptappay.store.app.constant.AppConstants.EmailConstants.*;

@Service("EmailService")
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final SimpleMailMessage template;
    private final SpringTemplateEngine thymeleafTemplateEngine;
    private final CustomLoggerService logger;

    @Value("${spring.mail.default-from-address}")
    private String mailDefaultFromAddress;

    @Value("${spring.mail.enable-sending-email}")
    private boolean enableSendingEmail;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            template.setFrom(mailDefaultFromAddress);
            template.setTo(to);
            template.setSubject(subject);
            template.setText(text);

            emailSender.send(template);
        } catch (MailException exception) {
            logger.logError("Failed to send email : ");
        }
    }

    public void sendMessageUsingThymeleafTemplate(Map<String, Object> templateModel) {

        String subject = (String) templateModel.get(TEMPLATE_KEY_SUBJECT);
        MyAccountToken myAccountToken = (MyAccountToken) templateModel.get(TEMPLATE_KEY_MY_ACCOUNT_TOKEN);
        String to = myAccountToken.getEmail();

        if (!enableSendingEmail) {
            logger.logInfo("Email is disabled, skip sending email");
            logEmail(to, subject, myAccountToken);
            return;
        }


        templateModel.put("organizationName", myAccountToken.getOrganizationName());
        templateModel.put("accountRole", myAccountToken.getRole().equals(AppConstants.MembershipRoles.MEMBERSHIP_ROLE_ADMIN) ? "Admin" : "Manager");

        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process(TEMPLATE_THYMELEAF_MEMBERSHIP_EMAIL, thymeleafContext);

        boolean isSent = sendHtmlMessage(to, subject, htmlBody);
        if (isSent) {
            logger.logInfo("Email is sent successfully");
        } else {
            logger.logInfo("Failed to sent successfully");
        }
    }

    private boolean sendHtmlMessage(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(mailDefaultFromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
//        helper.addInline("attachment.png", resourceFile);
            emailSender.send(message);
            return true;
        } catch (MessagingException exception) {
            logger.logError("Messaging Exception occurred while sending email : ");
        }
        return false;
    }

    private void logEmail(String to, String subject, MyAccountToken myAccountToken) {
        logger.logInfo("From : " + mailDefaultFromAddress + ", To : " + to
                + ", Date : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                ", Subject : " + subject +
                ", Message : " + "Membership created, Login Link : " + myAccountToken.getLoginURL());
    }
}