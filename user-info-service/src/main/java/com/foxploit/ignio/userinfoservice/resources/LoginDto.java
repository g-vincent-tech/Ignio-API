package com.foxploit.ignio.userinfoservice.resources;

import javax.validation.constraints.NotNull;

public class LoginDto {

    @NotNull
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    /**
     * Default constructor
     */
    protected LoginDto() {
    }

    /**
     * Partial constructor
     *
     * @param username
     * @param password
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Full constructor
     *
     * @param username
     * @param password
     */
    public LoginDto(String username, String password, String email, String firstName, String lastName) {
        this(username, password);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
