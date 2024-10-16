package uk.gov.hmcts.reform.dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.models.MyUser;
import uk.gov.hmcts.reform.dev.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserServiceImplementation implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<MyUser> getUsers() throws Exception {
        return userRepository.findAll();
    }

    @Override
    public MyUser getUserById(Long id) throws Exception {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public MyUser createUser(MyUser myUser) throws Exception {
        return userRepository.save(myUser);
    }

    @Override
    public MyUser updateUser(Long id, MyUser myUser) throws Exception {
        Optional<MyUser> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            MyUser userToUpdate = existingUser.get();
            userToUpdate.setFirstname(myUser.getFirstname());
            userToUpdate.setLastname(myUser.getLastname());
            userToUpdate.setEmail(myUser.getEmail());
            userToUpdate.setPassword(myUser.getPassword());
            userToUpdate.setRole(myUser.getRole());
            return userRepository.save(userToUpdate);
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public void deleteUser(Long id) throws Exception {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public MyUser getMyUserByEmail(String email) throws Exception {
        MyUser user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        } /*else {
            throw new Exception("User not found");
        }*/
        return user;
    }
}
