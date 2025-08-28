package com.tiptappay.store.app.service.bundles;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.dto.bundles.store.CartData;
import com.tiptappay.store.app.dto.bundles.store.CheckoutData;
import com.tiptappay.store.app.dto.bundles.store.PaymentData;
import com.tiptappay.store.app.dto.bundles.wrapper.BundleOrderWrapper;
import com.tiptappay.store.app.model.CampaignDateValidation;
import com.tiptappay.store.app.model.CustomHttpResponse;
import com.tiptappay.store.app.model.SalesRep;
import com.tiptappay.store.app.model.membership.MembershipRequest;
import com.tiptappay.store.app.model.organization.Organization;
import com.tiptappay.store.app.model.organization.OrganizationRequest;
import com.tiptappay.store.app.model.organization.OrganizationResponse;
import com.tiptappay.store.app.model.payload.Payload;
import com.tiptappay.store.app.service.CustomHttpService;
import com.tiptappay.store.app.service.app.AppService;

import com.tiptappay.store.app.service.app.CustomLoggerService;
import com.tiptappay.store.app.service.membership.MembershipService;
import com.tiptappay.store.app.service.organization.OrganizationService;
import com.tiptappay.store.app.service.product.BundleProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.tiptappay.store.app.constant.AppConstants.OrderFormTypes.FORM_TYPE_BUNDLES;

@Service
@RequiredArgsConstructor
public class BundlesStoreService {
    private final CustomLoggerService logger;
    private final AppService app;
    private final BundleProductService bundleProductService;
    private final OrganizationService organizationService;
    private final MembershipService membershipService;
    private final CustomHttpService customHttpService;
    private static final String PRODUCTION_BUNDLE_SO_FORM_INTERNAL_ID = "181";
    private static final String SANDBOX_BUNDLE_SO_FORM_INTERNAL_ID = "182";

    public static final List<SalesRep> CONSTANT_SALES_REPS;

    static {
        CONSTANT_SALES_REPS = List.of(
                new SalesRep("101", "Rizek, Tariq", "mark@tiptappay.com"),
                new SalesRep("102", "Mark Jordan", "mark@tiptappay.com"),
                new SalesRep("104", "Andrew Brousse", "andrew@tiptappay.com"),
                new SalesRep("105", "Henrietta Sung", "henrietta@tiptappay.com"),
                new SalesRep("106", "Robert McGarry", "robert@tiptappay.com"),
                new SalesRep("107", "Parker, Jason", "jason@tiptappay.com"),
                new SalesRep("108", "Shyam Kakaria", "shyam@tiptappay.com")
        );
    }

    public String getSalesRepNameById(String id) {
        return SalesRep.getNameById(CONSTANT_SALES_REPS, id);
    }

    public String getSalesRepEmailById(String id) {
        return SalesRep.getEmailById(CONSTANT_SALES_REPS, id);
    }

    public boolean isSalesRepIdExist(String id) {
        return SalesRep.isIdExist(CONSTANT_SALES_REPS, id);
    }

