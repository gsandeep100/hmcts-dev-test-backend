package uk.gov.hmcts.reform.dev.service;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.List;
import java.util.UUID;

public interface ICaseService {
    CaseDTO addNewCase(Case ca);
    CaseDTO getCase(UUID caseid);
    List<CaseDTO> getAllCases();
    boolean deleteCase(UUID caseid);
    boolean updateCase();
}
