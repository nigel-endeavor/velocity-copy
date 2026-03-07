package com.vertek.corporate.qto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 6/12/2024
 */
@Entity
@Table(name = "v_address")
public class AddressView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    /**
     * Type of address. Options are:
     * Location - Address for a location.
     * Billing - Address for a service.
     * Z Location - Address for an Ethernet Service Z location.
     */
    @Column(name = "type")
    private String type;

    @Column(name = "company_id")
    private Long companyId;

    @Override
    @JsonIgnore
    public Long getId() {
        return null;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
