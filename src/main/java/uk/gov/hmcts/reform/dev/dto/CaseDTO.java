package uk.gov.hmcts.reform.dev.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CaseDTO {
    private UUID id;
    private String caseNumber;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdDate;
}