    /**
     * Creates an organization in Athena API
     * @param checkoutData The checkout data containing organization information
     * @param referenceCode The reference code to use for the organization (usually the SO number)
     * @return The organization response from Athena API
     */
    public OrganizationResponse createOrganization(CheckoutData checkoutData, String referenceCode) {
        logger.logInfo("Inside BundlesStoreService.createOrganization()");

        // Create organization request
        OrganizationRequest organizationRequest = new OrganizationRequest();
        Organization organization = new Organization();

        // Set organization details from checkout data
        organization.setBasicReportingOnly(false);
        organization.setBenevityCauseId(null); // No Benevity cause ID for bundles store

        // Set country code based on the country provided in checkout data
        String countryCode = "US"; // Default to US
        if (checkoutData.getCountryForSettings() != null) {
            if (checkoutData.getCountryForSettings().equalsIgnoreCase("Canada")) {
                countryCode = "CA";
            }
        }
        organization.setCountryCode(countryCode);

        // Set currency based on country code
        organization.setCurrency(countryCode.equals("CA") ? "CAD" : "USD");

        // Set default timezone to Eastern Time
        organization.setDefaultTimezone("America/New_York");

        // Set organization name
        organization.setName(checkoutData.getOrganizationName());

        // Set reference code
        organization.setReferenceCode(referenceCode);

        // Set other required fields
        organization.setNotificationsSmsEnabled(false);
        organization.setNotificationsSmsRecipients(checkoutData.getPhone());
        organization.setNotificationsSmsTemplate("A transaction for {COMPANY_NAME} of {TRANSACTION_AMOUNT} {TRANSACTION_CURRENCY} was {TRANSACTION_STATUS} on {TRANSACTION_DATETIME} from device {DEVICE_SERIAL_NUMBER}");
        organization.setPaymentPagesEnabled("true");
        organization.setRequireTermsAndConditionsAgreement(false);
        organization.setStatus("active");

        // Set subscription details
        Organization.Subscription subscription = new Organization.Subscription();

        Organization.Notifications notifications = new Organization.Notifications();
        Organization.RequestForTaxReceipt requestForTaxReceipt = new Organization.RequestForTaxReceipt();

        requestForTaxReceipt.setEnabled(false);
        requestForTaxReceipt.setObfuscateEmailAddress(false);
        notifications.setRequestForTaxReceipt(requestForTaxReceipt);

        subscription.setNotifications(notifications);

        Organization.Tools tools = new Organization.Tools();
        Organization.QrCodeGenerator qrCodeGenerator = new Organization.QrCodeGenerator();

        qrCodeGenerator.setEnabled(false);
        tools.setQrCodeGenerator(qrCodeGenerator);

        subscription.setTools(tools);

        organization.setSubscription(subscription);

        // Set organization in request
        organizationRequest.setOrganization(organization);

        // Set flags for default campaign and payment page
        organizationRequest.setCreateDefaultCampaign(true);
        organizationRequest.setCreateDefaultPaymentPage(true);

        // Call organization service to create the organization
        return organizationService.postOrganization(organizationRequest);
    }

    /**
     * Creates a membership in Athena API
     * @param organizationId The ID of the organization to create the membership for
     * @param checkoutData The checkout data containing user information
     * @return The membership response result
     */
    public CustomHttpResponse createMembership(int organizationId, CheckoutData checkoutData) {
        logger.logInfo("Inside BundlesStoreService.createMembership()");

        // Create membership request
        MembershipRequest membershipRequest = new MembershipRequest();

        // Set email from checkout data
        membershipRequest.setEmail(checkoutData.getEmail());

        // Set role to admin
        membershipRequest.setRole("admin");

        // Set status to active
        membershipRequest.setStatus("active");

        // Set login destination to tilled-onboarding/account-setup-begin
        membershipRequest.setLoginDestination(String.format("/companies/%d/tilled-onboarding/account-setup-begin", organizationId));

        // Call membership service to create the membership
        return membershipService.postMembership(organizationId, membershipRequest);
    }

