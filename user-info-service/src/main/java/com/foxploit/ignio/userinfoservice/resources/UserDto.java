package com.foxploit.ignio.userinfoservice.resources;

import com.foxploit.ignio.userinfoservice.domain.User;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class UserDto extends User {

    private Optional<String> token;

    /**
     * Constructor for sign in / sign up
     * @param user
     * @param token
     */
    public UserDto(Optional<User> user, Optional<String> token) {
        super(user.get().getId(), user.get().getUsername(), user.get().getPassword(), user.get().getEmail(), user.get().getRoles(), user.get().getFirstName(), user.get().getLastName());
        this.setIgnios(user.get().getIgnios());
        this.setPlanType(user.get().getPlanType());
        this.setAddress(user.get().getAddress());
        this.setPostalCode(user.get().getPostalCode());
        this.setCountry(user.get().getCountry());
        this.setBillingInfo(user.get().getBillingInfo());
        this.setEmergencyContacts(user.get().getEmergencyContacts());
        this.token = token;
    }

    public Optional<String> getToken() {
        return token;
    }

    public void setToken(Optional<String> token) {
        this.token = token;
    }

}
