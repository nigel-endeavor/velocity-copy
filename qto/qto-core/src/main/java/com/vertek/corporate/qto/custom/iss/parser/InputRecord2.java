package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardBaseEntity;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "input_record_2")
public class InputRecord2  extends StandardBaseEntity {

     /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_record_2_id")
    private Long id;

    @Column(name = "input_record_1_id")
    private Long inputRecord1Id;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "circuit_ownership")
    private String circuitOwnership;

    @Column(name = "special_install_instructions")
    private String specialInstallInstructions;

    @Column(name = "dmarc_info")
    private String dmarcInfo;

    @Column(name = "download_speed")
    private String downloadSpeed;

    @Column(name = "download_speed_type")
    private String downloadSpeedType;

    @Column(name = "1fb_order_number")
    private String OneFbOrderNumber;

    @Column(name = "connection_type")
    private String connectionType;

    @Column(name = "line_type")
    private String lineType;

    @Column(name = "dsl_phone_number")
    private String dslPhoneNumber;

    @Column(name = "dsl_1fb_owner")
    private String dsl1fbOwner;

    @Column(name = "dsl_1fb_provider")
    private String dsl1fbProvider;

    @Column(name = "lan_ips")
    private String lanIps;

    @Column(name = "lec_contract_expiry_date")
    private Date lecContractExpiryDate;

    @Column(name = "lec_circuit_id")
    private String lecCircuitId;

    @Column(name = "modem_brand_model")
    private String modemBrandModel;

    @Column(name = "modem_on_site")
    private String modemOnSite;

    @Column(name = "modem_ownership")
    private String modemOwnership;

    @Column(name = "modem_serial_number")
    private String modemSerialNumber;

    @Column(name = "pos")
    private String pos;

    @Column(name = "router_configured")
    private String RouterConfigured;

    @Column(name = "router_brand_model")
    private String RouterBrandModel;

    @Column(name = "router_ownership")
    private String RouterOwnership;

    @Column(name = "static_ip")
    private String StaticIp;

    @Column(name = "subproduct_type")
    private String SubproductType;

    @Column(name = "supplier_supplier_ckt_id")
    private String SupplierSupplierCktId;

    @Column(name = "supplier_ban")
    private String SupplierBan;

    @Column(name = "upload_speed")
    private String UploadSpeed;

    @Column(name = "upload_speed_type")
    private String UploadSpeedType;

    @Column(name = "user_name")
    private String UserName;

    @Column(name = "user_password")
    private String UserPassword;

    @Column(name = "system_asset_number")
    private String SystemAssetNumber;




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

    public String getCircuitOwnership() {
        return circuitOwnership;
    }

    public void setCircuitOwnership(final String circuitOwnership) {
        this.circuitOwnership = circuitOwnership;
    }

    public String getSpecialInstallInstructions() {
        return specialInstallInstructions;
    }

    public void setSpecialInstallInstructions(final String specialInstallInstructions) {
        this.specialInstallInstructions = specialInstallInstructions;
    }

    public String getDmarcInfo() {
        return dmarcInfo;
    }

    public void setDmarcInfo(final String dmarcInfo) {
        this.dmarcInfo = dmarcInfo;
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

    public String getOneFbOrderNumber() {
        return OneFbOrderNumber;
    }

    public void setOneFbOrderNumber(final String oneFbOrderNumber) {
        OneFbOrderNumber = oneFbOrderNumber;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(final String connectionType) {
        this.connectionType = connectionType;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(final String lineType) {
        this.lineType = lineType;
    }

    public String getDslPhoneNumber() {
        return dslPhoneNumber;
    }

    public void setDslPhoneNumber(final String dslPhoneNumber) {
        this.dslPhoneNumber = dslPhoneNumber;
    }

    public String getDsl1fbOwner() {
        return dsl1fbOwner;
    }

    public void setDsl1fbOwner(final String dsl1fbOwner) {
        this.dsl1fbOwner = dsl1fbOwner;
    }

    public String getDsl1fbProvider() {
        return dsl1fbProvider;
    }

    public void setDsl1fbProvider(final String dsl1fbProvider) {
        this.dsl1fbProvider = dsl1fbProvider;
    }

    public String getLanIps() {
        return lanIps;
    }

    public void setLanIps(final String lanIps) {
        this.lanIps = lanIps;
    }

    public Date getLecContractExpiryDate() {
        return lecContractExpiryDate;
    }

    public void setLecContractExpiryDate(final Date lecContractExpiryDate) {
        this.lecContractExpiryDate = lecContractExpiryDate;
    }

    public String getLecCircuitId() {
        return lecCircuitId;
    }

    public void setLecCircuitId(final String lecCircuitId) {
        this.lecCircuitId = lecCircuitId;
    }

    public String getModemBrandModel() {
        return modemBrandModel;
    }

    public void setModemBrandModel(final String modemBrandModel) {
        this.modemBrandModel = modemBrandModel;
    }

    public String getModemOnSite() {
        return modemOnSite;
    }

    public void setModemOnSite(final String modemOnSite) {
        this.modemOnSite = modemOnSite;
    }

    public String getModemOwnership() {
        return modemOwnership;
    }

    public void setModemOwnership(final String modemOwnership) {
        this.modemOwnership = modemOwnership;
    }

    public String getModemSerialNumber() {
        return modemSerialNumber;
    }

    public void setModemSerialNumber(final String modemSerialNumber) {
        this.modemSerialNumber = modemSerialNumber;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(final String pos) {
        this.pos = pos;
    }

    public String getRouterConfigured() {
        return RouterConfigured;
    }

    public void setRouterConfigured(final String routerConfigured) {
        RouterConfigured = routerConfigured;
    }

    public String getRouterBrandModel() {
        return RouterBrandModel;
    }

    public void setRouterBrandModel(final String routerBrandModel) {
        RouterBrandModel = routerBrandModel;
    }

    public String getRouterOwnership() {
        return RouterOwnership;
    }

    public void setRouterOwnership(final String routerOwnership) {
        RouterOwnership = routerOwnership;
    }

    public String getStaticIp() {
        return StaticIp;
    }

    public void setStaticIp(final String staticIp) {
        StaticIp = staticIp;
    }

    public String getSubproductType() {
        return SubproductType;
    }

    public void setSubproductType(final String subproductType) {
        SubproductType = subproductType;
    }

    public String getSupplierSupplierCktId() {
        return SupplierSupplierCktId;
    }

    public void setSupplierSupplierCktId(final String supplierSupplierCktId) {
        SupplierSupplierCktId = supplierSupplierCktId;
    }

    public String getSupplierBan() {
        return SupplierBan;
    }

    public void setSupplierBan(final String supplierBan) {
        SupplierBan = supplierBan;
    }

    public String getUploadSpeed() {
        return UploadSpeed;
    }

    public void setUploadSpeed(final String uploadSpeed) {
        UploadSpeed = uploadSpeed;
    }

    public String getUploadSpeedType() {
        return UploadSpeedType;
    }

    public void setUploadSpeedType(final String uploadSpeedType) {
        UploadSpeedType = uploadSpeedType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(final String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(final String userPassword) {
        UserPassword = userPassword;
    }

    public String getSystemAssetNumber() {
        return SystemAssetNumber;
    }

    public void setSystemAssetNumber(final String systemAssetNumber) {
        SystemAssetNumber = systemAssetNumber;
    }
}
