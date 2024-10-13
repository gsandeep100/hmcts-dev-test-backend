package uk.gov.hmcts.reform.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.hmcts.reform.dev.service.CaseServiceImpl;
import uk.gov.hmcts.reform.dev.service.ICaseService;

@Configuration
public class SpringConfig {

    @Bean
    public ICaseService ICaseService() {
        return new CaseServiceImpl();
    }

}
