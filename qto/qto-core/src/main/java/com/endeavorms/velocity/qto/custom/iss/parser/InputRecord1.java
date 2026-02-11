package com.endeavorms.velocity.qto.custom.iss.parser;

import com.endeavorms.velocity.qto.common.StandardBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "input_record_1")
public class InputRecord1 extends StandardBaseEntity {
    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_record_1_id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;


    @Column(name = "processed_folder_name")
    private String processedFolder;

    @Column(name = "created_service_id")
    private Long serviceId;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "address_1")
    private String Address1;

    @Column(name = "address_2")
    private String Address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "lcon_number")
    private String lconNumber;

    @Column(name = "lcon_name")
    private String lconName;

    @Column(name = "sar_number")
    private String sarNumber;

    @Column(name = "parent_entity")
    private String parentEntity;

    @Column(name = "franchise")
    private String franchise;

    @Column(name = "brand")
    private String brand;

    @Column(name = "sales_order_comp_date")
    private Date salesOrderCompDate;

    @Column(name = "est_install_comp_date")
    private Date estInstallCompDate;

    @Column(name = "vendor_number")
    private String vendorNumber;

    @Column(name = "purchase_order_number")
    private String purchaseOrderNumber;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "system_type")
    private String systemType;

    @Column(name = "air_card_flag")
    private String airCardFlag;

    @Column(name = "item_no")
    private String itemNo;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "iss_due_date")
    private Date issDueDate;

    @Column(name = "site_info")
    private String siteInfo;

    @Column(name = "site_type")
    private String siteType;

    @Column(name = "dbu")
    private String dbu;

    @Column(name = "phone_line_used")
    private String phoneLineUsed;

    @Column(name = "lec_provider")
    private String lecProvider;

    @Column(name = "provider_tn")
    private String providerTn;

    @Column(name = "complete_date")
    private Date completeDate;

    @Column(name = "tracker_id")
    private String trackerId;

    @Column(name = "job_number")
    private String jobNumber;

    @Column(name = "network_use")
    private String networkUse;

    @Column(name = "system_asset_number")
    private String SystemAssetNumber;

    @Override
    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getProcessedFolder() {
        return processedFolder;
    }

    public void setProcessedFolder(final String processedFolder) {
        this.processedFolder = processedFolder;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(final String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(final String siteName) {
        this.siteName = siteName;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(final String address1) {
        Address1 = address1;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(final String address2) {
        Address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getLconNumber() {
        return lconNumber;
    }

    public void setLconNumber(final String lconNumber) {
        this.lconNumber = lconNumber;
    }

    public String getLconName() {
        return lconName;
    }

    public void setLconName(final String lconName) {
        this.lconName = lconName;
    }

    public String getSarNumber() {
        return sarNumber;
    }

    public void setSarNumber(final String sarNumber) {
        this.sarNumber = sarNumber;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(final String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public String getFranchise() {
        return franchise;
    }

    public void setFranchise(final String franchise) {
        this.franchise = franchise;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public Date getSalesOrderCompDate() {
        return salesOrderCompDate;
    }

    public void setSalesOrderCompDate(final Date salesOrderCompDate) {
        this.salesOrderCompDate = salesOrderCompDate;
    }

    public Date getEstInstallCompDate() {
        return estInstallCompDate;
    }

    public void setEstInstallCompDate(final Date estInstallCompDate) {
        this.estInstallCompDate = estInstallCompDate;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(final String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setPurchaseOrderNumber(final String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(final String systemType) {
        this.systemType = systemType;
    }

    public String getAirCardFlag() {
        return airCardFlag;
    }

    public void setAirCardFlag(final String airCardFlag) {
        this.airCardFlag = airCardFlag;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(final String itemNo) {
        this.itemNo = itemNo;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(final String quantity) {
        this.quantity = quantity;
    }

    public Date getIssDueDate() {
        return issDueDate;
    }

    public void setIssDueDate(final Date issDueDate) {
        this.issDueDate = issDueDate;
    }

    public String getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(final String siteInfo) {
        this.siteInfo = siteInfo;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(final String siteType) {
        this.siteType = siteType;
    }

    public String getDbu() {
        return dbu;
    }

    public void setDbu(final String dbu) {
        this.dbu = dbu;
    }

    public String getPhoneLineUsed() {
        return phoneLineUsed;
    }

    public void setPhoneLineUsed(final String phoneLineUsed) {
        this.phoneLineUsed = phoneLineUsed;
    }

    public String getLecProvider() {
        return lecProvider;
    }

    public void setLecProvider(final String lecProvider) {
        this.lecProvider = lecProvider;
    }

    public String getProviderTn() {
        return providerTn;
    }

    public void setProviderTn(final String providerTn) {
        this.providerTn = providerTn;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(final String trackerId) {
        this.trackerId = trackerId;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(final String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getNetworkUse() {
        return networkUse;
    }

    public void setNetworkUse(final String networkUse) {
        this.networkUse = networkUse;
    }

    public String getSystemAssetNumber() {
        return SystemAssetNumber;
    }

    public void setSystemAssetNumber(final String systemAssetNumber) {
        SystemAssetNumber = systemAssetNumber;
    }
}
