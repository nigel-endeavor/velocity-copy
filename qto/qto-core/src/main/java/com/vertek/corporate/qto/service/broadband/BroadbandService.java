package com.vertek.corporate.qto.service.broadband;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Entity
@Table(name = "broadband_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class BroadbandService extends Service {

    @Column(name = "modem_make")
    private String modemMake;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "modem_serial_number")
    private String modemSerialNumber;

    @Column(name = "customer_prem_equipment")
    private String customerPremEquipment;

    @Column(name = "network_protocol")
    private String networkProtocol;

    @Column(name = "pppoe_username")
    private String pppoeUsername;

    @Column(name = "pppoe_password")
    private String pppoePassword;

    @PrePersist
    void prePersist() {
        setType(ServiceType.BROADBAND.getServiceName());
    }

    public String getModemMake() {
        return modemMake;
    }

    public void setModemMake(final String modemMake) {
        this.modemMake = modemMake;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(final String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCustomerPremEquipment() {
        return customerPremEquipment;
    }

    public void setCustomerPremEquipment(final String customerPremEquipment) {
        this.customerPremEquipment = customerPremEquipment;
    }

    public String getNetworkProtocol() {
        return networkProtocol;
    }

    public void setNetworkProtocol(final String networkProtocol) {
        this.networkProtocol = networkProtocol;
    }

    public String getPppoeUsername() {
        return pppoeUsername;
    }

    public void setPppoeUsername(final String pppoeUsername) {
        this.pppoeUsername = pppoeUsername;
    }

    public String getPppoePassword() {
        return pppoePassword;
    }

    public void setPppoePassword(final String pppoePassword) {
        this.pppoePassword = pppoePassword;
    }

    public String getModemSerialNumber() {
        return modemSerialNumber;
    }

    public void setModemSerialNumber(final String modemSerialNumber) {
        this.modemSerialNumber = modemSerialNumber;
    }
}
