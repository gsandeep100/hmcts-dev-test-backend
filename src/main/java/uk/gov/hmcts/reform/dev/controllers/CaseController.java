package uk.gov.hmcts.reform.dev.controllers;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;
import uk.gov.hmcts.reform.dev.service.ICaseService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Component
public class CaseController {
    private static final Logger logger = LogManager.getLogger(CaseController.class);


    @Autowired
    private ICaseService service;


    /**
     * Creates a new case.
     * @param ca the request body containing case details
     * @return the newly created CaseDTO wrapped in ResponseEntity
     */
    @PostMapping(value = "/cases/addNewCase")
    public ResponseEntity<CaseDTO> newCase(@RequestBody Case ca) {
        logger.info("New case created.");
        return service.addNewCase(ca);
    }

    /**
     * Retrieves a list of cases.
     * @param title an optional filter for case title
     * @return a list of CaseDTO objects wrapped in ResponseEntity
     */
    @GetMapping(value = "/cases")
    public ResponseEntity<List<CaseDTO>> getCases(@RequestParam(required = false) String title) {
        return service.getAllCases(title);
    }

    /**
     * Retrieves a case by its unique identifier.
     * @param id the unique identifier of the case
     * @return the CaseDTO object wrapped in ResponseEntity
     */
    @GetMapping(value = "/cases/{id}")
    public ResponseEntity<CaseDTO> getCaseById(@PathVariable("id") long id) {
        return service.getCase(id);
    }

    /**
     * Retrieves a case by its case number.
     * @param casenumber the unique case number
     * @return the CaseDTO object wrapped in ResponseEntity
     */
    @GetMapping(value = "/cases/{casenumber}")
    public ResponseEntity<CaseDTO> getCaseByCaseNumber(@PathVariable("casenumber") String casenumber) {
        return service.getCaseByCaseNumber(casenumber);
    }

    /**
     * Retrieves a list of cases by their description.
     * @param description the case description
     * @return a list of CaseDTO objects wrapped in ResponseEntity
     */
    @GetMapping(value = "/cases/{description}")
    public ResponseEntity<List<CaseDTO>> getCaseByDescription(@PathVariable("description") String description) {
        return service.getCaseByDescription(description);
    }

    /**
     * Deletes a case by its unique identifier.
     * @param id the unique identifier of the case to be deleted
     * @return a boolean indicating success or failure wrapped in ResponseEntity
     */
    @DeleteMapping(value = "/cases/{id}")
    public ResponseEntity<Boolean> deleteCase(@PathVariable("id") long id) {
        return service.deleteCase(id);
    }

    /**
     * Updates an existing case.
     * @param id the unique identifier of the case to be updated
     * @param ca the request body containing updated case details
     * @return the updated CaseDTO object wrapped in ResponseEntity
     */
    @PutMapping(value = "/cases/{id}")
    @Transactional
    public ResponseEntity<CaseDTO> updateCase(@PathVariable("id") long id, @RequestBody Case ca) {
        return service.updateCase(id, ca);
    }
}
