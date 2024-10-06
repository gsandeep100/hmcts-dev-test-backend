package uk.gov.hmcts.reform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class CaseServiceImpl implements ICaseService {
    private final CaseRepository repository;

    @Autowired
    public CaseServiceImpl(CaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public CaseDTO addNewCase(Case ca) {
        return mapToCaseDTO(repository.save(ca));
        //.stream().map(this::mapToCaseDTO).collect(Collectors.toList());
    }

    @Override
    public CaseDTO getCase(UUID caseid) {
        return mapToCaseDTO(repository.findById(caseid));
    }

    @Override
    public List<CaseDTO> getAllCases() {
        List<Case> cases = repository.findAll();
        return cases.stream().map(this::mapToCaseDTO).collect(Collectors.toList());

    }

    @Override
    public boolean deleteCase(UUID caseid) {
        return false;
    }

    @Override
    public boolean updateCase() {
        return false;
    }

    private CaseDTO mapToCaseDTO(Case ca) {
        return CaseDTO.builder()
            .id(ca.getId())
            .title(ca.getTitle())
            .caseNumber(ca.getCaseNumber())
            .description(ca.getDescription())
            .createdDate(ca.getCreatedDate())
            .status(ca.getStatus())
            .build();
    }

    private CaseDTO mapToCaseDTO(Optional<Case> ca) {
        return CaseDTO.builder()
            .id(ca.orElseThrow().getId())
            .title(ca.orElseThrow().getTitle())
            .caseNumber(ca.orElseThrow().getCaseNumber())
            .description(ca.orElseThrow().getDescription())
            .createdDate(ca.orElseThrow().getCreatedDate())
            .status(ca.orElseThrow().getStatus())
            .build();
    }
}
