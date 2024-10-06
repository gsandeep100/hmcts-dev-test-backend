package uk.gov.hmcts.reform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;


@Service
public class CaseServiceImpl implements ICaseService {
    private final CaseRepository repository;

    @Autowired
    public CaseServiceImpl(CaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<CaseDTO> addNewCase(Case ca) {
        try {
            CaseDTO dto = mapToCaseDTO(repository.save(ca));
            if (dto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CaseDTO> getCase(long id) {
        try {
            CaseDTO dto = mapToCaseDTO(repository.findById(id));
            if (dto == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CaseDTO>> getAllCases(String title) {
        try {
            List<CaseDTO> dto = new ArrayList<CaseDTO>();

            if (title == null) {
                dto = repository.findAll().stream().map(this::mapToCaseDTO).collect(Collectors.toList());
            } else {
                dto = repository.findByTitle(title).stream().map(this::mapToCaseDTO).collect(Collectors.toList());
            }
            if (dto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Boolean> deleteCase(long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CaseDTO> updateCase(long id, Case ca) {
        Optional<Case> cas = repository.findById(id);
        if (cas.isPresent()) {
            Case _ca = cas.get();
            _ca.setTitle(ca.getTitle());
            _ca.setCaseNumber(ca.getCaseNumber());
            _ca.setStatus(ca.getStatus());
            _ca.setCreatedDate(ca.getCreatedDate());
            _ca.setDescription(ca.getDescription());
            return new ResponseEntity<>(mapToCaseDTO(repository.save(_ca)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
