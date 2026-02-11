package com.endeavorms.velocity.qto.service.historyview;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * @author rcasey
 * @since 10/27/2023
 */
@Entity
@Table(name = "v_service_history")
public class ServiceHistoryView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "sub_order_type")
    private String subOrderType;

    @Column(name = "provider_order_num")
    private String providerOrderNum;

    @Column(name = "service_status")
    private String serviceStatus;

    @Column(name = "parent_service_id")
    private Long parentServiceId;

    @Column(name = "created")
    private Date created;

    @Column(name = "complete")
    private Date complete;

    @Column(name = "cancelled")
    private Date cancelled;

    @Column(name = "parent_service_link")
    private String parentServiceLink;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Override
    public Long getId() {
        return id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

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

    public String getProviderOrderNum() {
        return providerOrderNum;
    }

    public void setProviderOrderNum(final String providerOrderNum) {
        this.providerOrderNum = providerOrderNum;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(final String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public Long getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(final Long parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getComplete() {
        return complete;
    }

    public void setComplete(final Date complete) {
        this.complete = complete;
    }

    public Date getCancelled() {
        return cancelled;
    }

    public void setCancelled(final Date cancelled) {
        this.cancelled = cancelled;
    }

    public String getParentServiceLink() {
        return parentServiceLink;
    }

    public void setParentServiceLink(final String parentServiceLink) {
        this.parentServiceLink = parentServiceLink;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }
}
