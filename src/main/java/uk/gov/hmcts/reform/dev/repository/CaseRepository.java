package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.Case;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {
    Case findByCaseNumber(String caseNumber);

    List<Case> findByTitle(String title);

    List<Case> findByDescription(String description);
}
