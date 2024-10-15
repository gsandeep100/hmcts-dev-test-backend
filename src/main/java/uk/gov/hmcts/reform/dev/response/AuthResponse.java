package uk.gov.hmcts.reform.dev.response;

import lombok.Data;
import uk.gov.hmcts.reform.dev.enums.Role;

@Data
public class AuthResponse {
    private String message;
    private Role role;
}
