package com.foxploit.ignio.userinfoservice.resources;

import com.foxploit.ignio.userinfoservice.domain.User;

import javax.persistence.Entity;
import java.util.Optional;

@Entity
public class UserDto extends User {

    private Optional<String> token;

    public UserDto(Optional<User> user, Optional<String> token) {
        super(user.get().getUsername(), user.get().getPassword(), user.get().getEmail(), user.get().getRoles(), user.get().getFirstName(), user.get().getLastName());
        this.token = token;
    }

    public Optional<String> getToken() {
        return token;
    }

    public void setToken(Optional<String> token) {
        this.token = token;
    }

}
