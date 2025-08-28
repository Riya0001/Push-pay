package com.tiptappay.store.app.controller.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tiptappay.store.app.model.support.SupportCaseForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/support")
@RequiredArgsConstructor
public class SupportForm {

    // Use the existing OAuth service in your project
    private final com.tiptappay.store.app.service.oauth.OauthServices oauthService;

    // Prefer Spring’s injected mapper but ensure nulls are included like Gson.serializeNulls()
    private final ObjectMapper objectMapper;

    /** Trim all incoming string values; convert empty strings to nulls. */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /** Ensure the form object exists on the model for all mappings here. */
    @ModelAttribute("supportCaseForm")
    public SupportCaseForm supportCaseForm() {
        return new SupportCaseForm();
    }

    /** Optional legacy/alternate page. */
    @GetMapping("/form")
    public String showLegacy(@ModelAttribute("supportCaseForm") SupportCaseForm form) {
        log.info("Rendering legacy support form");
        return "support/support-form";
    }

    /** New public support page. */
    @GetMapping("/new")
    public String showNew(@ModelAttribute("supportCaseForm") SupportCaseForm form, Model model) {
        log.info("Rendering NEW public support form");
        return "support/support-new";
    }

    /** Handle submission (PRG) + POST to NetSuite. */
    @PostMapping("/new")
    public String submitNew(
            @Valid @ModelAttribute("supportCaseForm") SupportCaseForm form,
            BindingResult binding,
            RedirectAttributes ra,
            HttpServletRequest request
    ) {
        log.info("Submitting NEW public support form: {}", form);

        // Public form always uses manual entry (no pickers)
        form.setManualDeviceEntryEnabled(true);
        form.setManualOrderNumbersEntryEnabled(true);

        // If JS didn’t set ticketName, infer it from visible dropdown label (optional enhancement)
        if (isBlank(form.getTicketName())) {
            String label = nvl(request.getParameter("subjectLabel"));
            if (label.toLowerCase().contains("existing order")) {
                form.setTicketName("Existing Order Inquiry");
            } else {
                form.setTicketName("General Inquiry");
            }
        }

        // Require Sales Order when "Existing Order Inquiry"
        if ("Existing Order Inquiry".equals(form.getTicketName())
                && isBlank(form.getOrderNumbersManualEntry())) {
            binding.rejectValue("orderNumbersManualEntry", "required",
                    "Sales Order Number is required");
        }

        if (binding.hasErrors()) {
            log.warn("Validation errors ({}): {}", binding.getErrorCount(), binding.getAllErrors());
            return "support/support-new";
        }

        // --- Build NetSuite payload and POST ---
        Map<String, Object> payload = buildNetsuitePayload(form);

        String jsonPayload;
        try {
            // copy to avoid mutating global config and to force inclusion of nulls like Gson.serializeNulls()
            ObjectMapper mapper = objectMapper.copy()
                    .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                    .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);

            jsonPayload = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize NetSuite payload", e);
            ra.addFlashAttribute("error", "Sorry, we couldn’t submit your request. Please try again.");
            return "redirect:/support/new";
        }

        log.info("NetSuite payload: {}", jsonPayload);

