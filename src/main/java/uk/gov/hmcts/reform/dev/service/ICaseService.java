package uk.gov.hmcts.reform.dev.service;

import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.List;

public interface ICaseService {
    ResponseEntity<CaseDTO> addNewCase(Case ca);

    ResponseEntity<CaseDTO> getCase(long id);

    ResponseEntity<List<CaseDTO>> getAllCases(String title);

    ResponseEntity<Boolean> deleteCase(long id);

    ResponseEntity<CaseDTO> updateCase(long id, Case ca);
}
