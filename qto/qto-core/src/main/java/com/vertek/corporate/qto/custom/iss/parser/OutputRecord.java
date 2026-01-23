package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "v_iss_output_file")
public class OutputRecord extends StandardBaseEntity {

    /**
     * ID.
     */
    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "location_id")
    private Long locationId;


    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "appointment_time")
    private String appointmentTime;

    @Column(name = "scheduling_segment")
    private String schedulingSegment;

    @Column(name = "installation_status")
    private String installationStatus;

    @Column(name = "installation_status_date")
    private String installationStatusDate;

    @Column(name = "project")
    private String project;

    @Column(name = "signonff_number")
    private String signonffNumber;

    @Column(name = "demarc")
    private String demarc;

    @Column(name = "preinstallation_checklist_complete")
    private String preinstallationChecklistComplete;

    @Column(name = "supplier_order_number")
    private String supplierOrderNumber;

    @Column(name = "carrier_order_submitted")
    private String carrierOrderSubmitted;

    @Column(name = "jeop_description")
    private String jeop;

    @Column(name = "provider_foc")
    private String confirmedFocDate;

    @Column(name = "estimated_foc_date")
    private String estimatedFocDate;

    @Column(name = "customer_bill_start")
    private String custBillStartDate;

    @Column(name = "circuit_owner")
    private String circuitOwner;

    @Column(name = "local_lec_circuit_id")
    private String localLecCircuitId;

    @Column(name = "subproduct_type")
    private String subproductType;

    @Column(name = "LECProvider")
    private String supplierSupplierCktId;

    @Column(name = "supplier_tn")
    private String supplierTn;

    @Column(name = "static_ip")
    private String staticIp;

    @Column(name = "pppoe_username")
    private String userName;

    @Column(name = "pppoe_password")
    private String userPassword;

    @Column(name = "vpi")
    private String vpi;

    @Column(name = "vci")
    private String vci;

    @Column(name = "dsl_number")
    private String dslNumber;

    @Column(name = "dsl_connection_type")
    private String dslConnectionType;

    @Column(name = "dsl_1fb_order")
    private String dsl1fbOrder;

    @Column(name = "dsl_owner")
    private String dslOwner;

    @Column(name = "dsl_provider")
    private String dslProvider;

    @Column(name = "router_make_model")
    private String routerMakeModel;

    @Column(name = "router_own_lease")
    private String routerOwnLease;

    @Column(name = "router_shipped")
    private String routerShipped;

    @Column(name = "router_configured")
    private String routerConfigured;

    @Column(name = "router_tracking_number")
    private String routerTrackingNumber;

    @Column(name = "modem_make")
    private String modemMakeModel;

    @Column(name = "modem_shipped")
    private String modemShipped;

    @Column(name = "modem_on_site")
    private String modemOnSite;

    @Column(name = "modem_tracking_number")
    private String modemTrackingNumber;

    @Column(name = "modem_ownership")
    private String modemOwnership;

    @Column(name = "aircard_product")
    private String aircardProduct;

    @Column(name = "aircard_provider")
    private String aircardProvider;

    @Column(name = "aircard_tn")
    private String aircardTn;

    @Column(name = "aircard_status")
    private String aircardStatus;

    @Column(name = "aircard_status_date")
    private String aircardStatusDate;

    @Column(name = "modem_serial_number")
    private String modemSerialNumber;

    @Column(name = "host_oms_number")
    private String hostOmsNumber;

    @Column(name = "dsl_line_type")
    private String dslLineType;

    @Column(name = "site_description")
    private String siteDescription;

    @Column(name = "wan_router_ip")
    private String wanRouterIp;

    @Column(name = "lan_ips")
    private String lanIps;

    @Column(name = "wan_ips")
    private String wanIps;

    @Column(name = "download_speed")
    private String downloadSpeed;

    @Column(name = "download_speed_type")
    private String downloadSpeedType;

    @Column(name = "upload_speed")
    private String uploadSpeed;

    @Column(name = "upload_speed_type")
    private String uploadSpeedType;

    @Column(name = "mos_score")
    private String mosScore;

    @Column(name = "complete_date")
    private String completeDate;

    @Column(name = "ticket_status")
    private String ticketStatus;

    @Column(name = "customer_requested_install")
    private String customerRequestedDate;

    @Column(name = "erfu_ptd_date")
    private String ErfuPtdDate;

    @Column(name = "revised_completion_date")
    private String RevisedCompletionDate;

    @Column(name = "client_due_date")
    private String ClientDueDate;

    @Column(name = "wap_serial_number")
    private String WapSerialNumber;

    @Column(name = "wap_serial_number_2")
    private String WapSerialNumber2;

    @Column(name = "wap_serial_number_3")
    private String WapSerialNumber3;

    @Column(name = "lec_contract_expiry_date")
    private String LecContractExpiryDate;

    @Column(name = "pos")
    private String pos;

    @Column(name = "supplier_ban")
    private String supplierBan;

    @Column(name = "store_downtime")
    private String storeDowntime;

    @Column(name = "network_use")
    private String networkUse;

    @Column(name = "system_asset_number")
    private String systemAssetNumber;


    @Override
    public Long getId() {
        return id;
    }



    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(final String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getAppointmentString() {
        return appointmentDate;
    }

    public void setAppointmentDate(final String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(final String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getSchedulingSegment() {
        return schedulingSegment;
    }

    public void setSchedulingSegment(final String schedulingSegment) {
        this.schedulingSegment = schedulingSegment;
    }

    public String getInstallationStatus() {
        return installationStatus;
    }

    public void setInstallationStatus(final String installationStatus) {
        this.installationStatus = installationStatus;
    }

    public String getInstallationStatusDate() {
        return installationStatusDate;
    }

    public void setInstallationStatusDate(final String installationStatusDate) {
        this.installationStatusDate = installationStatusDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(final String project) {
        this.project = project;
    }

    public String getSignonffNumber() {
        return signonffNumber;
    }

    public void setSignonffNumber(final String signonffNumber) {
        this.signonffNumber = signonffNumber;
    }

    public String getDemarc() {
        return demarc;
    }

    public void setDemarc(final String demarc) {
        this.demarc = demarc;
    }

    public String getPreinstallationChecklistComplete() {
        return preinstallationChecklistComplete;
    }

    public void setPreinstallationChecklistComplete(final String preinstallationChecklistComplete) {
        this.preinstallationChecklistComplete = preinstallationChecklistComplete;
    }

    public String getSupplierOrderNumber() {
        return supplierOrderNumber;
    }

    public void setSupplierOrderNumber(final String supplierOrderNumber) {
        this.supplierOrderNumber = supplierOrderNumber;
    }

    public String getCarrierOrderSubmitted() {
        return carrierOrderSubmitted;
    }

    public void setCarrierOrderSubmitted(final String carrierOrderSubmitted) {
        this.carrierOrderSubmitted = carrierOrderSubmitted;
    }

    public String getJeop() {
        return jeop;
    }

    public void setJeop(String jeop) {
        this.jeop = jeop;
    }

    public String getConfirmedFocDate() {
        return confirmedFocDate;
    }

    public void setConfirmedFocDate(final String confirmedFocDate) {
        this.confirmedFocDate = confirmedFocDate;
    }

    public String getEstimatedFocDate() {
        return estimatedFocDate;
    }

    public void setEstimatedFocDate(final String estimatedFocDate) {
        this.estimatedFocDate = estimatedFocDate;
    }

    public String getCustBillStartDate() {
        return custBillStartDate;
    }

    public void setCustBillStartDate(final String custBillStartDate) {
        this.custBillStartDate = custBillStartDate;
    }

    public String getCircuitOwner() {
        return circuitOwner;
    }

    public void setCircuitOwner(final String circuitOwner) {
        this.circuitOwner = circuitOwner;
    }

    public String getLocalLecCircuitId() {
        return localLecCircuitId;
    }

    public void setLocalLecCircuitId(final String localLecCircuitId) {
        this.localLecCircuitId = localLecCircuitId;
    }

    public String getSubproductType() {
        return subproductType;
    }

    public void setSubproductType(final String subproductType) {
        this.subproductType = subproductType;
    }

    public String getSupplierSupplierCktId() {
        return supplierSupplierCktId;
    }

    public void setSupplierSupplierCktId(final String supplierSupplierCktId) {
        this.supplierSupplierCktId = supplierSupplierCktId;
    }

    public String getSupplierTn() {
        return supplierTn;
    }

    public void setSupplierTn(final String supplierTn) {
        this.supplierTn = supplierTn;
    }

    public String getStaticIp() {
        return staticIp;
    }

    public void setStaticIp(final String staticIp) {
        this.staticIp = staticIp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public String getVpi() {
        return vpi;
    }

    public void setVpi(final String vpi) {
        this.vpi = vpi;
    }

    public String getVci() {
        return vci;
    }

    public void setVci(final String vci) {
        this.vci = vci;
    }

    public String getDslNumber() {
        return dslNumber;
    }

    public void setDslNumber(final String dslNumber) {
        this.dslNumber = dslNumber;
    }

    public String getDslConnectionType() {
        return dslConnectionType;
    }

    public void setDslConnectionType(final String dslConnectionType) {
        this.dslConnectionType = dslConnectionType;
    }

    public String getDsl1fbOrder() {
        return dsl1fbOrder;
    }

    public void setDsl1fbOrder(final String dsl1fbOrder) {
        this.dsl1fbOrder = dsl1fbOrder;
    }

    public String getDslOwner() {
        return dslOwner;
    }

    public void setDslOwner(final String dslOwner) {
        this.dslOwner = dslOwner;
    }

    public String getDslProvider() {
        return dslProvider;
    }

    public void setDslProvider(final String dslProvider) {
        this.dslProvider = dslProvider;
    }

    public String getRouterMakeModel() {
        return routerMakeModel;
    }

    public void setRouterMakeModel(final String routerMakeModel) {
        this.routerMakeModel = routerMakeModel;
    }

    public String getRouterOwnLease() {
        return routerOwnLease;
    }

    public void setRouterOwnLease(final String routerOwnLease) {
        this.routerOwnLease = routerOwnLease;
    }

    public String getRouterShipped() {
        return routerShipped;
    }

    public void setRouterShipped(final String routerShipped) {
        this.routerShipped = routerShipped;
    }

    public String getRouterConfigured() {
        return routerConfigured;
    }

    public void setRouterConfigured(final String routerConfigured) {
        this.routerConfigured = routerConfigured;
    }

    public String getRouterTrackingNumber() {
        return routerTrackingNumber;
    }

    public void setRouterTrackingNumber(final String routerTrackingNumber) {
        this.routerTrackingNumber = routerTrackingNumber;
    }

    public String getModemMakeModel() {
        return modemMakeModel;
    }

    public void setModemMakeModel(final String modemMakeModel) {
        this.modemMakeModel = modemMakeModel;
    }

    public String getModemShipped() {
        return modemShipped;
    }

    public void setModemShipped(final String modemShipped) {
        this.modemShipped = modemShipped;
    }

    public String getModemOnSite() {
        return modemOnSite;
    }

    public void setModemOnSite(final String modemOnSite) {
        this.modemOnSite = modemOnSite;
    }

    public String getModemTrackingNumber() {
        return modemTrackingNumber;
    }

    public void setModemTrackingNumber(final String modemTrackingNumber) {
        this.modemTrackingNumber = modemTrackingNumber;
    }

    public String getModemOwnership() {
        return modemOwnership;
    }

    public void setModemOwnership(final String modemOwnership) {
        this.modemOwnership = modemOwnership;
    }

    public String getAircardProduct() {
        return aircardProduct;
    }

    public void setAircardProduct(final String aircardProduct) {
        this.aircardProduct = aircardProduct;
    }

    public String getAircardProvider() {
        return aircardProvider;
    }

    public void setAircardProvider(final String aircardProvider) {
        this.aircardProvider = aircardProvider;
    }

    public String getAircardTn() {
        return aircardTn;
    }

    public void setAircardTn(final String aircardTn) {
        this.aircardTn = aircardTn;
    }

    public String getAircardStatus() {
        return aircardStatus;
    }

    public void setAircardStatus(final String aircardStatus) {
        this.aircardStatus = aircardStatus;
    }

    public String getAircardStatusDate() {
        return aircardStatusDate;
    }

    public void setAircardStatusDate(final String aircardStatusDate) {
        this.aircardStatusDate = aircardStatusDate;
    }

    public String getModemSerialNumber() {
        return modemSerialNumber;
    }

    public void setModemSerialNumber(final String modemSerialNumber) {
        this.modemSerialNumber = modemSerialNumber;
    }

    public String getHostOmsNumber() {
        return hostOmsNumber;
    }

    public void setHostOmsNumber(final String hostOmsNumber) {
        this.hostOmsNumber = hostOmsNumber;
    }

    public String getDslLineType() {
        return dslLineType;
    }

    public void setDslLineType(final String dslLineType) {
        this.dslLineType = dslLineType;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(final String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getWanRouterIp() {
        return wanRouterIp;
    }

    public void setWanRouterIp(final String wanRouterIp) {
        this.wanRouterIp = wanRouterIp;
    }

    public String getLanIps() {
        return lanIps;
    }

    public void setLanIps(final String lanIps) {
        this.lanIps = lanIps;
    }

    public String getWanIps() {
        return wanIps;
    }

    public void setWanIps(final String wanIps) {
        this.wanIps = wanIps;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(final String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getDownloadSpeedType() {
        return downloadSpeedType;
    }

    public void setDownloadSpeedType(final String downloadSpeedType) {
        this.downloadSpeedType = downloadSpeedType;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(final String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public String getUploadSpeedType() {
        return uploadSpeedType;
    }

    public void setUploadSpeedType(final String uploadSpeedType) {
        this.uploadSpeedType = uploadSpeedType;
    }

    public String getMosScore() {
        return mosScore;
    }

    public void setMosScore(final String mosScore) {
        this.mosScore = mosScore;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(final String completeDate) {
        this.completeDate = completeDate;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(final String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getCustomerRequestedDate() {
        return customerRequestedDate;
    }

    public void setCustomerRequestedDate(final String customerRequestedDate) {
        this.customerRequestedDate = customerRequestedDate;
    }

    public String getErfuPtdDate() {
        return ErfuPtdDate;
    }

    public void setErfuPtdDate(final String erfuPtdDate) {
        ErfuPtdDate = erfuPtdDate;
    }

    public String getRevisedCompletionDate() {
        return RevisedCompletionDate;
    }

    public void setRevisedCompletionDate(final String revisedCompletionDate) {
        RevisedCompletionDate = revisedCompletionDate;
    }

    public String getClientDueDate() {
        return ClientDueDate;
    }

    public void setClientDueDate(final String clientDueDate) {
        ClientDueDate = clientDueDate;
    }

    public String getWapSerialNumber() {
        return WapSerialNumber;
    }

    public void setWapSerialNumber(final String wapSerialNumber) {
        WapSerialNumber = wapSerialNumber;
    }

    public String getWapSerialNumber2() {
        return WapSerialNumber2;
    }

    public void setWapSerialNumber2(final String wapSerialNumber2) {
        WapSerialNumber2 = wapSerialNumber2;
    }

    public String getWapSerialNumber3() {
        return WapSerialNumber3;
    }

    public void setWapSerialNumber3(final String wapSerialNumber3) {
        WapSerialNumber3 = wapSerialNumber3;
    }

    public String getLecContractExpiryDate() {
        return LecContractExpiryDate;
    }

    public void setLecContractExpiryDate(final String lecContractExpiryDate) {
        LecContractExpiryDate = lecContractExpiryDate;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(final String pos) {
        this.pos = pos;
    }

    public String getSupplierBan() {
        return supplierBan;
    }

    public void setSupplierBan(final String supplierBan) {
        this.supplierBan = supplierBan;
    }

    public String getStoreDowntime() {
        return storeDowntime;
    }

    public void setStoreDowntime(final String storeDowntime) {
        this.storeDowntime = storeDowntime;
    }

    public String getNetworkUse() {
        return networkUse;
    }

    public void setNetworkUse(final String networkUse) {
        this.networkUse = networkUse;
    }

    public String getSystemAssetNumber() {
        return systemAssetNumber;
    }

    public void setSystemAssetNumber(final String systemAssetNumber) {
        this.systemAssetNumber = systemAssetNumber;
    }
}