        try {
            String responseBody = String.valueOf(oauthService.runApiRequest(
                    "POST",
                    "customscript_oa_customapi",
                    new HashMap<>(),          // query params, if any
                    jsonPayload               // body
            ));
            log.info("NetSuite response: {}", responseBody);

            String caseNumber = null;
            try {
                var node = objectMapper.readTree(responseBody);
                caseNumber = node.path("caseNumber").asText(null);
            } catch (Exception parseEx) {
                log.warn("Could not parse caseNumber from NetSuite response", parseEx);
            }

            ra.addFlashAttribute("caseNumber", caseNumber);           // available for the next request (after redirect)
            ra.addFlashAttribute("supportCaseResponseJson", responseBody); // if you still want the raw JSON on the page
            ra.addFlashAttribute("toast", "Thanks! Your request has been submitted.");

            return "redirect:/support/thanks";
        } catch (Exception ex) {
            log.error("Failed to POST support case to NetSuite", ex);
            ra.addFlashAttribute("error", "Sorry, we couldn’t submit your request. Please try again.");
            return "redirect:/support/new";
        }
    }

    /** Simple confirmation page. */
    @GetMapping("/thanks")
    public String thanks() {
        return "support/support-thanks";
    }

    // ----------------- helpers -----------------

    private static boolean isBlank(String s) { return s == null || s.isBlank(); }
    private static String nvl(String s) { return s == null ? "" : s; }

    /**
     * Build the exact JSON structure your NetSuite endpoint expects:
     *
     * {
     *   "config": {...},
     *   "primaryInformation": {...},
     *   "returnLabelRequest": {...},
     *   "existingOrderInquiry": {...},
     *   "technicalIssue": {...},
     *   "message": "..."
     * }
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> buildNetsuitePayload(SupportCaseForm f) {
        Map<String, Object> root = new LinkedHashMap<>();

        // --- config ---
        Map<String, Object> company = new LinkedHashMap<>();
        company.put("enableSearch", true);

        Map<String, Object> contactCfg = new LinkedHashMap<>();
        contactCfg.put("enableSearch", true);
        contactCfg.put("enableCreate", true);

        Map<String, Object> config = new LinkedHashMap<>();
        config.put("origin", "-5");      // “Other / Website” marker
        config.put("company", company);
        config.put("contact", contactCfg);
        root.put("config", config);

        // --- primaryInformation ---
        Map<String, Object> primary = new LinkedHashMap<>();
        primary.put("ticketName", nvl(f.getTicketName()));
        primary.put("caseIssue", isBlank(f.getCaseIssue()) ? "1" : f.getCaseIssue());
        primary.put("companyName", nvl(f.getCompanyName()));

        Map<String, Object> contact = new LinkedHashMap<>();
        contact.put("firstName", nvl(f.getContactFirstName()));
        contact.put("lastName", nvl(f.getContactLastName()));
        contact.put("email", nvl(f.getContactEmail()));

        String phone = (nvl(f.getContactPhoneCountryCode()) + " " + nvl(f.getContactPhone())).trim();
        contact.put("phone", isBlank(phone) ? "" : phone);
        primary.put("contact", contact);

        root.put("primaryInformation", primary);

        // --- returnLabelRequest (not used by public form; send blanks) ---
        Map<String, Object> sender = new LinkedHashMap<>();
        sender.put("name", nvl(f.getSenderName()));
        sender.put("email", nvl(f.getSenderEmail()));
        sender.put("phone", nvl(f.getSenderPhone()));
        sender.put("address", nvl(f.getSenderAddress()));
        sender.put("organization", nvl(f.getSenderOrganization()));

        Map<String, Object> rlr = new LinkedHashMap<>();
        rlr.put("senderInformation", sender);
        rlr.put("numOfDeviceReturned", nvl(f.getNumOfDeviceReturned()));
        root.put("returnLabelRequest", rlr);

        // --- existingOrderInquiry ---
        Map<String, Object> eoi = new LinkedHashMap<>();
        eoi.put("orderNumbers", nvl(f.getOrderNumbersManualEntry()));
        root.put("existingOrderInquiry", eoi);

        // --- technicalIssue (unused on public form; keep empty-safe fields) ---
        Map<String, Object> ti = new LinkedHashMap<>();
        ti.put("productType", nvl(f.getTechnicalIssueProductType()));

        Map<String, Object> device = new LinkedHashMap<>();
        device.put("ledSequence", nvl(f.getTechnicalIssueDeviceLedSequence()));
        device.put("poweredMin4Hr", nvl(f.getTechnicalIssueDevicePoweredMin4Hr()));
        device.put("firstBeep", nvl(f.getTechnicalIssueDeviceFirstBeep()));
        device.put("secondBeep", nvl(f.getTechnicalIssueDeviceSecondBeep()));
        device.put("serialNumbers", nvl(f.getTechnicalIssueDeviceSerialNumbersManualEntry()));
        ti.put("device", device);

        Map<String, Object> battery = new LinkedHashMap<>();
        battery.put("numOfBatteries", nvl(f.getTechnicalIssueBatteryNumOfBatteries()));
        battery.put("type", nvl(f.getTechnicalIssueBatteryType()));
        battery.put("issueType", nvl(f.getTechnicalIssueBatteryIssueType()));
        ti.put("battery", battery);

        Map<String, Object> cable = new LinkedHashMap<>();
        cable.put("numOfCable", nvl(f.getTechnicalIssueCableNumOfCable()));
        cable.put("type", nvl(f.getTechnicalIssueCableType()));
        cable.put("issueType", nvl(f.getTechnicalIssueCableIssueType()));
        ti.put("cable", cable);

        Map<String, Object> wallPlug = new LinkedHashMap<>();
        wallPlug.put("numberOfWallPlugs", nvl(f.getTechnicalIssueWallPlugNumberOfWallPlugs()));
        wallPlug.put("issueType", nvl(f.getTechnicalIssueWallPlugIssueType()));
        ti.put("wallPlug", wallPlug);

        Map<String, Object> another = new LinkedHashMap<>();
        another.put("issueType", nvl(f.getTechnicalIssueAnotherIssueType()));
        ti.put("another", another);

        root.put("technicalIssue", ti);

        // --- message ---
        root.put("message", nvl(f.getMessage()));

        return root;
    }
}
