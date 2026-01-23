package com.vertek.corporate.qto.service.dia;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

/** A DIA service. */
@Entity
@Table(name = "dia_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class DiaService extends Service {

    /** Provider activation method. */
    @Column(name = "provider_activation_method")
    private String providerActivationMethod;
    /** Interface connector. */
    @Column(name = "interface_connector")
    private String interfaceConnector;
    /** NPA NXX. */
    @Column(name = "npa_nxx")
    private String npaNxx;
    /** Last mile provider. */
    @Column(name = "last_mile_provider")
    private String lastMileProvider;
    /** New Access Circuit ID. */
    @Column(name = "new_access_circuit_id")
    private String newAccessCircuitId;
    /** Burstable Speed. */
    @Column(name = "burstable_speed")
    private String burstableSpeed;

    /** Burstable Speed Cost. */
    @Column(name = "burstable_speed_cost")
    private BigDecimal burstableSpeedCost;

    @Column(name = "router_serial_number")
    private String routerSerialNumber;

    @Column(name = "router_mac_address")
    private String routerMacAddress;

    @PrePersist
    void prePersist() {
        setType(ServiceType.DIA.getServiceName());
    }

    public String getProviderActivationMethod() {
        return providerActivationMethod;
    }

    public void setProviderActivationMethod(final String providerActivationMethod) {
        this.providerActivationMethod = providerActivationMethod;
    }

    public String getInterfaceConnector() {
        return interfaceConnector;
    }

    public void setInterfaceConnector(final String interfaceConnector) {
        this.interfaceConnector = interfaceConnector;
    }

    public String getNpaNxx() {
        return npaNxx;
    }

    public void setNpaNxx(final String npaNxx) {
        this.npaNxx = npaNxx;
    }

    public String getLastMileProvider() {
        return lastMileProvider;
    }

    public void setLastMileProvider(final String lastMileProvider) {
        this.lastMileProvider = lastMileProvider;
    }

    public String getNewAccessCircuitId() {
        return newAccessCircuitId;
    }

    public void setNewAccessCircuitId(final String newAccessCircuitId) {
        this.newAccessCircuitId = newAccessCircuitId;
    }

    public String getBurstableSpeed() {
        return burstableSpeed;
    }

    public void setBurstableSpeed(final String burstableSpeed) {
        this.burstableSpeed = burstableSpeed;
    }

    public BigDecimal getBurstableSpeedCost() {
        return burstableSpeedCost;
    }

    public void setBurstableSpeedCost(final BigDecimal burstableSpeedCost) {
        this.burstableSpeedCost = burstableSpeedCost;
    }

    public String getRouterSerialNumber() {
        return routerSerialNumber;
    }

    public void setRouterSerialNumber(final String routerSerialNumber) {
        this.routerSerialNumber = routerSerialNumber;
    }

    public String getRouterMacAddress() {
        return routerMacAddress;
    }

    public void setRouterMacAddress(final String routerMacAddress) {
        this.routerMacAddress = routerMacAddress;
    }
}
