package com.foxploit.ignio.userinfoservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;


@Entity
public class User {
    @Id
    private String id;
    private String username;

    @JsonIgnore
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private List<String> ignios;
    private String planType;
    private String address;
    private String postalCode;
    private String country;
    private BillingInfo billingInfo;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Contact> emergencyContacts;


    /**
     * Default Constructor.
     */
    protected User() {
    }

    /**
     * Constructor for Sign in
     * @param username
     * @param password
     * @param email
     * @param role
     * @param firstName
     * @param lastName
     */
    public User(String username, String password, String email, List<Role> role, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getIgnios() {
        return ignios;
    }

    public void setIgnios(List<String> ignios) {
        this.ignios = ignios;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BillingInfo getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }

    public List<Contact> getEmergencyContacts() {
        return emergencyContacts;
    }

    public void setEmergencyContacts(List<Contact> emergencyContacts) {
        this.emergencyContacts = emergencyContacts;
    }
}
