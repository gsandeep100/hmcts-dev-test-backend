package uk.gov.hmcts.reform.dev.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.service.ICaseService;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/cases")
public class CaseController {
    private final ICaseService service;

    @Autowired
    public CaseController(ICaseService service) {
        this.service = service;
    }

    /**
     * @param ca requestBody
     * @return newly created Case
     */
    @PostMapping(value = "/addNewCase")
    public ResponseEntity<CaseDTO> newCase(@RequestBody Case ca) {
        return service.addNewCase(ca);
    }

    /**
     * @param title
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CaseDTO>> getCases(@RequestParam(required = false) String title) {
        return service.getAllCases(title);
    }

    /**
     * @param id unique identifier
     * @return Case Model Object
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<CaseDTO> getCase(@PathVariable("id") long id) {
        return service.getCase(id);
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteCase(@PathVariable("id") long id) {
        return service.deleteCase(id);
    }

    /**
     * @param id
     * @param ca
     * @return
     */
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<CaseDTO> updateCase(@PathVariable("id") long id, @RequestBody Case ca) {
        return service.updateCase(id, ca);
    }
}
