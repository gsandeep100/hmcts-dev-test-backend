package uk.gov.hmcts.reform.dev.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CaseDTO {
    private UUID id;
    @NotEmpty(message = "Case number should not be empty")
    private String caseNumber;
    @NotEmpty(message = "Case title should not be empty")
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdDate;
}
