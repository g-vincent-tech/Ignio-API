package com.foxploit.ignio.userinfoservice.domain;

import javax.persistence.Entity;

@Entity
public class BillingInfo {

    private String creditCardNumber;
    private String creditCardType;
    private String cvv2;
    private String expiresOn;
    private String billingAddress;
    private String city;
    private String country;
    private int postalCode;

    public BillingInfo(String creditCardNumber, String creditCardType, String cvv2, String expiresOn, String billingAddress, String city, String country, int postalCode) {
        this.creditCardNumber = creditCardNumber;
        this.creditCardType = creditCardType;
        this.cvv2 = cvv2;
        this.expiresOn = expiresOn;
        this.billingAddress = billingAddress;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
