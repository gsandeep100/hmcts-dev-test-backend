package uk.gov.hmcts.reform.dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.gov.hmcts.reform.dev.models.Case;
import uk.gov.hmcts.reform.dev.repository.CaseRepository;
import uk.gov.hmcts.reform.dev.service.CaseServiceImpl;
import uk.gov.hmcts.reform.dev.service.ICaseService;

@Configuration
public class SpringConfig {


    @Bean
    public ICaseService Service() {
        return new CaseServiceImpl();
    }

}
