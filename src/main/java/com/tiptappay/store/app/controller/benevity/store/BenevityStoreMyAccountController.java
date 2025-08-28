package com.tiptappay.store.app.controller.benevity.store;

import com.tiptappay.store.app.dto.benevity.store.MyAccountDTO;
import com.tiptappay.store.app.dto.benevity.store.MyAccountToken;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.membership.MagicLinkRequest;
import com.tiptappay.store.app.model.membership.Membership;
import com.tiptappay.store.app.model.membership.MembershipRequest;
import com.tiptappay.store.app.model.membership.MembershipResponse;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;
import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.service.benevity.BenevityStoreService;
import com.tiptappay.store.app.service.membership.MembershipService;
import com.tiptappay.store.app.util.AppUtil;
import com.tiptappay.store.app.util.DataUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.tiptappay.store.app.constant.AppConstants.CookiesConstants.COOKIE_ID_BENEVITY_EDUCATION;
import static com.tiptappay.store.app.constant.AppConstants.CookiesConstants.COOKIE_ID_BENEVITY_MAIN;
import static com.tiptappay.store.app.constant.AppConstants.HttpConstants.*;
import static com.tiptappay.store.app.constant.AppConstants.MembershipRoles.MEMBERSHIP_ROLE_MANAGER;
import static com.tiptappay.store.app.constant.AppConstants.RedirectTemplates.*;
import static com.tiptappay.store.app.constant.AppConstants.ViewTemplates.*;

@Slf4j
@Controller
@RequestMapping("${stores.benevity.endpoint}")
@RequiredArgsConstructor
public class BenevityStoreMyAccountController {

    private final BenevityStoreService benevityStoreService;
    private final MembershipService membershipService;
    private final CustomLoggerService logger;
    private final AppService appService;
    private final CustomHttpService customHttpService;

    @Value("${stores.benevity.endpoint}")
    private String endpoint;

    @Value("${stores.benevity.my-account.resend-email.expiry-short}")
    private int resendEmailExpiryShort;

    @Value("${stores.benevity.my-account.resend-email.expiry-long}")
    private int resendEmailExpiryLong;

    @GetMapping("/my-account-setup-later")
    @ResponseBody
    public Map<String, String> showBenevityMyAccountSetupLater(HttpServletResponse response) {
        benevityStoreService.deleteMyAccountToken(response, COOKIE_ID_BENEVITY_MAIN);

        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", "Account setup postponed successfully");
        jsonResponse.put("todo", "Email the user to guide how to complete verification setup");

        return jsonResponse;
    }

    @PostMapping("/start-collecting")
    public String startCollecting() {
        return REDIRECT_BENEVITY_MY_ACCOUNT_SETUP;
    }

    @GetMapping("/my-account-setup")
    public String showBenevityMyAccountSetup(HttpServletRequest request,
                                             HttpServletResponse response,
                                             Model model,
                                             MyAccountDTO myAccountDTO) {

        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);
        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        // If Manager, use entered email here
        if (myAccountToken.getRole().equals(MEMBERSHIP_ROLE_MANAGER) && !myAccountToken.getEmail().isBlank()) {
            myAccountDTO.setEmail(myAccountToken.getEmail());
        }

