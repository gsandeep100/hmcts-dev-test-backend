package uk.gov.hmcts.reform.dev.service;

import uk.gov.hmcts.reform.dev.models.MyUser;

import java.util.List;

public interface IUserService {

    public List<MyUser> getUsers() throws Exception;

    public MyUser getUserById(Long id) throws Exception;

    public MyUser createUser(MyUser myUser) throws Exception;

    public MyUser updateUser(Long id, MyUser myUser) throws Exception;

    public void deleteUser(Long id) throws Exception;

    public MyUser getMyUserByEmail(String email) throws Exception;
}
