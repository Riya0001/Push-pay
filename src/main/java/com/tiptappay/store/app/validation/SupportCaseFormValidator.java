package com.tiptappay.store.app.validation;

import com.tiptappay.store.app.model.support.SupportCaseForm;
import com.tiptappay.store.app.model.support.ValidationError;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.tiptappay.web.constant.TTPConstant.TicketNames.*;

@Slf4j
public class SupportCaseFormValidator implements ConstraintValidator<ValidSupportCaseForm, SupportCaseForm> {

    @Override
    public void initialize(ValidSupportCaseForm constraintAnnotation) {
        // no-op
    }

    @Override
    public boolean isValid(SupportCaseForm form, ConstraintValidatorContext context) {
        // Let other annotations handle @NotNull on the bean itself
        if (form == null) {
            return true;
        }

        List<ValidationError> errors = new ArrayList<>();

        final String ticketName = form.getTicketName();
        log.debug("Validating SupportCaseForm, ticketName={}", ticketName);

        if (ticketName != null) {
            switch (ticketName) {
                case EXISTING_ORDER_INQUIRY -> orderNumbersRequiredCase(form, errors);
                case RETURN_LABEL_REQUEST     -> returnLabelCase(form, errors);
                case TECHNICAL_ISSUE_CABLE    -> technicalIssueCableCase(form, errors);
                case TECHNICAL_ISSUE_DEVICE   -> technicalIssueDeviceCase(form, errors);
                case TECHNICAL_ISSUE_BATTERY  -> technicalIssueBatteryCase(form, errors);
                case TECHNICAL_ISSUE_WALL_PLUG-> technicalIssueWallPlugCase(form, errors);
                case TECHNICAL_ISSUE_OTHER    -> technicalIssueOtherCase(form, errors);
                default -> { /* no extra rules */ }
            }
        }

        if (!errors.isEmpty()) {
            log.info("SupportCaseForm validation errors: {}", errors);
            context.disableDefaultConstraintViolation();
            for (ValidationError err : errors) {
                context.buildConstraintViolationWithTemplate(err.getMessage())
                        .addPropertyNode(err.getId())
                        .addConstraintViolation();
            }
            return false;
        }

        return true;
    }

    /* ---------- Case-specific rules ---------- */

    private void orderNumbersRequiredCase(SupportCaseForm form, List<ValidationError> errors) {
        if (form.isManualOrderNumbersEntryEnabled()) {
            require(form.getOrderNumbersManualEntry(),
                    "orderNumbersManualEntry",
                    "SO Number(s) is required",
                    errors);
        } else {
            if (form.getSelectedOrders() == null || form.getSelectedOrders().isEmpty()) {
                errors.add(new ValidationError("selectedOrders", "SO Number(s) is required"));
            }
        }
    }

    private void returnLabelCase(SupportCaseForm form, List<ValidationError> errors) {
        orderNumbersRequiredCase(form, errors);

        require(form.getSenderName(),        "senderName",        "Sender Name is required", errors);
        require(form.getSenderEmail(),       "senderEmail",       "Sender Email is required", errors);
        require(form.getSenderPhone(),       "senderPhone",       "Sender Phone is required", errors);
        require(form.getSenderOrganization(),"senderOrganization","Sender Organization is required", errors);
        require(form.getSenderAddress(),     "senderAddress",     "Sender Address is required", errors);

        // ✅ fix: validate the correct field id for number of devices
        require(form.getNumOfDeviceReturned(),"numOfDeviceReturned","Number of devices is required", errors);
    }

    private void technicalIssueCableCase(SupportCaseForm form, List<ValidationError> errors) {
        orderNumbersRequiredCase(form, errors);

        require(form.getTechnicalIssueCableNumOfCable(),  "technicalIssueCableNumOfCable",  "Cable Number is required", errors);
        require(form.getTechnicalIssueCableType(),        "technicalIssueCableType",        "Cable Type is required", errors);
        require(form.getTechnicalIssueCableIssueType(),   "technicalIssueCableIssueType",   "Cable Issue Type is required", errors);

        log.debug("Cable issue validation errors: {}", errors);
    }

    private void technicalIssueDeviceCase(SupportCaseForm form, List<ValidationError> errors) {
        orderNumbersRequiredCase(form, errors);

        require(form.getTechnicalIssueDeviceLedSequence(), "technicalIssueDeviceLedSequence", "LED Sequence is required", errors);

        if (form.isManualDeviceEntryEnabled()) {
            log.debug("Manual device entry enabled");
            require(form.getTechnicalIssueDeviceSerialNumbersManualEntry(),
                    "technicalIssueDeviceSerialNumbersManualEntry",
                    "Device Serial Number(s) is required", errors);
        } else {
            log.debug("Manual device entry disabled");
            if (form.getSelectedDevices() == null || form.getSelectedDevices().isEmpty()) {
                errors.add(new ValidationError("selectedDevices", "Device Serial Number(s) is required"));
            }
        }

        require(form.getTechnicalIssueDevicePoweredMin4Hr(), "technicalIssueDevicePoweredMin4Hr", "Please select one option", errors);
        require(form.getTechnicalIssueDeviceFirstBeep(),     "technicalIssueDeviceFirstBeep",     "Please select one option", errors);
        require(form.getTechnicalIssueDeviceSecondBeep(),    "technicalIssueDeviceSecondBeep",    "Please select one option", errors);
    }

    private void technicalIssueBatteryCase(SupportCaseForm form, List<ValidationError> errors) {
        orderNumbersRequiredCase(form, errors);

        require(form.getTechnicalIssueBatteryNumOfBatteries(), "technicalIssueBatteryNumOfBatteries", "Battery Number is required", errors);
        require(form.getTechnicalIssueBatteryType(),           "technicalIssueBatteryType",           "Battery Type is required", errors);
        require(form.getTechnicalIssueBatteryIssueType(),      "technicalIssueBatteryIssueType",      "Battery Issue Type is required", errors);
    }

    private void technicalIssueWallPlugCase(SupportCaseForm form, List<ValidationError> errors) {
        orderNumbersRequiredCase(form, errors);

        require(form.getTechnicalIssueWallPlugNumberOfWallPlugs(), "technicalIssueWallPlugNumberOfWallPlugs", "Wall Plug Number is required", errors);
        require(form.getTechnicalIssueWallPlugIssueType(),         "technicalIssueWallPlugIssueType",         "Wall Plug Issue Type is required", errors);
    }

    private void technicalIssueOtherCase(SupportCaseForm form, List<ValidationError> errors) {
        orderNumbersRequiredCase(form, errors);

        require(form.getTechnicalIssueAnotherIssueType(), "technicalIssueAnotherIssueType", "This is required", errors);
    }

    /* ---------- Helpers ---------- */

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private static void require(String value, String fieldId, String message, List<ValidationError> errors) {
        if (isBlank(value)) {
            errors.add(new ValidationError(fieldId, message));
        }
    }
}
