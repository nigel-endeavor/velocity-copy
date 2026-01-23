package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardBaseEntity;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "input_record_3")
public class InputRecord3  extends StandardBaseEntity {

        /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_record_3_id")
    private Long id;

    @Column(name = "input_record_1_id")
    private Long inputRecord1Id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "circuit_wan_ip")
    private String circuitWanIp;

    @Column(name = "wan_router_ip")
    private String wanRouterIp;

    @Column(name = "wap_serial_number")
    private String wapSerialNumber;

    @Column(name = "wap_serial_number_2")
    private String wapSerialNumber2;

    @Column(name = "wap_serial_number_3")
    private String wapSerialNumber3;

    @Column(name = "demarc_comments")
    private String demarcComments;

    @Column(name = "oms_billing_account_number")
    private String omsBillingAccountNumber;

    @Column(name = "router_serial_number")
    private String routerSerialNumber;

    @Column(name = "system_asset_number")
    private String systemAssetNumber;

    @Override
    public Long getId() {
        return id;
    }

    public Long getInputRecord1Id() {
        return inputRecord1Id;
    }

    public void setInputRecord1Id(final Long inputRecord1Id) {
        this.inputRecord1Id = inputRecord1Id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(final String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getCircuitWanIp() {
        return circuitWanIp;
    }

    public void setCircuitWanIp(final String circuitWanIp) {
        this.circuitWanIp = circuitWanIp;
    }

    public String getWanRouterIp() {
        return wanRouterIp;
    }

    public void setWanRouterIp(final String wanRouterIp) {
        this.wanRouterIp = wanRouterIp;
    }

    public String getWapSerialNumber() {
        return wapSerialNumber;
    }

    public void setWapSerialNumber(final String wapSerialNumber) {
        this.wapSerialNumber = wapSerialNumber;
    }

    public String getWapSerialNumber2() {
        return wapSerialNumber2;
    }

    public void setWapSerialNumber2(final String wapSerialNumber2) {
        this.wapSerialNumber2 = wapSerialNumber2;
    }

    public String getWapSerialNumber3() {
        return wapSerialNumber3;
    }

    public void setWapSerialNumber3(final String wapSerialNumber3) {
        this.wapSerialNumber3 = wapSerialNumber3;
    }

    public String getDemarcComments() {
        return demarcComments;
    }

    public void setDemarcComments(final String demarcComments) {
        this.demarcComments = demarcComments;
    }

    public String getOmsBillingAccountNumber() {
        return omsBillingAccountNumber;
    }

    public void setOmsBillingAccountNumber(final String omsBillingAccountNumber) {
        this.omsBillingAccountNumber = omsBillingAccountNumber;
    }

    public String getRouterSerialNumber() {
        return routerSerialNumber;
    }

    public void setRouterSerialNumber(final String routerSerialNumber) {
        this.routerSerialNumber = routerSerialNumber;
    }

    public String getSystemAssetNumber() {
        return systemAssetNumber;
    }

    public void setSystemAssetNumber(final String systemAssetNumber) {
        this.systemAssetNumber = systemAssetNumber;
    }
}