        model.addAttribute("myAccountDTO", myAccountDTO);
        return VIEW_BENEVITY_STORE_MY_ACCOUNT_SETUP_FOR_CREATING_MEMBER;
    }

    @PostMapping("/my-account-setup")
    public String postBenevityMyAccountSetup(HttpServletRequest request, HttpServletResponse response, MyAccountDTO myAccountDTO) {
        logger.logDebug("myAccountDTO : " + myAccountDTO);

        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);
        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        // If token exist but already used before
        // Simply remove it and do not allow new member creation
        if (myAccountToken.getNumOfResent() > 0) {
            benevityStoreService.deleteMyAccountToken(response, COOKIE_ID_BENEVITY_MAIN);
            return REDIRECT_BENEVITY_ORDER;
        }

        myAccountToken.setEmail(myAccountDTO.getEmail());

        MembershipRequest membershipRequest = membershipService
                .generateNewMembershipRequest(myAccountToken, myAccountDTO.getPassword());

        logger.logDebug("membershipRequest : " + membershipRequest);

        CustomHttpResponse customHttpResponse = membershipService
                .postMembership(myAccountToken.getOrganizationId(), membershipRequest);

        StringBuilder errorMessage = new StringBuilder();

        if (customHttpResponse.getResponseCode() == HTTP_RESPONSE_CREATED) {

            try {
                MembershipResponse createdMembership =
                        DataUtils.convertToObject(customHttpResponse.getResponseBody(), MembershipResponse.class);

                if (createdMembership != null) {
                    myAccountToken.setLoginURL(createdMembership.getLoginUrl());

                    // TODO: We removed email send logic here, API will send an email and we will be using that one
                    // Send Magic Link
                    // benevityStoreService.sendMagicLinkEmail(myAccountToken);
                }

                myAccountToken.setNumOfResent(0);
                myAccountToken.setResendCounter(AppUtil.getCurrentTimeMillis());

                benevityStoreService.writeMyAccountToken(response, myAccountToken, COOKIE_ID_BENEVITY_MAIN);
                return REDIRECT_BENEVITY_MY_ACCOUNT_SETUP_VERIFY;

            } catch (Exception exception) {
                logger.logInfo("Exception processing the memberships response result : " + exception.getMessage());
                errorMessage.append("Unexpected Error");
            }

        } else if (customHttpResponse.getResponseCode() == HTTP_RESPONSE_UNPROCESSABLE_ENTITY) {
            logger.logInfo("Unexpected case > member " + myAccountToken.getEmail()
                    + " does already exist in organization " + myAccountToken.getOrganizationId() + " : "
                    + customHttpResponse.getResponseBody());
            errorMessage.append("Already Member in ")
                    .append(myAccountToken.getOrganizationName())
                    .append(".");
        } else {
            logger.logInfo("Unexpected response for member " + myAccountToken.getEmail()
                    + " in organization " + myAccountToken.getOrganizationId() + " : "
                    + customHttpResponse.getResponseBody());
            errorMessage.append("Unexpected Error");
        }

        benevityStoreService.writeMyAccountToken(response, myAccountToken, COOKIE_ID_BENEVITY_MAIN);
        return String.format(REDIRECT_BENEVITY_MY_ACCOUNT_SETUP_ERROR + "?error=%s", errorMessage);
    }

    @GetMapping("/my-account-setup/verify")
    public String showBenevityMyAccountSetupVerify(HttpServletRequest request, HttpServletResponse response, Model model) {
        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);
        logger.logDebug("My Account Token : " + myAccountToken);

        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        model.addAttribute("email", myAccountToken.getEmail());
        model.addAttribute("futureEpochTime", myAccountToken.getResendCounter());

        return VIEW_BENEVITY_STORE_MY_ACCOUNT_SETUP_VERIFY;
    }


    @PostMapping("/my-account-setup/resend-email")
    public String showBenevityMyAccountSetupResendEmail(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.logInfo("Inside showBenevityMyAccountSetupResendEmail()");

        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);

        logger.logDebug("My Account Token : " + myAccountToken);

        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        if ((myAccountToken.getResendCounter() - AppUtil.getCurrentTimeMillis()) < 0) {// Valid to resend

            String urlPath = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() + "/memberships/send-magic-link";

            Map<String, String> headers = Map.of(
                    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
                    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
            );

            MagicLinkRequest magicLinkRequest = new MagicLinkRequest(
                    myAccountToken.getEmail(),
                    String.format("/companies/%s/setup/my-first-payment-page", myAccountToken.getOrganizationId())
            );

            String requestBody = DataUtils.convertToJsonString(magicLinkRequest);

            CustomHttpResponse customHttpResponse = customHttpService.postRequest(urlPath, headers, requestBody);

            logger.logDebug("CustomHttpResponse : " + customHttpResponse.getResponseBody());

            myAccountToken.setNumOfResent(myAccountToken.getNumOfResent() + 1);
            if (myAccountToken.getNumOfResent() == 1) {
                myAccountToken.setResendCounter(AppUtil.getCurrentTimeMillisPlus(TimeUnit.MINUTES, resendEmailExpiryShort));
            } else {
                myAccountToken.setResendCounter(AppUtil.getCurrentTimeMillisPlus(TimeUnit.MINUTES, resendEmailExpiryLong));
            }

            benevityStoreService.writeMyAccountToken(response, myAccountToken, COOKIE_ID_BENEVITY_MAIN);
        }

        return REDIRECT_BENEVITY_MY_ACCOUNT_SETUP_VERIFY;
    }

    @GetMapping("/my-account-setup/error")
    public String showBenevityMyAccountSetupError(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  @RequestParam(value = "error") String errorMessage,
                                                  Model model) {
        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);
        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        model.addAttribute("errorMessage", errorMessage);

        return VIEW_BENEVITY_STORE_MY_ACCOUNT_SETUP_VERIFY_FAILED;
    }

    @PostMapping("/keep-collecting")
    public String keepCollecting() {
        return REDIRECT_BENEVITY_MY_ACCOUNT;
    }

    @GetMapping("/my-account")
    public String showBenevityMyAccount(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Model model,
                                        MyAccountDTO myAccountDTO) {
        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);
        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        model.addAttribute("myAccountDTO", myAccountDTO);
        model.addAttribute("organizationName", myAccountToken.getOrganizationName());
        return VIEW_BENEVITY_STORE_MY_ACCOUNT_FOR_EMAIL_ENTRY;
    }

    @PostMapping("/my-account")
    public String postBenevityMyAccount(HttpServletRequest request,
                                        HttpServletResponse response,
                                        MyAccountDTO myAccountDTO) {
        logger.logDebug("myAccountDTO : " + myAccountDTO);

        MyAccountToken myAccountToken = benevityStoreService.readMyAccountToken(request, response, COOKIE_ID_BENEVITY_MAIN);
        if (!benevityStoreService.validateMyAccountToken(myAccountToken, response, COOKIE_ID_BENEVITY_MAIN)) {
            return String.format(REDIRECT_STRING_FORMAT_BENEVITY_SESSION_EXPIRED, endpoint);
        }

        myAccountToken.setEmail(myAccountDTO.getEmail());

        benevityStoreService.writeMyAccountToken(response, myAccountToken, COOKIE_ID_BENEVITY_MAIN);

        // TODO : Instead of getting all list and try to find user in that list, we should call API to find if given email exist
        // TODO : •••••••••••••••• UPDATE BELOW LATER ••••••••••••••••••••••
        List<Membership> membershipList = membershipService.fetchMembershipsByOrganizationId(myAccountToken.getOrganizationId());

        for (Membership membership : membershipList) {
            if (membership.getUser().getEmail().equals(myAccountToken.getEmail())) {
                // Member found
                return String.format(
                        "redirect:%s/login?login_destination=%s&username=%s",
                        appService.getBaseUrl(),
                        String.format("/companies/%s/payment-pages", myAccountToken.getOrganizationId()),
                        URLEncoder.encode(myAccountToken.getEmail().trim(), StandardCharsets.UTF_8)
                );
            }
        }

        // TODO : •••••••••••••••• UPDATE ABOVE LATER ••••••••••••••••••••••

        return REDIRECT_BENEVITY_MY_ACCOUNT_SETUP;
    }

    @GetMapping("/my-account-setup/session-expired")
    public String showBenevitySessionExpired(Model model) {
        model.addAttribute("actionEndpoint", endpoint);
        return VIEW_BENEVITY_SESSION_EXPIRED;
    }

}
