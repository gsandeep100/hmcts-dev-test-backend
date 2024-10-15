package uk.gov.hmcts.reform.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.dev.models.MyUser;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByEmail(String email);
}
