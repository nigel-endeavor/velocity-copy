package com.vertek.corporate.qto.service.macd.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO of a single MACD type.
 * @since 1.3.0
 */
public class MacdDto implements Serializable {
    @JsonProperty
    private String orderType;
    @JsonProperty
    private String subOrderType;
    @JsonProperty
    private boolean createDisconnectUponCompletion;
    @JsonProperty
    private String disconnectReason;
    @JsonProperty
    private String serviceType;
    @JsonProperty
    private String projectName;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public String getSubOrderType() {
        return subOrderType;
    }

    public void setSubOrderType(final String subOrderType) {
        this.subOrderType = subOrderType;
    }

    public boolean isCreateDisconnectUponCompletion() {
        return createDisconnectUponCompletion;
    }

    public void setCreateDisconnectUponCompletion(final boolean createDisconnectUponCompletion) {
        this.createDisconnectUponCompletion = createDisconnectUponCompletion;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }
}
