package com.vertek.corporate.qto.service.microsoftLicenses;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name= "service_microsoft_licenses")
@PrimaryKeyJoinColumn(name = "service_id")
public class MicrosoftLicensesService extends Service {

    @Column(name = "e3")
    private Long e3;

    @Column(name = "e5")
    private Long e5;

    @Column(name = "business_premium")
    private Long businessPremium;

    @Column(name = "microsoft_entra_id_p1")
    private Long microsoftEntraIdP1;

    @Column(name = "microsoft_entra_id_p2")
    private Long microsoftEntraIdP2;

    @Column(name = "microsoft_entra_suite")
    private Long microsoftEntraSuite;

    @Column(name = "microsoft_intune_plan_1")
    private Long microsoftIntunePlan1;

    @Column(name = "microsoft_intune_plan_2")
    private Long microsoftIntunePlan2;

    @Column(name = "microsoft_defender_for_endpoint_p1")
    private Long microsoftDefenderForEndpointP1;

    @Column(name = "microsoft_defender_for_endpoint_p2")
    private Long microsoftDefenderForEndpointP2;

    @Column(name = "defender_for_office_365_plan_1")
    private Long defenderForOffice365Plan1;

    @Column(name = "defender_for_office_365_plan_2")
    private Long defenderForOffice365Plan2;



    @PrePersist
    void prePersist() {
        setType(ServiceType.MICROSOFTLICENSES.getServiceName());
    }

    public Long getE3() {
        return e3;
    }

    public void setE3(Long e3) {
        this.e3 = e3;
    }

    public Long getE5() {
        return e5;
    }

    public void setE5(Long e5) {
        this.e5 = e5;
    }

    public Long getBusinessPremium() {
        return businessPremium;
    }

    public void setBusinessPremium(Long businessPremium) {
        this.businessPremium = businessPremium;
    }

    public Long getMicrosoftEntraIdP1() {
        return microsoftEntraIdP1;
    }

    public void setMicrosoftEntraIdP1(Long microsoftEntraIdP1) {
        this.microsoftEntraIdP1 = microsoftEntraIdP1;
    }

    public Long getMicrosoftEntraIdP2() {
        return microsoftEntraIdP2;
    }

    public void setMicrosoftEntraIdP2(Long microsoftEntraIdP2) {
        this.microsoftEntraIdP2 = microsoftEntraIdP2;
    }

    public Long getMicrosoftEntraSuite() {
        return microsoftEntraSuite;
    }

    public void setMicrosoftEntraSuite(Long microsoftEntraSuite) {
        this.microsoftEntraSuite = microsoftEntraSuite;
    }

    public Long getMicrosoftIntunePlan1() {
        return microsoftIntunePlan1;
    }

    public void setMicrosoftIntunePlan1(Long microsoftIntunePlan1) {
        this.microsoftIntunePlan1 = microsoftIntunePlan1;
    }

    public Long getMicrosoftIntunePlan2() {
        return microsoftIntunePlan2;
    }

    public void setMicrosoftIntunePlan2(Long microsoftIntunePlan2) {
        this.microsoftIntunePlan2 = microsoftIntunePlan2;
    }

    public Long getMicrosoftDefenderForEndpointP1() {
        return microsoftDefenderForEndpointP1;
    }

    public void setMicrosoftDefenderForEndpointP1(Long microsoftDefenderForEndpointP1) {
        this.microsoftDefenderForEndpointP1 = microsoftDefenderForEndpointP1;
    }

    public Long getMicrosoftDefenderForEndpointP2() {
        return microsoftDefenderForEndpointP2;
    }

    public void setMicrosoftDefenderForEndpointP2(Long microsoftDefenderForEndpointP2) {
        this.microsoftDefenderForEndpointP2 = microsoftDefenderForEndpointP2;
    }

    public Long getDefenderForOffice365Plan1() {
        return defenderForOffice365Plan1;
    }

    public void setDefenderForOffice365Plan1(Long defenderForOffice365Plan1) {
        this.defenderForOffice365Plan1 = defenderForOffice365Plan1;
    }

    public Long getDefenderForOffice365Plan2() {
        return defenderForOffice365Plan2;
    }

    public void setDefenderForOffice365Plan2(Long defenderForOffice365Plan2) {
        this.defenderForOffice365Plan2 = defenderForOffice365Plan2;
    }
}