    public Payload preparePayload(
            String salesRepId,
            CheckoutData checkoutData,
            List<CartData> cartDataList,
            PaymentData paymentData,
            String action) {

        // Make server side correction if any case
        cartDataList = bundleProductService.setBundlesPriceKeyAndCalculate(cartDataList);

        Payload payload = new Payload();
        try {
            // Set Header
            Payload.Header header = new Payload.Header();
            header.setTimestamp(new Date());

            header.setOrderFormType(FORM_TYPE_BUNDLES);

            if (AppConstants.NetsuiteEnvironmentOptions.PRODUCTION.equals(app.getNetsuiteEnvironment())) {
                header.setNsSOFormInternalID(PRODUCTION_BUNDLE_SO_FORM_INTERNAL_ID);
            } else {
                header.setNsSOFormInternalID(SANDBOX_BUNDLE_SO_FORM_INTERNAL_ID);
            }

            payload.setHeader(header);

            // Set Contact Information
            Payload.ContactInformation contactInformation = getContactInformation(checkoutData);
            payload.setContactInformation(contactInformation);

            // Set Order Details
            BundleOrderWrapper bundleOrderWrapper = bundleProductService.getOrderDetails(cartDataList);
            payload.setOrderDetails(bundleOrderWrapper.getOrderDetails());
            payload.setBundleOrders(bundleOrderWrapper.getBundleOrders());

            // Set Pricing
            Payload.Pricing pricing = bundleProductService.getPricing(checkoutData);
            payload.setPricing(pricing);

            // Set Billing
            Payload.Billing billing = getBilling(checkoutData);
            payload.setBilling(billing);

            // Set Shipping
            Payload.Shipping shipping = getShipping(checkoutData);
            payload.setShipping(shipping);

            // Set Customer Information
            Payload.CustomerInformation customerInformation = getCustomerInformation(checkoutData);
            payload.setCustomerInformation(customerInformation);

            // Set Organization Hierarchy
            Payload.OrganizationHierarchy organizationHierarchy = getOrganizationHierarchy();
            payload.setOrganizationHierarchy(organizationHierarchy);

            // Set Customer Category
            Payload.CustomerCategory customerCategory = getCustomerCategory(salesRepId);
            payload.setCustomerCategory(customerCategory);

            if (action != null) {

                if (action.equals(AppConstants.NetSuiteActions.ACTION_COMPLETE_ORDER_WITH_PAYMENT)) {

                    // Set Program Details
                    Payload.ProgramDetails programDetails = getProgramDetails();
                    payload.setProgramDetails(programDetails);

                    // Set Payment Information
                    Payload.PaymentInformation paymentInformation = getPaymentInformation(paymentData);
                    payload.setPaymentInformation(paymentInformation);
                }

                payload.setAction(action);
            }

            // Set Memo
            payload.setMemo("");

        } catch (Exception exception) {
            logger.logError(AppConstants.AppMessages.ERROR_PREPARING_PAYLOAD, exception);
        }
        System.out.println("✅ Payload prepared successfully: " + payload);
        return payload;
    }

    private Payload.ContactInformation getContactInformation(CheckoutData checkoutData) {
        Payload.ContactInformation contactInformation = new Payload.ContactInformation();
        contactInformation.setContactFirstName(checkoutData.getShippingFullName().split(" ", 2)[0]);
        contactInformation.setContactLastName(checkoutData.getShippingFullName().split(" ", 2)[1]);
        contactInformation.setContactPhone("+1 " + checkoutData.getPhone()); // Add country code
        contactInformation.setContactEmail(checkoutData.getEmail());
        contactInformation.setContactPrefContactMethod("Phone");
        return contactInformation;
    }



    private Payload.Shipping getShipping(CheckoutData checkoutData) {
        Payload.Shipping shipping = new Payload.Shipping();

        if (checkoutData == null) {
            return shipping;
        }

        shipping.setAttentionTo(checkoutData.getShippingFullName());
        shipping.setShippingAddr1(checkoutData.getShippingAddressLine1());
        shipping.setShippingAddr2(checkoutData.getShippingAddressLine2());
        shipping.setShippingCity(checkoutData.getShippingCity());
        shipping.setShippingState(checkoutData.getShippingState());
        shipping.setShippingCountry(checkoutData.getShippingCountry());
        shipping.setShippingZip(checkoutData.getShippingZipCode());
        shipping.setShippingPhone("+1 " + checkoutData.getShippingPhone()); // Add country code
        shipping.setShippingEmail(checkoutData.getShippingEmail());

        return shipping;
    }

