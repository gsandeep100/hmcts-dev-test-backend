package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.service.ICaseService;
import uk.gov.hmcts.reform.dev.models.Case;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CaseController {
    private final ICaseService service;

    @Autowired
    public CaseController(ICaseService service) {
        this.service = service;
    }

    /*@GetMapping(value = "/get-example-case", produces = "application/json")
    public ResponseEntity<Case> getExampleCase() {
        return ok(new Case(1, "ABC12345", "Case Title",
                                  "Case Description", "Case Status", LocalDateTime.now()
        ));
    }*/

    /**
     *
     * @param ca requestBody
     * @return newly created Case
     */
    @PostMapping (value = "/cases/addNewCase")
    public CaseDTO newCase(@RequestBody Case ca) {
        //return service.addNewCase(new Case("ABC12345", "Case Title","Case Description", "Case Status"));
        return service.addNewCase(ca);
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/cases", produces = "application/json")
    public ResponseEntity<List<CaseDTO>> getCases(Model model) {
        List<CaseDTO> dto = service.getAllCases();
        model.addAttribute("cases", dto);
        return ok(dto);
    }

    /**
     *
     * @param caseid unique identifier
     * @return Case Model Object
     */
    @GetMapping(value = "/cases/{id}", produces = "application/json")
    public ResponseEntity<CaseDTO> getCase(@RequestParam("caseid") UUID caseid) {
        return ok(service.getCase(caseid));
    }

    /**
     *
     * @param caseid
     * @return
     */
    @PostMapping(value = "/cases/{id}", produces = "application/json")
    public ResponseEntity<Boolean> deleteCase(@RequestParam("caseid") UUID caseid) {
        return ok(service.deleteCase(caseid));
    }
}
