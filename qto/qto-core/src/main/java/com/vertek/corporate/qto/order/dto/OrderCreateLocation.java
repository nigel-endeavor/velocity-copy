package com.vertek.corporate.qto.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderCreateLocation implements Serializable {

    @JsonProperty("openLocationId")
    private Long openLocationId;
    @JsonProperty("clientLocationId")
    private String clientLocationId;
    @JsonProperty("clientOrderId")
    private String clientOrderId;
    @JsonProperty("address")
    private OrderCreateAddress address;
    @JsonProperty("lconName")
    private String lconName;
    @JsonProperty("lconPhone")
    private String lconPhone;
    @JsonProperty("lconEmail")
    private String lconEmail;
    @JsonProperty("locationInfo")
    private String locationInfo;
    @JsonProperty("locationType")
    private String locationType;
    @JsonProperty("levelOfEffort")
    private String levelOfEffort;
    @JsonProperty("recordSource")
    private String recordSource;

    @JsonProperty("services")
    private List<OrderCreateService> services = new ArrayList<>();

    public Long getOpenLocationId() {
        return openLocationId;
    }

    public void setOpenLocationId(final Long openLocationId) {
        this.openLocationId = openLocationId;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(final String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public OrderCreateAddress getAddress() {
        return address;
    }

    public void setAddress(final OrderCreateAddress address) {
        this.address = address;
    }

    public String getLconName() {
        return lconName;
    }

    public void setLconName(final String lconName) {
        this.lconName = lconName;
    }

    public String getLconPhone() {
        return lconPhone;
    }

    public void setLconPhone(final String lconPhone) {
        this.lconPhone = lconPhone;
    }

    public String getLconEmail() {
        return lconEmail;
    }

    public void setLconEmail(final String lconEmail) {
        this.lconEmail = lconEmail;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(final String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(final String locationType) {
        this.locationType = locationType;
    }

    public String getLevelOfEffort() {
        return levelOfEffort;
    }

    public void setLevelOfEffort(final String levelOfEffort) {
        this.levelOfEffort = levelOfEffort;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(final String recordSource) {
        this.recordSource = recordSource;
    }

    public List<OrderCreateService> getServices() {
        return services;
    }

    public void setServices(final List<OrderCreateService> services) {
        this.services = services;
    }
}
