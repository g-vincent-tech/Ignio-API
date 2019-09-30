package com.foxploit.ignio.gateway.service.dto;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.foxploit.ignio.gateway.domain.BillingInfo} entity.
 */
public class BillingInfoDTO implements Serializable {

    private String id;

    private Set<String> ignios;

    private Long creditCardNumber;

    private String creditCardType;

    private String cvv2;

    private String expiresOn;

    private String billingAddress;

    private String city;

    private String country;

    private Integer postalCode;


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

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
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

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillingInfoDTO billingInfoDTO = (BillingInfoDTO) o;
        if (billingInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billingInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillingInfoDTO{" +
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
