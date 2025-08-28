package com.tiptappay.store.app.service;

import com.tiptappay.store.app.model.Stepper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StepperService {

    public Stepper getStepperByScreen(int screen) {
        return switch (screen) {
            case 1 -> new Stepper("active", "inactive", "inactive", "inactive", "inactive");
            case 2 -> new Stepper("completed", "active", "active", "inactive", "inactive");
            case 3 -> new Stepper("completed", "active", "completed", "active", "inactive");
            case 4, 5 -> new Stepper("completed", "active", "completed", "active", "active");
            case 6 -> new Stepper("completed", "active", "completed", "active", "completed");
            case 7 -> new Stepper("completed", "active", "completed", "active", "error");
            default -> null;
        };
    }
}
