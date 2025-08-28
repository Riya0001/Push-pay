package com.tiptappay.store.app.model.support;

import lombok.Data;

import java.io.Serializable;

@Data
public class SupportCaseResponse implements Serializable {
    private boolean success;
    private Integer caseId; // Optional
    private String caseNumber; // Optional
    private String message;
    private String error; // Optional
}
