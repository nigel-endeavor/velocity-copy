package com.vertek.corporate.qto.service._4g5g;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.math.BigDecimal;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@Entity
@Table(name = "4g5g_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class GService extends Service {

    @Column(name = "uid")
    private String uid;

    @Column(name = "iccid")
    private String iccid;

    @Column(name = "imei")
    private String imei;

    @Column(name = "mdn")
    private String mdn;

    @Column(name = "apn")
    private String apn;

    @Column(name = "rate_plan")
    private String ratePlan;

    @Column(name = "rsrp")
    private BigDecimal rsrp;

    @Column(name = "rsrq")
    private BigDecimal rsrq;

    @Column(name = "sinr")
    private BigDecimal sinr;

    @Column(name = "rssi")
    private BigDecimal rssi;

    @Column(name = "replace_4g_5g")
    private String replace4g5g;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "circuit_priority")
    private String circuitPriority;

    @PrePersist
    void prePersist() {
        setType(ServiceType.G.getServiceName());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(final String iccid) {
        this.iccid = iccid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(final String imei) {
        this.imei = imei;
    }

    public String getMdn() {
        return mdn;
    }

    public void setMdn(final String mdn) {
        this.mdn = mdn;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(final String apn) {
        this.apn = apn;
    }

    public String getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(final String ratePlan) {
        this.ratePlan = ratePlan;
    }

    public BigDecimal getRsrp() {
        return rsrp;
    }

    public void setRsrp(final BigDecimal rsrp) {
        this.rsrp = rsrp;
    }

    public BigDecimal getRsrq() {
        return rsrq;
    }

    public void setRsrq(final BigDecimal rsrq) {
        this.rsrq = rsrq;
    }

    public BigDecimal getSinr() {
        return sinr;
    }

    public void setSinr(final BigDecimal sinr) {
        this.sinr = sinr;
    }

    public BigDecimal getRssi() {
        return rssi;
    }

    public void setRssi(final BigDecimal rssi) {
        this.rssi = rssi;
    }

    public String getReplace4g5g() {
        return replace4g5g;
    }

    public void setReplace4g5g(final String replace4g5g) {
        this.replace4g5g = replace4g5g;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(final String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(final String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCircuitPriority() {
        return circuitPriority;
    }

    public void setCircuitPriority(final String circuitPriority) {
        this.circuitPriority = circuitPriority;
    }
}
