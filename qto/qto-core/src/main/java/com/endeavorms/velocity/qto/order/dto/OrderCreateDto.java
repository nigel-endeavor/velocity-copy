package com.endeavorms.velocity.qto.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rcasey
 * @since 1/11/2023
 */
public class OrderCreateDto implements Serializable {
    @JsonProperty("masterCustomer")
    private OrderCreateCompany masterCustomer;
    @JsonProperty("endCustomer")
    private OrderCreateCompany endCustomer;
    @JsonProperty("clientOrderId")
    private String clientOrderId;
    @JsonProperty("provisioner")
    private Long provisioner;
    @JsonProperty("activationEngineer")
    private Long activationEngineer;
    @JsonProperty("clientProjectManager")
    private String clientProjectManager;
    @JsonProperty("vertekProjectManager")
    private Long vertekProjectManager;
    @JsonProperty("qaManager")
    private Long qaManager;
    @JsonProperty("holdProvisioning")
    private boolean holdProvisioning;
    @JsonProperty("jeopDescription")
    private String jeopDescription;
    @JsonProperty("jeopResponsibility")
    private String jeopResponsibility;

    @JsonProperty("salesContact")
    private OrderCreateContact salesContact;
    @JsonProperty("techContact")
    private OrderCreateContact techContact;
    @JsonProperty("authContact")
    private OrderCreateContact authContact;
    @JsonProperty("billingAddress")
    private OrderCreateAddress billingAddress;

    @JsonProperty("locations")
    private List<OrderCreateLocation> locations = new ArrayList<>();

    public OrderCreateCompany getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(final OrderCreateCompany masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    public OrderCreateCompany getEndCustomer() {
        return endCustomer;
    }

    public void setEndCustomer(final OrderCreateCompany endCustomer) {
        this.endCustomer = endCustomer;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(final String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public List<OrderCreateLocation> getLocations() {
        return locations;
    }

    public void setLocations(final List<OrderCreateLocation> locations) {
        this.locations = locations;
    }

    public Long getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final Long provisioner) {
        this.provisioner = provisioner;
    }

    public Long getActivationEngineer() {
        return activationEngineer;
    }

    public void setActivationEngineer(final Long activationEngineer) {
        this.activationEngineer = activationEngineer;
    }

    public String getClientProjectManager() {
        return clientProjectManager;
    }

    public void setClientProjectManager(final String clientProjectManager) {
        this.clientProjectManager = clientProjectManager;
    }

    public Long getVertekProjectManager() {
        return vertekProjectManager;
    }

    public void setVertekProjectManager(final Long vertekProjectManager) {
        this.vertekProjectManager = vertekProjectManager;
    }

    public Long getQaManager() {
        return qaManager;
    }

    public void setQaManager(final Long qaManager) {
        this.qaManager = qaManager;
    }

    public boolean isHoldProvisioning() {
        return holdProvisioning;
    }

    public void setHoldProvisioning(final boolean holdProvisioning) {
        this.holdProvisioning = holdProvisioning;
    }

    public String getJeopDescription() {
        return jeopDescription;
    }

    public void setJeopDescription(final String jeopDescription) {
        this.jeopDescription = jeopDescription;
    }

    public String getJeopResponsibility() {
        return jeopResponsibility;
    }

    public void setJeopResponsibility(final String jeopResponsibility) {
        this.jeopResponsibility = jeopResponsibility;
    }

    public OrderCreateContact getSalesContact() {
        return salesContact;
    }

    public void setSalesContact(final OrderCreateContact salesContact) {
        this.salesContact = salesContact;
    }

    public OrderCreateContact getTechContact() {
        return techContact;
    }

    public void setTechContact(final OrderCreateContact techContact) {
        this.techContact = techContact;
    }

    public OrderCreateContact getAuthContact() {
        return authContact;
    }

    public void setAuthContact(final OrderCreateContact authContact) {
        this.authContact = authContact;
    }

    public OrderCreateAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(final OrderCreateAddress billingAddress) {
        this.billingAddress = billingAddress;
    }
}

