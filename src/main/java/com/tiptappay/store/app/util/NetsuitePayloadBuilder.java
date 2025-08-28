package com.tiptappay.store.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tiptappay.store.app.model.support.SupportCaseForm;

import java.util.*;
import java.util.stream.Collectors;

public final class NetsuitePayloadBuilder {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private NetsuitePayloadBuilder() {}

    /** Build the JSON for the NetSuite custom API from a SupportCaseForm. */
    public static String buildSupportPayload(SupportCaseForm f, String originOffset) {
        Map<String, Object> root = new LinkedHashMap<>();

        // --- config ---
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("origin", nvl(originOffset, "-5"));
        config.put("company", Map.of("enableSearch", true));
        config.put("contact", Map.of("enableSearch", true, "enableCreate", true));
        root.put("config", config);

        // --- primaryInformation ---
        Map<String, Object> primary = new LinkedHashMap<>();
        Map<String, Object> contact = new LinkedHashMap<>();
        contact.put("firstName", nz(f.getContactFirstName()));
        contact.put("lastName",  nz(f.getContactLastName()));
        contact.put("email",     nz(f.getContactEmail()));
        contact.put("phone",     joinPhone(f.getContactPhoneCountryCode(), f.getContactPhone()));

        primary.put("ticketName",  nvl(f.getTicketName(), "General Inquiry"));
        primary.put("caseIssue",   nvl(f.getCaseIssue(), "1"));
        primary.put("companyName", nz(f.getCompanyName()));
        primary.put("contact",     contact);
        root.put("primaryInformation", primary);

        // --- returnLabelRequest ---
        Map<String, Object> senderInfo = new LinkedHashMap<>();
        senderInfo.put("name",         nz(f.getSenderName()));
        senderInfo.put("email",        nz(f.getSenderEmail()));
        senderInfo.put("phone",        joinPhone(f.getSenderPhoneCountryCode(), f.getSenderPhone()));
        senderInfo.put("address",      nz(f.getSenderAddress()));
        senderInfo.put("organization", nz(f.getSenderOrganization()));

        Map<String, Object> rlr = new LinkedHashMap<>();
        rlr.put("senderInformation", senderInfo);
        rlr.put("numOfDeviceReturned", f.getNumOfDeviceReturned());
        root.put("returnLabelRequest", rlr);

        // --- existingOrderInquiry ---
        Map<String, Object> eoi = new LinkedHashMap<>();
        eoi.put("orderNumbers", orderNumbers(f));
        root.put("existingOrderInquiry", eoi);

        // --- technicalIssue ---
        Map<String, Object> tech = new LinkedHashMap<>();
        tech.put("productType", nz(f.getTechnicalIssueProductType()));

        Map<String, Object> device = new LinkedHashMap<>();
        device.put("ledSequence",   nz(f.getTechnicalIssueDeviceLedSequence()));
        device.put("poweredMin4Hr", nz(f.getTechnicalIssueDevicePoweredMin4Hr()));
        device.put("firstBeep",     nz(f.getTechnicalIssueDeviceFirstBeep()));
        device.put("secondBeep",    nz(f.getTechnicalIssueDeviceSecondBeep()));
        device.put("serialNumbers", nz(f.getTechnicalIssueDeviceSerialNumbersManualEntry()));
        tech.put("device", device);

        Map<String, Object> battery = new LinkedHashMap<>();
        battery.put("numOfBatteries", nz(f.getTechnicalIssueBatteryNumOfBatteries()));
        battery.put("type",           nz(f.getTechnicalIssueBatteryType()));
        battery.put("issueType",      nz(f.getTechnicalIssueBatteryIssueType()));
        tech.put("battery", battery);

        Map<String, Object> cable = new LinkedHashMap<>();
        cable.put("numOfCable", nz(f.getTechnicalIssueCableNumOfCable()));
        cable.put("type",       nz(f.getTechnicalIssueCableType()));
        cable.put("issueType",  nz(f.getTechnicalIssueCableIssueType()));
        tech.put("cable", cable);

        Map<String, Object> wallPlug = new LinkedHashMap<>();
        wallPlug.put("numberOfWallPlugs", nz(f.getTechnicalIssueWallPlugNumberOfWallPlugs()));
        wallPlug.put("issueType",         nz(f.getTechnicalIssueWallPlugIssueType()));
        tech.put("wallPlug", wallPlug);

        Map<String, Object> another = new LinkedHashMap<>();
        another.put("issueType", nz(f.getTechnicalIssueAnotherIssueType()));
        tech.put("another", another);

        root.put("technicalIssue", tech);

        // --- message ---
        root.put("message", nvl(f.getMessage(), ""));

        try {
            return MAPPER.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize NetSuite payload", e);
        }
    }

    // ---------- helpers ----------
    private static String nz(String s) { return s == null ? "" : s.trim(); }
    private static String nvl(String s, String def) {
        if (s == null) return def;
        String t = s.trim();
        return t.isEmpty() ? def : t;
    }
    private static String joinPhone(String code, String number) {
        String c = nvl(code, "").trim();
        String n = nvl(number, "").trim();
        if (c.isEmpty() && n.isEmpty()) return "";
        if (c.isEmpty()) return n;
        if (n.isEmpty()) return c;
        return c + " " + n;
    }
    private static String orderNumbers(SupportCaseForm f) {
        String manual = nvl(f.getOrderNumbersManualEntry(), "");
        if (!manual.isBlank()) return manual;
        if (f.getSelectedOrders() != null && !f.getSelectedOrders().isEmpty()) {
            return f.getSelectedOrders().stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining(","));
        }
        return "";
    }
}
