package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CaseRepository extends JpaRepository<Case, UUID> {
    Optional<Case> findByCaseNumber(String caseNumber);

    Case findCaseById(UUID caseid);
    //Case findById(UUID caseId);
}
