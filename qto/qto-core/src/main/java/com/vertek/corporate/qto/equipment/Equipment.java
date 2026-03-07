package com.vertek.corporate.qto.equipment;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * Equipment entity.
 * @author fcurran
 * @since 1.15.0
 */
@Entity
@Table(name = "equipment")
@Inheritance(strategy = InheritanceType.JOINED)
public class Equipment extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipment_id")
    private Long id;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "equipment_subtype")
    private String equipmentSubtype;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "ownership")
    private String ownership;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "tracking_info")
    private String trackingInfo;

    @Column(name = "ship_date")
    private Date shipDate;

    @Column(name = "delivered_date")
    private Date deliveredDate;

    @Column(name = "description")
    private String description;

    @Column(name = "network_ip_range")
    private String networkIpRange;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "gateway")
    private String gateway;

    @Column(name = "dns1")
    private String dns1;

    @Column(name = "dns2")
    private String dns2;

    @Column(name = "decommission")
    private boolean decommission;

    @Column(name = "decommissioned_date")
    private Date decommissionedDate;

    @Override
    public Long getId() {
        return id;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(final String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentSubtype() {
        return equipmentSubtype;
    }

    public void setEquipmentSubtype(final String equipmentSubtype) {
        this.equipmentSubtype = equipmentSubtype;
    }

    public String getMake() {
        return make;
    }

    public void setMake(final String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(final String macAddress) {
        this.macAddress = macAddress;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(final String ownership) {
        this.ownership = ownership;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(final String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getTrackingInfo() {
        return trackingInfo;
    }

    public void setTrackingInfo(final String trackingInfo) {
        this.trackingInfo = trackingInfo;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(final Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(final Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getNetworkIpRange() {
        return networkIpRange;
    }

    public void setNetworkIpRange(final String networkIpRange) {
        this.networkIpRange = networkIpRange;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(final String gateway) {
        this.gateway = gateway;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(final String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(final String dns2) {
        this.dns2 = dns2;
    }

    public boolean isDecommission() {
        return decommission;
    }

    public void setDecommission(final boolean decommission) {
        this.decommission = decommission;
    }

    public Date getDecommissionedDate() {
        return decommissionedDate;
    }

    public void setDecommissionedDate(final Date decommissionedDate) {
        this.decommissionedDate = decommissionedDate;
    }
}
