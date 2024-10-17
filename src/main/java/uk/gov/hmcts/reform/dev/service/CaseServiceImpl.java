package uk.gov.hmcts.reform.dev.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class CaseServiceImpl implements ICaseService {
    private static final Logger logger = LogManager.getLogger(CaseServiceImpl.class);

    @Autowired
    private CaseRepository repository;

    public CaseServiceImpl() {
    }

    @Autowired
    public CaseServiceImpl(CaseRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
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
            CaseDTO dto = mapToCaseDTO(repository.findById(id).orElseThrow(() -> new NoSuchElementException(
                "Case not found")));
            return ok(dto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<CaseDTO> getCaseByCaseNumber(String casenumber) {
        try {
            CaseDTO dto = mapToCaseDTO(repository.findByCaseNumber(casenumber));
            if (dto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CaseDTO>> getCaseByDescription(String description) {
        try {
            List<CaseDTO> dto = repository
                .findByDescription(description)
                .stream()
                .map(this::mapToCaseDTO)
                .collect(Collectors.toList());
            if (dto.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<CaseDTO>> getAllCases(String title) {
        try {
            List<CaseDTO> dto = new ArrayList<>();
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
    @Transactional
    public ResponseEntity<Boolean> deleteCase(long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<CaseDTO> updateCase(long id, Case ca) {
        return null;
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
}
