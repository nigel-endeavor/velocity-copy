package com.vertek.corporate.qto.activation.attempt.emailView;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * @author fcurran
 * @since 6/5/2023
 */
@Entity
@Table(name = "v_activations_email_template")
public class ActivationAttemptEmailView extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_attempt_id")
    private Long id;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "network_complete_date")
    private Date networkCompleteDate;

    @Column(name = "voip_complete_date")
    private Date voipCompleteDate;

    @Column(name = "tested_download_speed")
    private String testedDownloadSpeed;

    @Column(name = "tested_upload_speed")
    private String testedUploadSpeed;

    @Column(name = "signal_rsrp")
    private String signalRsrp;

    @Column(name = "sinr_rsrq")
    private String sinrRsrq;

    @Column(name = "backup_download_speed")
    private String backupDownloadSpeed;

    @Column(name = "backup_upload_speed")
    private String backupUploadSpeed;

    @Column(name = "location_downtown_for_cutover")
    private String locationDowntimeForCutover;

    @Column(name = "replace_4g_5g")
    private String replace4g5gWithBroadbandDia;

    @Column(name = "total_appointment_time")
    private String totalAppointmentTime;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "close_notes")
    private String closeNotes;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Override
    public Long getId() {
        return id;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(final String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Date getNetworkCompleteDate() {
        return networkCompleteDate;
    }

    public void setNetworkCompleteDate(final Date networkCompleteDate) {
        this.networkCompleteDate = networkCompleteDate;
    }

    public Date getVoipCompleteDate() {
        return voipCompleteDate;
    }

    public void setVoipCompleteDate(final Date voipCompleteDate) {
        this.voipCompleteDate = voipCompleteDate;
    }

    public String getTestedDownloadSpeed() {
        return testedDownloadSpeed;
    }

    public void setTestedDownloadSpeed(final String testedDownloadSpeed) {
        this.testedDownloadSpeed = testedDownloadSpeed;
    }

    public String getTestedUploadSpeed() {
        return testedUploadSpeed;
    }

    public void setTestedUploadSpeed(final String testedUploadSpeed) {
        this.testedUploadSpeed = testedUploadSpeed;
    }

    public String getSignalRsrp() {
        return signalRsrp;
    }

    public void setSignalRsrp(final String signalRsrp) {
        this.signalRsrp = signalRsrp;
    }

    public String getSinrRsrq() {
        return sinrRsrq;
    }

    public void setSinrRsrq(final String sinrRsrq) {
        this.sinrRsrq = sinrRsrq;
    }

    public String getBackupDownloadSpeed() {
        return backupDownloadSpeed;
    }

    public void setBackupDownloadSpeed(final String backupDownloadSpeed) {
        this.backupDownloadSpeed = backupDownloadSpeed;
    }

    public String getBackupUploadSpeed() {
        return backupUploadSpeed;
    }

    public void setBackupUploadSpeed(final String backupUploadSpeed) {
        this.backupUploadSpeed = backupUploadSpeed;
    }

    public String getLocationDowntimeForCutover() {
        return locationDowntimeForCutover;
    }

    public void setLocationDowntimeForCutover(final String locationDowntimeForCutover) {
        this.locationDowntimeForCutover = locationDowntimeForCutover;
    }

    public String getReplace4g5gWithBroadbandDia() {
        return replace4g5gWithBroadbandDia;
    }

    public void setReplace4g5gWithBroadbandDia(final String replace4g5gWithBroadbandDia) {
        this.replace4g5gWithBroadbandDia = replace4g5gWithBroadbandDia;
    }

    public String getTotalAppointmentTime() {
        return totalAppointmentTime;
    }

    public void setTotalAppointmentTime(final String totalAppointmentTime) {
        this.totalAppointmentTime = totalAppointmentTime;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public String getClientLocationInfo() {
        return clientLocationInfo;
    }

    public void setClientLocationInfo(final String clientLocationInfo) {
        this.clientLocationInfo = clientLocationInfo;
    }

    public String getCloseNotes() {
        return closeNotes;
    }

    public void setCloseNotes(final String closeNotes) {
        this.closeNotes = closeNotes;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
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
}
