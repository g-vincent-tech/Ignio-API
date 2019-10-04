package com.foxploit.ignio.gateway.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

/**
 * A BillingInfo.
 */
@Document(collection = "billing_info")
public class BillingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("ignios")
    private Set<String> ignios;

    @Field("credit_card_number")
    private Long creditCardNumber;

    @Field("credit_card_type")
    private String creditCardType;

    @Field("cvv_2")
    private String cvv2;

    @Field("expires_on")
    private String expiresOn;

    @Field("billing_address")
    private String billingAddress;

    @Field("city")
    private String city;

    @Field("country")
    private String country;

    @Field("postal_code")
    private Integer postalCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<String> getIgnios() {
        return ignios;
    }

    public void setIgnios(Set<String> ignios) {
        this.ignios = ignios;
    }

    public BillingInfo ignios(Set<String> ignios) {
        this.ignios = ignios;
        return this;
    }

    public BillingInfo addIgnio(String ignio){
        this.ignios.add(ignio);
        return this;
    }

    public BillingInfo removeIgnio(String ignio){
        this.ignios.remove(ignio);
        return this;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public BillingInfo creditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        return this;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public BillingInfo creditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
        return this;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCvv2() {
        return cvv2;
    }

    public BillingInfo cvv2(String cvv2) {
        this.cvv2 = cvv2;
        return this;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public BillingInfo expiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public void setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public BillingInfo billingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCity() {
        return city;
    }

    public BillingInfo city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public BillingInfo country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public BillingInfo postalCode(Integer postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingInfo)) {
            return false;
        }
        return id != null && id.equals(((BillingInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BillingInfo{" +
            "id=" + getId() +
            ", ignios='" + getIgnios() + "'" +
            ", creditCardNumber=" + getCreditCardNumber() +
            ", creditCardType='" + getCreditCardType() + "'" +
            ", cvv2='" + getCvv2() + "'" +
            ", expiresOn='" + getExpiresOn() + "'" +
            ", billingAddress='" + getBillingAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode=" + getPostalCode() +
            "}";
    }
}
