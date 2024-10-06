package uk.gov.hmcts.reform.dev.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CaseDTO {
    private long id;
    private String caseNumber;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdDate;
}
