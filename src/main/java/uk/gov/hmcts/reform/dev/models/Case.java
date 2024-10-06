package uk.gov.hmcts.reform.dev.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name="Cases")
public class Case implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String caseNumber;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdDate;

    /*public Case(String caseNumber, String caseTitle, String caseDescription, String caseStatus) {
        this.caseNumber = caseNumber;
        this.title = caseTitle;
        this.description = caseDescription;
        this.status = caseStatus;
        this.createdDate = LocalDateTime.now();
    }*/
}
