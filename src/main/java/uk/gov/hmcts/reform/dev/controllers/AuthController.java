package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import uk.gov.hmcts.reform.dev.enums.Role;
import uk.gov.hmcts.reform.dev.models.MyUser;
import uk.gov.hmcts.reform.dev.requests.LoginRequest;
import uk.gov.hmcts.reform.dev.response.AuthResponse;
import uk.gov.hmcts.reform.dev.service.CustomUserDetailsService;
import uk.gov.hmcts.reform.dev.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService myUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //? Register a new user
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUser(@RequestBody MyUser myUser) throws Exception {

        //? check if email exist in the database
        if (myUserService.getMyUserByEmail(myUser.getEmail()) != null) {
            throw new Exception("Email already exists with another account");
        }

        //if not, encode password and save user
        try {
            MyUser createdUser = new MyUser();
            createdUser.setFirstname(myUser.getFirstname());
            createdUser.setLastname(myUser.getLastname());
            createdUser.setEmail(myUser.getEmail());
            createdUser.setRole(myUser.getRole());
            createdUser.setPassword(passwordEncoder.encode(myUser.getPassword()));

            //save the user to db
            MyUser savedUser = myUserService.createUser(createdUser);
            if (savedUser == null) {
                throw new Exception("Error creating user");
            }
            //Set the user as authenticated
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    myUser.getEmail(),
                    myUser.getPassword()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //load user details to generate token if needed
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(myUser.getEmail());

            //create authentication response
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("User created successfully");
            authResponse.setRole(myUser.getRole());

            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception("Error creating user" + e.getMessage());
        }
    }

    //? Login a user
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {

        //? Automatically authenticate the user using the configured UserDetailsService, using authentication manager
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        if (authentication.isAuthenticated()) {
            //create authentication response
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("User logged in successfully");
            //extract role from authorities
            String role = customUserDetailsService.loadUserByUsername(loginRequest.getEmail()).getAuthorities().stream()
                    .findFirst().get().getAuthority().replace("ROLE_", "");
            authResponse.setRole(Role.valueOf(role));

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

    //? Logout a user
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logoutUser() {
        //Clear the security context
        SecurityContextHolder.clearContext();

        //create response
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("User logged out successfully");

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    //? Get all users
    @GetMapping("/users")
    public ResponseEntity<List<MyUser>> getAllUsers() throws Exception {
        return ResponseEntity.ok(myUserService.getUsers());
    }

    //? Get a user by email
    @GetMapping("/user/{email}")
    public ResponseEntity<MyUser> getUserByEmail(@PathVariable String email) throws Exception {
        return ResponseEntity.ok(myUserService.getMyUserByEmail(email));
    }

    //? get user by id
    @GetMapping("/user/{id}")
    public ResponseEntity<MyUser> getUserById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(myUserService.getUserById(id));
    }

    //? update a user
    @PutMapping("/user/{id}")
    public ResponseEntity<MyUser> updateUser(@PathVariable Long id, @RequestBody MyUser myUser) throws Exception {
        //? check if user exist
        if (myUserService.getUserById(id) == null) {
            throw new Exception("User not found");
        }
        //? if user exist, encode password and call the updateUser method
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        return ResponseEntity.ok(myUserService.updateUser(id, myUser));
    }

    //? delete a user
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws Exception {
        myUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
