package com.tiptappay.store.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampaignDateValidation {
    private boolean dateValid;
    private String daysBetween;
}
