package com.tiptappay.store.app.service;

import com.tiptappay.store.app.constant.AppConstants;
import com.tiptappay.store.app.constant.SaUsOrganizationMap;
import com.tiptappay.store.app.constant.StoreConstants;
import com.tiptappay.store.app.dto.canada.CanadaStoreShoppingBag;
import com.tiptappay.store.app.dto.mosques.store.MosquesStoreShoppingBag;
import com.tiptappay.store.app.dto.saus.store.SaUsStoreShoppingBag;
import com.tiptappay.store.app.dto.us.store.UsStoreShoppingBag;
import com.tiptappay.store.app.model.ContactAndShipping;
import com.tiptappay.store.app.model.Hierarchy;
import com.tiptappay.store.app.model.Payment;
import com.tiptappay.store.app.model.payload.Payload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class PayloadService {

    @Value("${netsuite.environment}")
    private String netsuiteEnvironment;

    public Payload preparePayload(String storeId,
                                  Object shoppingBag,
                                  ContactAndShipping contactAndShipping,
                                  Payment payment,
                                  String action) {

        Payload payload = new Payload();
        try {
            // Set Header
            Payload.Header header = new Payload.Header();
            header.setTimestamp(new Date());

            header.setOrderFormType(getOrderFormTypeByStoreId(storeId));
            header.setNsSOFormInternalID(getNsSOFormInternalIDByStoreId(storeId));

            payload.setHeader(header);

            // Set Contact Information
            Payload.ContactInformation contactInformation = getContactInformation(contactAndShipping);
            payload.setContactInformation(contactInformation);

            // Set Order Details
            Payload.OrderDetails orderDetails = getOrderDetails(storeId, shoppingBag);
            payload.setOrderDetails(orderDetails);
            if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
                List<Payload.BundleOrder> bundleOrders = new ArrayList<>();

                // Map orderDetails to internal NetSuite PROD IDs and rates
                if (orderDetails != null) {
                    CanadaStoreShoppingBag canadaBag = (CanadaStoreShoppingBag) shoppingBag;
                    //dev
                   addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getThreeDeviceDisplayEnglishQuantity()), "2048", "1", "300.00");
                   addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getThreeDeviceDisplayFrenchQuantity()), "2049", "1", "300.00");
                   addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getThreeDeviceDisplayBilingualQuantity()), "2050", "1", "300.00");

                   addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getSingleCurveEnglishQuantity()), "2045", "1", "150.00");
                   addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getSingleCurveFrenchQuantity()), "2046", "1", "150.00");
                   addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getSingleCurveBilingualQuantity()), "2047", "1", "150.00");

                    //prod
                    // addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getThreeDeviceDisplayEnglishQuantity()), "2157", "1", "300.00");
                    // addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getThreeDeviceDisplayFrenchQuantity()), "2158", "1", "300.00");
                    // addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getThreeDeviceDisplayBilingualQuantity()), "2159", "1", "300.00");

                    // addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getSingleCurveEnglishQuantity()), "2160", "1", "150.00");
                    // addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getSingleCurveFrenchQuantity()), "2161", "1", "150.00");
                    // addBundleOrderIfQtyPositive(bundleOrders, String.valueOf(canadaBag.getSingleCurveBilingualQuantity()), "2162", "1", "150.00");
                }

                payload.setBundleOrders(bundleOrders);
            }


            // Set Pricing
            Payload.Pricing pricing = getPricing(storeId, payment);
            payload.setPricing(pricing);

            // Set Billing
            Payload.Billing billing = getBilling(payment, contactAndShipping, storeId);
            payload.setBilling(billing);

            // Set Shipping
            Payload.Shipping shipping = getShipping(contactAndShipping);
            payload.setShipping(shipping);

            // Set Customer Information
            Payload.CustomerInformation customerInformation = getCustomerInformation(contactAndShipping, storeId);
            payload.setCustomerInformation(customerInformation);

            // Set Organization Hierarchy
            Payload.OrganizationHierarchy organizationHierarchy = getOrganizationHierarchy(storeId, contactAndShipping);
            payload.setOrganizationHierarchy(organizationHierarchy);

            // Set Customer Category
            Payload.CustomerCategory customerCategory = getCustomerCategory(storeId);
            payload.setCustomerCategory(customerCategory);

            if (action != null) {
                // Always set the action
                payload.setAction(action);

                // Only include payment & program details if NOT Canada Store
                if (action.equals(AppConstants.NetSuiteActions.ACTION_COMPLETE_ORDER_WITH_PAYMENT)
                        && !StoreConstants.CanadaStore.STORE_ID.equals(storeId)) {

                    // Set Program Details
                    Payload.ProgramDetails programDetails = getProgramDetails();
                    payload.setProgramDetails(programDetails);

                    // Set Payment Information
                    Payload.PaymentInformation paymentInformation = getPaymentInformation(payment);
                    payload.setPaymentInformation(paymentInformation);
                }
            }
            payload.setDivision(contactAndShipping.getDivision());
            payload.setMinistryName(contactAndShipping.getMinistryName());
            payload.setRespcNumber(contactAndShipping.getRespcNumber());


            // Set Memo
            payload.setMemo("");

        } catch (Exception exception) {
            log.error(AppConstants.AppMessages.ERROR_PREPARING_PAYLOAD + " : {}", exception.getMessage());
        }

        return payload;
    }

    private void addBundleOrderIfQtyPositive(List<Payload.BundleOrder> list, String quantityStr, String id, String priceLevel, String rate) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity > 0) {
                list.add(new Payload.BundleOrder(id, String.valueOf(quantity), priceLevel, rate));
            }
        } catch (NumberFormatException e) {
            log.warn("Invalid quantity: " + quantityStr + " for bundle ID " + id);
        }
    }



    private Payload.CustomerInformation getCustomerInformation(ContactAndShipping contactAndShipping, String storeId) throws ParseException {
        Payload.CustomerInformation customerInformation = new Payload.CustomerInformation();

        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            if (contactAndShipping.isFreeCustomerNameEntryAllowed()) {
                customerInformation.setCustomerName(contactAndShipping.getCustomerNameFree());
            } else {
                String[] sp = contactAndShipping.getCustomerGrandChild().split(",");
                for (String st : sp) {
                    if (!st.isEmpty()) {
                        customerInformation.setCustomerName(st);
                        break;
                    }
                }
            }
        }
        if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
            customerInformation.setCustomerName("Salvation Army Canada");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Ensure consistent base

            Date baseDate = sdf.parse("2025-11-01");

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTime(baseDate);
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            customerInformation.setCampaignStartDate(calendar.getTime());
        }

        else {
            // Ensure fallback value for Canada Store or when name is null
            String customerName = contactAndShipping.getCustomerName();
            if (customerName == null || customerName.isBlank()) {
                customerName = contactAndShipping.getContactFirstName() + " " + contactAndShipping.getContactLastName();
            }
            customerInformation.setCustomerName(customerName);
        }

        // Fix phone
        String phone = contactAndShipping.getContactPhone();
        String phoneCode = contactAndShipping.getContactPhoneCountryCode();
        customerInformation.setCustomerPhone((phoneCode != null ? phoneCode : "") + " " + (phone != null ? phone : ""));

        customerInformation.setCustomerEmail(contactAndShipping.getContactEmail());

        // Fix contact method fallback
        String method = contactAndShipping.getContactPrefContactMethod();
        customerInformation.setCustomerPrefContactMethod(method != null ? method : "Email");

        return customerInformation;
    }

    private Payload.ContactInformation getContactInformation(ContactAndShipping contactAndShipping) {
        Payload.ContactInformation contactInformation = new Payload.ContactInformation();
        String phoneCountryCode = contactAndShipping.getContactPhoneCountryCode() != null ? contactAndShipping.getContactPhoneCountryCode() : "";
        String phone = contactAndShipping.getContactPhone() != null ? contactAndShipping.getContactPhone() : "";
        contactInformation.setContactFirstName(contactAndShipping.getContactFirstName());
        contactInformation.setContactLastName(contactAndShipping.getContactLastName());
        contactInformation.setContactPhone(phoneCountryCode + " " + phone);
        contactInformation.setContactEmail(contactAndShipping.getContactEmail());
        contactInformation.setContactPrefContactMethod(contactAndShipping.getContactPrefContactMethod());

        return contactInformation;
    }

    private Payload.CustomerCategory getCustomerCategory(String storeId) {
        Payload.CustomerCategory customerCategory = new Payload.CustomerCategory();

        if (storeId.equals(StoreConstants.USStore.STORE_ID)) {
            customerCategory.setCustomerType(StoreConstants.USStore.CUSTOMER_TYPE);
            customerCategory.setCustomerReferral(StoreConstants.USStore.CUSTOMER_REFERRAL);
        }

        if (storeId.equals(StoreConstants.MosquesStore.STORE_ID)) {
            customerCategory.setCustomerType(StoreConstants.MosquesStore.CUSTOMER_TYPE);
            customerCategory.setCustomerReferral(StoreConstants.MosquesStore.CUSTOMER_REFERRAL);
        }

        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            customerCategory.setCustomerType(StoreConstants.SaUsStore.CUSTOMER_TYPE);
            customerCategory.setCustomerReferral(StoreConstants.SaUsStore.CUSTOMER_REFERRAL);
        }
        if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
            customerCategory.setCustomerType(StoreConstants.CanadaStore.CUSTOMER_TYPE);
            customerCategory.setCustomerReferral(StoreConstants.CanadaStore.CUSTOMER_REFERRAL);
        }

        return customerCategory;
    }

    private Payload.OrganizationHierarchy getOrganizationHierarchy(String storeId, ContactAndShipping contactAndShipping) {
        Payload.OrganizationHierarchy organizationHierarchy = new Payload.OrganizationHierarchy();

        if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.SANDBOX)) {
            if (storeId.equals(StoreConstants.USStore.STORE_ID)) {
                organizationHierarchy.setParent(StoreConstants.USStore.ORGANIZATION_PARENT_SB);
                organizationHierarchy.setChild(StoreConstants.USStore.ORGANIZATION_CHILD_SB);
                organizationHierarchy.setGrandChild(1);
            }
        }

        if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) {
            if (storeId.equals(StoreConstants.USStore.STORE_ID)) {
                organizationHierarchy.setParent(StoreConstants.USStore.ORGANIZATION_PARENT_PROD);
                organizationHierarchy.setChild(StoreConstants.USStore.ORGANIZATION_CHILD_PROD);
                organizationHierarchy.setGrandChild(1);
            }

        }
        if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
            if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.SANDBOX)) {
                organizationHierarchy.setChild(StoreConstants.CanadaStore.ORGANIZATION_CHILD_SB);
                organizationHierarchy.setGrandChild(1);
            }

            if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) {
                organizationHierarchy.setChild(StoreConstants.CanadaStore.ORGANIZATION_CHILD_PROD);
                organizationHierarchy.setGrandChild(1);
            }
        }

        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            String grandChild = "";
            String[] sp = contactAndShipping.getCustomerGrandChild().split(",");
            for (String st : sp) {
                if (!st.isEmpty()) {
                    grandChild = st;
                    break;
                }
            }

            Hierarchy hierarchy = SaUsOrganizationMap.getHierarchy(
                    contactAndShipping.getCustomerChild(),
                    grandChild
            );

            organizationHierarchy.setParent(hierarchy.getParent());
            organizationHierarchy.setChild(hierarchy.getChild());
            organizationHierarchy.setGrandChild(hierarchy.getGrandChild());
        }

        return organizationHierarchy;
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

    private Payload.PaymentInformation getPaymentInformation(Payment payment) {
        Payload.PaymentInformation paymentInformation = new Payload.PaymentInformation();
        paymentInformation.setCardNumber(payment == null ? "" : payment.getCardNumber());
        paymentInformation.setExpirationDate(payment == null ? "" : payment.getCardExpiryMonth() + "/" + payment.getCardExpiryYear());
        paymentInformation.setCvv(payment == null ? "" : payment.getCardCVV());
        return paymentInformation;
    }

    private Payload.Billing getBilling(Payment payment, ContactAndShipping contactAndShipping, String storeId) {
        Payload.Billing billing = new Payload.Billing();

        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            billing.setBillingFirstName(contactAndShipping.getContactFirstName());
            billing.setBillingLastName(contactAndShipping.getContactLastName());
            billing.setBillingAddr1("");
            billing.setBillingAddr2("");
            billing.setBillingCity("");
            billing.setBillingState("");
            billing.setBillingCountry("");
            billing.setBillingZip("");
        }
        else if (storeId.equals(StoreConstants.CanadaStore.STORE_ID) && payment == null) {
            billing.setBillingFirstName("Janet");
            billing.setBillingLastName("Park");
            billing.setBillingAddr1("2 Overlea Blvd");
            billing.setBillingAddr2(""); // optional
            billing.setBillingCity("Toronto");
            billing.setBillingState("Ontario");
            billing.setBillingCountry("Canada");
            billing.setBillingZip("M4H 1P4");
            billing.setBillingPhone("416-467-3170");
            billing.setBillingEmail("Janet.Park@salvationarmy.ca");
            billing.setBillingAttention(null); // explicitly set to null so your info does not appear
        }

        else {
            String firstName = "";
            String lastName = "";
            if (payment != null) {
                String nameOnCard = payment.getNameOnCard().trim();
                int lastSpaceIndex = nameOnCard.lastIndexOf(' ');
                if (lastSpaceIndex == -1) {
                    firstName = nameOnCard;
                    lastName = "";
                } else {
                    firstName = nameOnCard.substring(0, lastSpaceIndex);
                    lastName = nameOnCard.substring(lastSpaceIndex + 1);
                }
            }

            billing.setBillingFirstName(payment == null ? "" : firstName);
            billing.setBillingLastName(payment == null ? "" : lastName);
            billing.setBillingAddr1(payment == null ? "" : payment.getBillingAddr1());
            billing.setBillingAddr2(payment == null ? "" : payment.getBillingAddr2());
            billing.setBillingCity(payment == null ? "" : payment.getBillingCity());
            billing.setBillingState(payment == null ? "" :
                    payment.getBillingCountry().equalsIgnoreCase("canada") ?
                            payment.getBillingProvince() : payment.getBillingState());
            billing.setBillingCountry(payment == null ? "" : payment.getBillingCountry());
            billing.setBillingZip(payment == null ? "" :
                    payment.getBillingCountry().equalsIgnoreCase("canada") ?
                            payment.getBillingPostalCode() : payment.getBillingZip());
        }

        return billing;
    }

    private Payload.Shipping getShipping(ContactAndShipping contactAndShipping) {
        Payload.Shipping shipping = new Payload.Shipping();
        shipping.setAttentionTo(contactAndShipping.getAttentionTo());
        shipping.setShippingAddr1(contactAndShipping.getShippingAddr1());
        shipping.setShippingAddr2(contactAndShipping.getShippingAddr2());
        shipping.setShippingCity(contactAndShipping.getShippingCity());

        shipping.setShippingState(contactAndShipping.getShippingCountry().equalsIgnoreCase("canada")
                ? contactAndShipping.getShippingProvince()
                : contactAndShipping.getShippingState()
        );

        shipping.setShippingCountry(contactAndShipping.getShippingCountry());
        shipping.setShippingZip(
                contactAndShipping.getShippingCountry().equalsIgnoreCase("canada") ?
                        contactAndShipping.getShippingPostalCode() : contactAndShipping.getShippingZip()
        );
        shipping.setShippingPhone(contactAndShipping.getContactPhone());
        shipping.setShippingEmail(contactAndShipping.getContactEmail());
        return shipping;
    }

    // Added default values
    private Payload.Pricing getPricing(String storeId, Payment payment) {
        Payload.Pricing payloadPricing = new Payload.Pricing();

        payloadPricing.setOverrideSetupFee(true);
        payloadPricing.setOverrideMonthlyDeviceRentalFee(true);

        if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
            payloadPricing.setCurrency(StoreConstants.CanadaStore.CURRENCY);
            payloadPricing.setSetupFeeAmount(StoreConstants.CanadaStore.FEE_SETUP_IN_CENTS / 100);
            payloadPricing.setMonthlyDeviceRentalFeeAmount(StoreConstants.CanadaStore.FEE_RENTAL_IN_CENTS / 100);
        }
        if (storeId.equals(StoreConstants.USStore.STORE_ID)) {
            payloadPricing.setCurrency(StoreConstants.USStore.CURRENCY);
            payloadPricing.setSetupFeeAmount(StoreConstants.USStore.FEE_SETUP_IN_CENTS / 100);
            payloadPricing.setMonthlyDeviceRentalFeeAmount(StoreConstants.USStore.FEE_RENTAL_IN_CENTS / 100);
        }

        if (storeId.equals(StoreConstants.MosquesStore.STORE_ID)) {
            if (payment != null) {
                payloadPricing.setCurrency(payment.getBillingCountry().equalsIgnoreCase("Canada") ? "CAD" : "USD");
            } else {
                payloadPricing.setCurrency("");
            }

            payloadPricing.setSetupFeeAmount(StoreConstants.MosquesStore.FEE_SETUP_IN_CENTS / 100);
            payloadPricing.setMonthlyDeviceRentalFeeAmount(StoreConstants.MosquesStore.FEE_RENTAL_IN_CENTS / 100);
        }

        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            payloadPricing.setCurrency(StoreConstants.SaUsStore.CURRENCY);
            payloadPricing.setSetupFeeAmount(StoreConstants.SaUsStore.FEE_SETUP_IN_CENTS / 100);
            payloadPricing.setMonthlyDeviceRentalFeeAmount(StoreConstants.SaUsStore.FEE_RENTAL_IN_CENTS / 100);
        }

        return payloadPricing;
    }

    private Payload.OrderDetails getOrderDetails(String storeId, Object shoppingBag) {
        Payload.OrderDetails orderDetails = new Payload.OrderDetails();

        if (storeId.equals(StoreConstants.USStore.STORE_ID)) {
            UsStoreShoppingBag usStoreShoppingBag = (UsStoreShoppingBag) shoppingBag;

            orderDetails.setNumberOf2DollarDevices("0");
            orderDetails.setNumberOfClips(String.valueOf(usStoreShoppingBag.getMiniClipKitQty()));
            orderDetails.setNumberOfKettleDisplay("0");
            orderDetails.setNumberOfFloorStands("0");
            orderDetails.setNumberOf5DollarDevices(String.valueOf(usStoreShoppingBag.getDevice5DollarQty()));
            orderDetails.setNumberOf10DollarDevices(String.valueOf(usStoreShoppingBag.getDevice10DollarQty()));
            orderDetails.setNumberOf20DollarDevices(String.valueOf(usStoreShoppingBag.getDevice20DollarQty()));
            orderDetails.setNumberOfBatteryForKettleDisplay("0");
        }

        if (storeId.equals(StoreConstants.MosquesStore.STORE_ID)) {
            MosquesStoreShoppingBag mosquesStoreShoppingBag = (MosquesStoreShoppingBag) shoppingBag;

            orderDetails.setNumberOf2DollarDevices("0");
            orderDetails.setNumberOfClips("0");
            orderDetails.setNumberOfKettleDisplay("0");
            orderDetails.setNumberOfFloorStands(String.valueOf(mosquesStoreShoppingBag.getFloorStandQty()));
            orderDetails.setNumberOf5DollarDevices(String.valueOf(mosquesStoreShoppingBag.getDevice5DollarQty()));
            orderDetails.setNumberOf10DollarDevices(String.valueOf(mosquesStoreShoppingBag.getDevice10DollarQty()));
            orderDetails.setNumberOf20DollarDevices(String.valueOf(mosquesStoreShoppingBag.getDevice20DollarQty()));
            orderDetails.setNumberOfBatteryForKettleDisplay("0");
        }

        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            SaUsStoreShoppingBag saUsStoreShoppingBag = (SaUsStoreShoppingBag) shoppingBag;

            orderDetails.setNumberOf2DollarDevices("0");
            orderDetails.setNumberOfClips("0");
            orderDetails.setNumberOfKettleDisplay(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOfFloorStands("0");
            orderDetails.setNumberOf5DollarDevices(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOf10DollarDevices(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOf20DollarDevices(String.valueOf(saUsStoreShoppingBag.getKettleDisplayQty()));
            orderDetails.setNumberOfBatteryForKettleDisplay(String.valueOf(saUsStoreShoppingBag.getBatteryForKettleDisplayQty()));
        }
        if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
            CanadaStoreShoppingBag canadaBag = (CanadaStoreShoppingBag) shoppingBag;

            orderDetails.setNumberOf2DollarDevices("0");
            orderDetails.setNumberOfClips("0");
            orderDetails.setNumberOfKettleDisplay(String.valueOf(canadaBag.getDesktopDisplayQty()));
            orderDetails.setNumberOfFloorStands("0");
            orderDetails.setNumberOf5DollarDevices(String.valueOf(canadaBag.getDevice5DollarQty()));
            orderDetails.setNumberOf10DollarDevices(String.valueOf(canadaBag.getDevice10DollarQty()));
            orderDetails.setNumberOf20DollarDevices(String.valueOf(canadaBag.getDevice20DollarQty()));
            orderDetails.setNumberOfBatteryForKettleDisplay("0");

            // New fields from CanadaStoreShoppingBag
//            orderDetails.setThreeDeviceDisplayEnglishQuantity(String.valueOf(canadaBag.getThreeDeviceDisplayEnglishQuantity()));
//            orderDetails.setThreeDeviceDisplayFrenchQuantity(String.valueOf(canadaBag.getThreeDeviceDisplayFrenchQuantity()));
//            orderDetails.setThreeDeviceDisplayBilingualQuantity(String.valueOf(canadaBag.getThreeDeviceDisplayBilingualQuantity()));
//
//            orderDetails.setSingleCurveEnglishQuantity(String.valueOf(canadaBag.getSingleCurveEnglishQuantity()));
//            orderDetails.setSingleCurveFrenchQuantity(String.valueOf(canadaBag.getSingleCurveFrenchQuantity()));
//            orderDetails.setSingleCurveBilingualQuantity(String.valueOf(canadaBag.getSingleCurveBilingualQuantity()));
        }


        return orderDetails;
    }


    private String getOrderFormTypeByStoreId(String storeId) {
        switch (storeId) {
            case StoreConstants.USStore.STORE_ID -> {
                return "Order Form";
            }
            case StoreConstants.MosquesStore.STORE_ID -> {
                return "Mosque Order Form";
            }
            case StoreConstants.SaUsStore.STORE_ID -> {
                return "Salvation Army USA Order Form";
            }
            case StoreConstants.CanadaStore.STORE_ID -> {
                return "Salvation Army Canada Order Form";
            }
            default -> {
                return "unknown";
            }
        }
    }

    private String getNsSOFormInternalIDByStoreId(String storeId) {
        if (storeId.equals(StoreConstants.SaUsStore.STORE_ID)) {
            if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.SANDBOX)) {
                return "181";
            }
            if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) {
                return "180";
            }
        }
        if (storeId.equals(StoreConstants.CanadaStore.STORE_ID)) {
            if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.SANDBOX)) {
                return "184";
            }
            if (netsuiteEnvironment.equals(AppConstants.NetsuiteEnvironmentOptions.PRODUCTION)) {
                return "183";
            }
        }
        return "";
    }
}




