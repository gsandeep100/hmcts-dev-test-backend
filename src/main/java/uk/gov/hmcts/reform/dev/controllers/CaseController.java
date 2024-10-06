package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.dev.service.ICaseService;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CaseController {
    @Autowired
    private ICaseService service;

    /*@GetMapping(value = "/get-example-case", produces = "application/json")
    public ResponseEntity<Case> getExampleCase() {
        return ok(new Case(1, "ABC12345", "Case Title",
                                  "Case Description", "Case Status", LocalDateTime.now()
        ));
    }*/

    /**
     *
     * @param id is unique identifier
     * @param caseNumber is String
     * @param title is String
     * @param description String
     * @param status String
     * @return void
     */
//    @PostMapping
//    public Void newCase(@RequestParam("id") int id,
//                        @RequestParam("caseNumber") String caseNumber,
//                        @RequestParam("id") String title,
//                        @RequestParam("description") String description,
//                        @RequestParam("status") String status) {
//        return service.newCase(new Case(id, caseNumber, title, description, status));
//    }

    /**
     *
     * @param caseid unique identifier
     * @return Case Model Object
     */
//    @GetMapping(value = "/getCase", produces = "application/json")
//    public ResponseEntity<Case> getCase(@RequestParam("caseid") Integer caseid) {
//        return ok(service.getCase(caseid));
//    }

    /**
     *
     * @return al cases List od Case Model class
     */
//    @GetMapping(value = "/getCases", produces = "application/json")
//    public ResponseEntity<List<Case>> getCases() {
//        return ok(service.getCases());
//    }
}
