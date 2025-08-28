package com.tiptappay.store.app.controller.benevity.store;

import com.tiptappay.store.app.dto.benevity.store.BenevityCause;
import com.tiptappay.store.app.service.benevity.BenevityStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/benevity")
@RequiredArgsConstructor
public class BenevityApiController {
    private final BenevityStoreService benevityStoreService;

    @GetMapping("/cause")
    public BenevityCause getCausesById(@RequestParam("id") String causeId) {
        return benevityStoreService.getBenevityCause(causeId);
    }

    @GetMapping("/causes/alternative/search")
    public List<BenevityCause> searchCausesByTerm(@RequestParam("search_term") String searchTerm) {
        return benevityStoreService.searchBenevityCausesToList(searchTerm);
    }

    @GetMapping("/causes/search")
    public String searchCausesByTerm2(@RequestParam("search_term") String searchTerm) {
        return benevityStoreService.searchBenevityCauses(searchTerm);
    }
}