package uk.gov.hmcts.reform.dev.service;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.List;
import java.util.UUID;

public interface ICaseService {
    public CaseDTO addNewCase(Case ca);
    public CaseDTO getCase(UUID caseid);
    public List<CaseDTO> getAllCases();
    public boolean deleteCase(UUID caseid);
    public boolean updateCase();
}
