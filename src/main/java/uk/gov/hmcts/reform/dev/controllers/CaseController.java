package uk.gov.hmcts.reform.dev.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;
import uk.gov.hmcts.reform.dev.service.ICaseService;
import uk.gov.hmcts.reform.dev.models.Case;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Component
//@RequestMapping("/cases")
//@ComponentScan("uk.gov.hmcts.reform.dev.*")

public class CaseController {
    private static final Logger logger = LogManager.getLogger(CaseController.class);

    private final ICaseService service;

    @Autowired
    public CaseController(@Qualifier("Service") ICaseService service) {
        this.service = service;
    }

    /**
     * @param ca requestBody
     * @return newly created Case
     */
    @PostMapping(value = "/cases/addNewCase")
    public ResponseEntity<CaseDTO> newCase(@RequestBody Case ca) {
        logger.info("new case created ");
        return service.addNewCase(ca);
    }

    /**
     * @param title
     * @return
     */
    @GetMapping(value = "/cases")
    public ResponseEntity<List<CaseDTO>> getCases(@RequestParam(required = false) String title) {
        return service.getAllCases(title);
    }

    /**
     * @param id unique identifier
     * @return Case Model Object
     */
    @GetMapping(value = "/cases/{id}")
    public ResponseEntity<CaseDTO> getCaseById(@PathVariable("id") long id) {
        return service.getCase(id);
    }

    /**
     * @param casenumber
     * @return
     */
    @GetMapping(value = "/cases/{casenumber}")
    public ResponseEntity<CaseDTO> getCaseByCaseNumber(@PathVariable("casenumber") String casenumber) {
        return service.getCaseByCaseNumber(casenumber);
    }

    @GetMapping(value = "/cases/{description}")
    public ResponseEntity<List<CaseDTO>> getCaseByDescription(@PathVariable("description") String description) {
        return service.getCaseByDescription(description);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping(value = "/cases/{id}")
    public ResponseEntity<Boolean> deleteCase(@PathVariable("id") long id) {
        return service.deleteCase(id);
    }

    /**
     * @param id
     * @param ca
     * @return
     */
    @PutMapping(value = "/cases/{id}")
    @Transactional
    public ResponseEntity<CaseDTO> updateCase(@PathVariable("id") long id, @RequestBody Case ca) {
        return service.updateCase(id, ca);
    }
}
