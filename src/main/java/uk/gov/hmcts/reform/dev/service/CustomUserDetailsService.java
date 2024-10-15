package uk.gov.hmcts.reform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.components.CustomUserDetails;
import uk.gov.hmcts.reform.dev.models.MyUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService myUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser;
        try {
            myUser = myUserService.getMyUserByEmail(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with Email:" + username, e);
        }
        if (myUser == null) {
            throw new UsernameNotFoundException("User not found with Email:" + username);
        }
        return new CustomUserDetails(myUser);
    }
}
