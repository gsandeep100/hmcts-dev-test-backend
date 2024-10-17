package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.dev.dto.CaseDTO;
import uk.gov.hmcts.reform.dev.models.Case;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;
import uk.gov.hmcts.reform.dev.service.CaseServiceImpl;
import uk.gov.hmcts.reform.dev.service.ICaseService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CaseServiceUTest {

    @Mock
    private CaseRepository mockedCaseRepository;

    private ICaseService caseService;

    @BeforeEach
    void setup() {
        caseService = new CaseServiceImpl(mockedCaseRepository);
    }

    @Test
    void shouldAddNewCase() {

        // Create a Case object
        Case caseObj = Case.builder()
            .id(123L)
            .caseNumber("1234")
            .title("Case Title")
            .description("Description of the case")
            .status("Open")
            .createdDate(LocalDateTime.now())
            .build();

        Mockito.when(mockedCaseRepository.save(caseObj)).thenReturn(caseObj);

        CaseDTO newCaseDTO = caseService.addNewCase(caseObj).getBody();

        verify(mockedCaseRepository, times(1)).save(caseObj);

        assertEquals(caseObj.getId(), newCaseDTO.getId());
        assertEquals(caseObj.getCaseNumber(), newCaseDTO.getCaseNumber());
        assertEquals(caseObj.getTitle(), newCaseDTO.getTitle());
        assertEquals(caseObj.getDescription(), newCaseDTO.getDescription());
        assertEquals(caseObj.getStatus(), newCaseDTO.getStatus());
        assertEquals(caseObj.getCreatedDate(), newCaseDTO.getCreatedDate());
    }

    @Test
    void shouldGetCaseById() {
        long caseId = 123L;
        Case caseObj = Case.builder()
            .id(123L)
            .caseNumber("1234")
            .title("Case Title")
            .description("Description of the case")
            .status("Open")
            .createdDate(LocalDateTime.now())
            .build();

        Mockito.when(mockedCaseRepository.findById(caseId)).thenReturn(java.util.Optional.of(caseObj));

        CaseDTO retrievedCaseDTO = caseService.getCase(caseId).getBody();

        verify(mockedCaseRepository, times(1)).findById(caseId);

        assertEquals(caseObj.getId(), retrievedCaseDTO.getId());
        assertEquals(caseObj.getCaseNumber(), retrievedCaseDTO.getCaseNumber());
        assertEquals(caseObj.getTitle(), retrievedCaseDTO.getTitle());
        assertEquals(caseObj.getDescription(), retrievedCaseDTO.getDescription());
        assertEquals(caseObj.getStatus(), retrievedCaseDTO.getStatus());
        assertEquals(caseObj.getCreatedDate(), retrievedCaseDTO.getCreatedDate());
    }


    @Test
    void shouldGetAllCasesByTitle() {
        String title = "Case Title";
        Case caseObj1 = Case.builder()
            .id(123L)
            .caseNumber("1234")
            .title(title)
            .description("Description 1")
            .status("Open")
            .createdDate(LocalDateTime.now())
            .build();

        Case caseObj2 = Case.builder()
            .id(123L)
            .caseNumber("5678")
            .title(title)
            .description("Description 2")
            .status("Closed")
            .createdDate(LocalDateTime.now())
            .build();

        List<Case> cases = Arrays.asList(caseObj1, caseObj2);
        Mockito.when(mockedCaseRepository.findByTitle(title)).thenReturn(cases);

        ResponseEntity<List<CaseDTO>> responseEntity = caseService.getAllCases(title);

        verify(mockedCaseRepository, times(1)).findByTitle(title);
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void shouldGetCaseByDescription() {
        String description = "Description of the case";
        Case caseObj = Case.builder()
            .id(123L)
            .caseNumber("1234")
            .title("Case Title")
            .description(description)
            .status("Open")
            .createdDate(LocalDateTime.now())
            .build();

        List<Case> cases = Collections.singletonList(caseObj);
        Mockito.when(mockedCaseRepository.findByDescription(description)).thenReturn(cases);

        ResponseEntity<List<CaseDTO>> responseEntity = caseService.getCaseByDescription(description);

        verify(mockedCaseRepository, times(1)).findByDescription(description);

        assertEquals(1, responseEntity.getBody().size());

        CaseDTO returnedCaseDTO = responseEntity.getBody().get(0);
        assertEquals(caseObj.getId(), returnedCaseDTO.getId());
        assertEquals(caseObj.getCaseNumber(), returnedCaseDTO.getCaseNumber());
        assertEquals(caseObj.getTitle(), returnedCaseDTO.getTitle());
        assertEquals(caseObj.getDescription(), returnedCaseDTO.getDescription());
        assertEquals(caseObj.getStatus(), returnedCaseDTO.getStatus());
        assertEquals(caseObj.getCreatedDate(), returnedCaseDTO.getCreatedDate());
    }

    @Test
    void shouldGetCaseByCaseNumber() {
        String caseNumber = "1234";
        Case caseObj = Case.builder()
            .id(123L)
            .caseNumber(caseNumber)
            .title("Case Title")
            .description("Description of the case")
            .status("Open")
            .createdDate(LocalDateTime.now())
            .build();

        Mockito.when(mockedCaseRepository.findByCaseNumber(caseNumber)).thenReturn(caseObj);

        ResponseEntity<CaseDTO> responseEntity = caseService.getCaseByCaseNumber(caseNumber);

        verify(mockedCaseRepository, times(1)).findByCaseNumber(caseNumber);

        assertNotNull(responseEntity.getBody());

        CaseDTO returnedCaseDTO = responseEntity.getBody();
        assertEquals(caseObj.getId(), returnedCaseDTO.getId());
        assertEquals(caseObj.getCaseNumber(), returnedCaseDTO.getCaseNumber());
        assertEquals(caseObj.getTitle(), returnedCaseDTO.getTitle());
        assertEquals(caseObj.getDescription(), returnedCaseDTO.getDescription());
        assertEquals(caseObj.getStatus(), returnedCaseDTO.getStatus());
        assertEquals(caseObj.getCreatedDate(), returnedCaseDTO.getCreatedDate());
    }


    /*@Test
    void shouldUpdateCase() {
        long caseId = 123L;
        Case existingCase = Case.builder()
            .id(123L)
            .caseNumber("1234")
            .title("Old Title")
            .description("Old Description")
            .status("Open")
            .createdDate(LocalDateTime.now())
            .build();

        Case updatedCase = Case.builder()
            .id(123L)
            .caseNumber("1234")
            .title("Updated Title")
            .description("Updated Description")
            .status("Closed")
            .createdDate(LocalDateTime.now())
            .build();

        Mockito.when(mockedCaseRepository.findById(caseId)).thenReturn(Optional.of(existingCase));
        Mockito.when(mockedCaseRepository.save(updatedCase)).thenReturn(updatedCase);

        ResponseEntity<CaseDTO> responseEntity = caseService.updateCase(caseId, updatedCase);

        verify(mockedCaseRepository, times(1)).findById(caseId);
        verify(mockedCaseRepository, times(1)).save(updatedCase);

        assertEquals(200, responseEntity.getStatusCodeValue());

        assertNotNull(responseEntity.getBody());

        CaseDTO returnedCaseDTO = responseEntity.getBody();
        assertEquals(updatedCase.getId(), returnedCaseDTO.getId());
        assertEquals(updatedCase.getCaseNumber(), returnedCaseDTO.getCaseNumber());
        assertEquals(updatedCase.getTitle(), returnedCaseDTO.getTitle());
        assertEquals(updatedCase.getDescription(), returnedCaseDTO.getDescription());
        assertEquals(updatedCase.getStatus(), returnedCaseDTO.getStatus());
        assertEquals(updatedCase.getCreatedDate(), returnedCaseDTO.getCreatedDate());
    }
*/



}
