package com.foxploit.ignio.userinfoservice.resources;

import com.foxploit.ignio.userinfoservice.domain.BillingInfo;
import com.foxploit.ignio.userinfoservice.domain.Contact;
import com.foxploit.ignio.userinfoservice.domain.Role;
import com.foxploit.ignio.userinfoservice.domain.User;
import com.foxploit.ignio.userinfoservice.repository.BillingInfoRepository;
import com.foxploit.ignio.userinfoservice.repository.ContactRepository;
import com.foxploit.ignio.userinfoservice.repository.RoleRepository;
import com.foxploit.ignio.userinfoservice.repository.UserRepository;
import com.foxploit.ignio.userinfoservice.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private static final String CONSUMER_ID_TAG = "CONSMRIGNIOF";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BillingInfoRepository billingInfoRepository;

    @Autowired
    private ContactRepository contactRepository;

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

    public Optional<UserDto> signin(String username, String password) {
        LOGGER.info("New user attempting to sign in");

        Optional<String> token = Optional.empty();
        Optional<User> user = userRepository.findByUsername(username);
        Optional<UserDto> userDto = Optional.empty();

        if (user.isPresent()) {
            try{
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

                token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
                userDto = Optional.of(new UserDto(user, token));

                LOGGER.info(user.get().getRoles().get(0).getRoleName() + " signed in!");
            } catch (AuthenticationException e) {
                LOGGER.info("Log in failed for user {}", username);
            }
        }
        return userDto;
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

    public Optional<UserDto> signup(String username, String password, String email, String firstName, String lastName) {
        LOGGER.info("New user attempting to sign in");

        Optional<String> token = Optional.empty();
        Optional<User> user = Optional.empty();
        Optional<UserDto> userDto = Optional.empty();

        if (userRepository.findByUsername(username).isEmpty()) {

            Optional<Role> role = roleRepository.findByRoleName("ROLE_CONSUMER");
            List<Role> roleList = new ArrayList<>();

            roleList.add(role.get());
            user = Optional.of(userRepository.save(new User(generateConsumerID(), username, passwordEncoder.encode(password), email, roleList, firstName, lastName)));

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
            userDto = Optional.of(new UserDto(user, token));

            LOGGER.info(role.get().getRoleName() + " signed up!");
        }
        return userDto;
    }

    public Optional<User> update(String username, @Valid User updatedUser) {
        LOGGER.info("User attempting to update user details");

        Optional<User> oldUser = userRepository.findByUsername(username);
        Optional<User> user = Optional.empty();

        if (oldUser.isPresent()) {
            try{

                oldUser.get().setFirstName(updatedUser.getFirstName());
                oldUser.get().setLastName(updatedUser.getLastName());
                oldUser.get().setEmail(updatedUser.getEmail());
                oldUser.get().setIgnios(updatedUser.getIgnios());
                oldUser.get().setPlanType(updatedUser.getPlanType());
                oldUser.get().setAddress(updatedUser.getAddress());
                oldUser.get().setPostalCode(updatedUser.getPostalCode());
                oldUser.get().setCountry(updatedUser.getCountry());
                oldUser.get().setBillingInfo(billingInfoRepository.save(updatedUser.getBillingInfo()));

                List<Contact> contactList = updatedUser.getEmergencyContacts();
                contactList.forEach(contact -> contact = contactRepository.save(contact));

                oldUser.get().setEmergencyContacts(contactList);
                user = Optional.of(userRepository.save(oldUser.get()));
                System.out.println(user.get().getUsername());
                LOGGER.info(updatedUser.getUsername() + " details updated!");
            } catch (AuthenticationException e) {
                LOGGER.info("Update failed for user {}", username);
            }
        }
        return user;
    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public String generateConsumerID(){
        int count = Math.toIntExact(userRepository.count());
        String id = CONSUMER_ID_TAG + new String(String.valueOf(count));
        return id;
    }
}