    private Payload.Billing getBilling(CheckoutData checkoutData) {
        Payload.Billing billing = new Payload.Billing();

        if (checkoutData == null) {
            return billing;
        }

        billing.setBillingFirstName(checkoutData.getBillingFullName().split(" ", 2)[0].trim());
        billing.setBillingLastName(checkoutData.getBillingFullName().split(" ", 2)[1].trim());
        billing.setBillingAddr1(checkoutData.getBillingAddressLine1());
        billing.setBillingAddr2(checkoutData.getBillingAddressLine2());
        billing.setBillingCity(checkoutData.getBillingCity());
        billing.setBillingState(checkoutData.getBillingState());
        billing.setBillingCountry(checkoutData.getBillingCountry());
        billing.setBillingZip(checkoutData.getBillingZipCode());
        billing.setBillingPhone("+1 " + checkoutData.getBillingPhone());
        billing.setBillingEmail(checkoutData.getBillingEmail());

        return billing;
    }

    private Payload.CustomerInformation getCustomerInformation(CheckoutData checkoutData) {
        Payload.CustomerInformation customerInformation = new Payload.CustomerInformation();

        customerInformation.setCustomerName(checkoutData.getOrganizationName());
        customerInformation.setCustomerPhone("+1 " + checkoutData.getPhone()); // Add country code
        customerInformation.setCustomerEmail(checkoutData.getEmail());
        customerInformation.setCustomerPrefContactMethod("Phone");
        customerInformation.setCampaignStartDate(checkoutData.getCampaignStartDate());

        return customerInformation;
    }

    private Payload.OrganizationHierarchy getOrganizationHierarchy() {
        Payload.OrganizationHierarchy organizationHierarchy = new Payload.OrganizationHierarchy();

        organizationHierarchy.setParent(0);
        organizationHierarchy.setChild(0);
        organizationHierarchy.setGrandChild(0);

        return organizationHierarchy;
    }

    private Payload.CustomerCategory getCustomerCategory(String salesRepId) {
        Payload.CustomerCategory customerCategory = new Payload.CustomerCategory();
        boolean isSalesRepExist = isSalesRepIdExist(salesRepId);

        if (salesRepId != null && isSalesRepExist) {
            customerCategory.setCustomerType("Enterprise");
            customerCategory.setCustomerReferral(getSalesRepNameById(salesRepId));
        } else {
            customerCategory.setCustomerType("");
            customerCategory.setCustomerReferral("");
        }

        return customerCategory;
    }

    private Payload.ProgramDetails getProgramDetails() {
        Payload.ProgramDetails programDetails = new Payload.ProgramDetails();
        programDetails.setPartnerSponsoring("");
        programDetails.setSponsorshipBeneficiary("");
        programDetails.setProgramName("");
        programDetails.setProgramLeadName("");
        programDetails.setProgramLeadPhone("");
        programDetails.setProgramLeadEmail("");
        return programDetails;
    }

    private Payload.PaymentInformation getPaymentInformation(PaymentData paymentData) {
        Payload.PaymentInformation paymentInformation = new Payload.PaymentInformation();
        paymentInformation.setNameOnCard(paymentData.getNameOnCard().trim());
        paymentInformation.setCardNumber(paymentData.getCardNumber().replaceAll(" ", "").trim());
        paymentInformation.setExpirationDate(paymentData.getExpiration());
        paymentInformation.setCvv(paymentData.getCvv());
        return paymentInformation;
    }

    public CampaignDateValidation isCampaignStartDateValid(Date campaignStartDate, Date today) {
        try {
            long diffInMilliseconds = Math.abs(today.getTime() - campaignStartDate.getTime());
            long daysBetween = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);

            // Check if the difference is 28 days or more
            if (daysBetween >= 28) {
                logger.logInfo("User selected valid campaign start date : (" + daysBetween + " days) " + campaignStartDate);
                return new CampaignDateValidation(true, String.valueOf(daysBetween));
            } else {
                logger.logInfo("User selected invalid campaign start date : (" + daysBetween + " days) " + campaignStartDate);
                return new CampaignDateValidation(false, String.valueOf(daysBetween));
            }
        } catch (Exception e) {
            return new CampaignDateValidation(false, "");
        }
    }

}
