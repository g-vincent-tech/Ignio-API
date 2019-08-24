package com.foxploit.ignio.userinfoservice.resources;

import com.foxploit.ignio.userinfoservice.domain.Role;
import com.foxploit.ignio.userinfoservice.domain.User;
import com.foxploit.ignio.userinfoservice.repository.RoleRepository;
import com.foxploit.ignio.userinfoservice.repository.UserRepository;
import com.foxploit.ignio.userinfoservice.security.JwtProvider;
import com.foxploit.ignio.userinfoservice.security.WebSecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    /**
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @param username username
     * @param password password
     * @return Optional of the Java Web Token, empty otherwise
     */

    public Optional<String> signin(String username, String password) {
        LOGGER.info("New user attempting to sign in");
        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            System.out.println(user.get().getRoles().get(0).getRoleName());
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            } catch (AuthenticationException e) {
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        return token;
    }

    /**
     * Create a new user in the database.
     *
     * @param username  username
     * @param password  password
     * @param firstName first name
     * @param lastName  last name
     * @return Optional of user, empty if the user already exists.
     */

    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        LOGGER.info("New user attempting to sign in");
        Optional<User> user = Optional.empty();
        if (!userRepository.findByUsername(username).isPresent()) {
            Optional<Role> role = roleRepository.findByRoleName("ROLE_CSR");
//            user = Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password), role.get(), firstName, lastName)));
            Role role1 = new Role();
            role1.setRoleName("ROLE_ADMIN");
            // 1, 'ROLE_ADMIN', 'Administrator'

            role1.setId("1");
            role1.setDescription("Administrator");
            user = Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password), role1, firstName, lastName)));
        }
        return user;
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }
}