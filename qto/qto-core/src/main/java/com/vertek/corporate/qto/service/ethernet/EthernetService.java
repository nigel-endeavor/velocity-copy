package com.vertek.corporate.qto.service.ethernet;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 1/17/2024
 */
@Entity
@Table(name = "ethernet_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class EthernetService extends Service {

    @Column(name = "product_type")
    private String productType;

    @Column(name = "port_speed")
    private String portSpeed;

    @Column(name = "ea_speed")
    private String eaSpeed;

    @Column(name = "mtu")
    private String mtu;

    @Column(name = "mux")
    private String mux;

    @Column(name = "vlan_for_eline")
    private String vlanForEline;

    @Column(name = "vlan_tagging")
    private String vlanTagging;

    @Column(name = "vlan_id")
    private String vlanId;

    @Column(name = "cable_category")
    private String cableCategory;

    @Column(name = "cable_shielding")
    private String cableShielding;

    @Column(name = "data_center_name")
    private String dataCenterName;

    @Column(name = "provider_circuit_id")
    private String providerCircuitId;

    @Column(name = "access_type")
    private String accessType;

    @Column(name = "interface_connector")
    private String interfaceConnector;

    @Column(name = "access_hours")
    private String accessHours;

    @Column(name = "manned")
    private String manned;

    @Column(name = "loa_required")
    private String loaRequired;

    @Column(name = "handoff_fiber_mode")
    private String handoffFiberMode;

    @Column(name = "handoff_connector_type")
    private String handoffConnectorType;

    @Column(name = "clli_code")
    private String clliCode;

    @Column(name = "pop_clli")
    private String popClli;

    @Column(name = "alternate_pop_clli")
    private String alternatePopClli;

    @Column(name = "floor")
    private String floor;

    @Column(name = "npa_nxx")
    private String npaNxx;

    @Column(name = "cfa")
    private String cfa;

    @Column(name = "z_data_center_name")
    private String zDataCenterName;
    
    @Column(name = "z_provider_circuit_id")
    private String zProviderCircuitId;
    
    @Column(name = "z_building_status")
    private String zBuildingStatus;
    
    @Column(name = "z_access_type")
    private String zAccessType;
    
    @Column(name = "z_interface_connector")
    private String zInterfaceConnector;
    
    @Column(name = "z_access_hours")
    private String zAccessHours;
    
    @Column(name = "z_manned")
    private String zManned;
    
    @Column(name = "z_loa_required")
    private String zLoaRequired;
    
    @Column(name = "z_inside_wiring_required")
    private String zInsideWiringRequired;
    
    @Column(name = "z_handoff_media_type")
    private String zHandoffMediaType;
    
    @Column(name = "z_handoff_fiber_mode")
    private String zHandoffFiberMode;
    
    @Column(name = "z_handoff_connector_type")
    private String zHandoffConnectorType;
    
    @Column(name = "z_clli_code")
    private String zClliCode;
    
    @Column(name = "z_pop_clli")
    private String zPopClli;
    
    @Column(name = "z_alternate_pop_clli")
    private String zAlternatePopClli;
    
    @Column(name = "z_floor")
    private String zFloor;
    
    @Column(name = "z_npa_nxx")
    private String zNpaNxx;
    
    @Column(name = "z_dmarc")
    private String zDmarc;
    
    @Column(name = "z_cfa")
    private String zCfa;

    @Column(name = "access_speed")
    private String accessSpeed;

    @Column(name = "z_address_1")
    private String zAddress1;

    @Column(name = "z_address_2")
    private String zAddress2;

    @Column(name = "z_city")
    private String zCity;

    @Column(name = "z_state_province")
    private String zState;

    @Column(name = "z_postal_code")
    private String zPostalCode;

    @Column(name = "z_country")
    private String zCountry;
    
    @PrePersist
    void prePersist() {
        setType(ServiceType.ETHERNET.getServiceName());
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(final String productType) {
        this.productType = productType;
    }

    public String getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed(final String portSpeed) {
        this.portSpeed = portSpeed;
    }

    public String getEaSpeed() {
        return eaSpeed;
    }

    public void setEaSpeed(final String eaSpeed) {
        this.eaSpeed = eaSpeed;
    }

    public String getMtu() {
        return mtu;
    }

    public void setMtu(final String mtu) {
        this.mtu = mtu;
    }

    public String getMux() {
        return mux;
    }

    public void setMux(final String mux) {
        this.mux = mux;
    }

    public String getVlanForEline() {
        return vlanForEline;
    }

    public void setVlanForEline(final String vlanForEline) {
        this.vlanForEline = vlanForEline;
    }

    public String getVlanTagging() {
        return vlanTagging;
    }

    public void setVlanTagging(final String vlanTagging) {
        this.vlanTagging = vlanTagging;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(final String vlanId) {
        this.vlanId = vlanId;
    }

    public String getCableCategory() {
        return cableCategory;
    }

    public void setCableCategory(final String cableCategory) {
        this.cableCategory = cableCategory;
    }

    public String getCableShielding() {
        return cableShielding;
    }

    public void setCableShielding(final String cableShielding) {
        this.cableShielding = cableShielding;
    }

    public String getDataCenterName() {
        return dataCenterName;
    }

    public void setDataCenterName(final String dataCenterName) {
        this.dataCenterName = dataCenterName;
    }

    public String getProviderCircuitId() {
        return providerCircuitId;
    }

    public void setProviderCircuitId(final String providerCircuitId) {
        this.providerCircuitId = providerCircuitId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(final String accessType) {
        this.accessType = accessType;
    }

    public String getInterfaceConnector() {
        return interfaceConnector;
    }

    public void setInterfaceConnector(final String interfaceConnector) {
        this.interfaceConnector = interfaceConnector;
    }

    public String getAccessHours() {
        return accessHours;
    }

    public void setAccessHours(final String accessHours) {
        this.accessHours = accessHours;
    }

    public String getManned() {
        return manned;
    }

    public void setManned(final String manned) {
        this.manned = manned;
    }

    public String getLoaRequired() {
        return loaRequired;
    }

    public void setLoaRequired(final String loaRequired) {
        this.loaRequired = loaRequired;
    }

    public String getHandoffFiberMode() {
        return handoffFiberMode;
    }

    public void setHandoffFiberMode(final String handoffFiberMode) {
        this.handoffFiberMode = handoffFiberMode;
    }

    public String getHandoffConnectorType() {
        return handoffConnectorType;
    }

    public void setHandoffConnectorType(final String handoffConnectorType) {
        this.handoffConnectorType = handoffConnectorType;
    }

    public String getClliCode() {
        return clliCode;
    }

    public void setClliCode(final String clliCode) {
        this.clliCode = clliCode;
    }

    public String getPopClli() {
        return popClli;
    }

    public void setPopClli(final String popClli) {
        this.popClli = popClli;
    }

    public String getAlternatePopClli() {
        return alternatePopClli;
    }

    public void setAlternatePopClli(final String alternatePopClli) {
        this.alternatePopClli = alternatePopClli;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(final String floor) {
        this.floor = floor;
    }

    public String getNpaNxx() {
        return npaNxx;
    }

    public void setNpaNxx(final String npaNxx) {
        this.npaNxx = npaNxx;
    }

    public String getCfa() {
        return cfa;
    }

    public void setCfa(final String cfa) {
        this.cfa = cfa;
    }

    public String getzDataCenterName() {
        return zDataCenterName;
    }

    public void setzDataCenterName(final String zDataCenterName) {
        this.zDataCenterName = zDataCenterName;
    }

    public String getzProviderCircuitId() {
        return zProviderCircuitId;
    }

    public void setzProviderCircuitId(final String zProviderCircuitId) {
        this.zProviderCircuitId = zProviderCircuitId;
    }

    public String getzBuildingStatus() {
        return zBuildingStatus;
    }

    public void setzBuildingStatus(final String zBuildingStatus) {
        this.zBuildingStatus = zBuildingStatus;
    }

    public String getzAccessType() {
        return zAccessType;
    }

    public void setzAccessType(final String zAccessType) {
        this.zAccessType = zAccessType;
    }

    public String getzInterfaceConnector() {
        return zInterfaceConnector;
    }

    public void setzInterfaceConnector(final String zInterfaceConnector) {
        this.zInterfaceConnector = zInterfaceConnector;
    }

    public String getzAccessHours() {
        return zAccessHours;
    }

    public void setzAccessHours(final String zAccessHours) {
        this.zAccessHours = zAccessHours;
    }

    public String getzManned() {
        return zManned;
    }

    public void setzManned(final String zManned) {
        this.zManned = zManned;
    }

    public String getzLoaRequired() {
        return zLoaRequired;
    }

    public void setzLoaRequired(final String zLoaRequired) {
        this.zLoaRequired = zLoaRequired;
    }

    public String getzInsideWiringRequired() {
        return zInsideWiringRequired;
    }

    public void setzInsideWiringRequired(final String zInsideWiringRequired) {
        this.zInsideWiringRequired = zInsideWiringRequired;
    }

    public String getzHandoffMediaType() {
        return zHandoffMediaType;
    }

    public void setzHandoffMediaType(final String zHandoffMediaType) {
        this.zHandoffMediaType = zHandoffMediaType;
    }

    public String getzHandoffFiberMode() {
        return zHandoffFiberMode;
    }

    public void setzHandoffFiberMode(final String zHandoffFiberMode) {
        this.zHandoffFiberMode = zHandoffFiberMode;
    }

    public String getzHandoffConnectorType() {
        return zHandoffConnectorType;
    }

    public void setzHandoffConnectorType(final String zHandoffConnectorType) {
        this.zHandoffConnectorType = zHandoffConnectorType;
    }

    public String getzClliCode() {
        return zClliCode;
    }

    public void setzClliCode(final String zClliCode) {
        this.zClliCode = zClliCode;
    }

    public String getzPopClli() {
        return zPopClli;
    }

    public void setzPopClli(final String zPopClli) {
        this.zPopClli = zPopClli;
    }

    public String getzAlternatePopClli() {
        return zAlternatePopClli;
    }

    public void setzAlternatePopClli(final String zAlternatePopClli) {
        this.zAlternatePopClli = zAlternatePopClli;
    }

    public String getzFloor() {
        return zFloor;
    }

    public void setzFloor(final String zFloor) {
        this.zFloor = zFloor;
    }

    public String getzNpaNxx() {
        return zNpaNxx;
    }

    public void setzNpaNxx(final String zNpaNxx) {
        this.zNpaNxx = zNpaNxx;
    }

    public String getzDmarc() {
        return zDmarc;
    }

    public void setzDmarc(final String zDmarc) {
        this.zDmarc = zDmarc;
    }

    public String getzCfa() {
        return zCfa;
    }

    public void setzCfa(final String zCfa) {
        this.zCfa = zCfa;
    }

    public String getAccessSpeed() {
        return accessSpeed;
    }

    public void setAccessSpeed(final String accessSpeed) {
        this.accessSpeed = accessSpeed;
    }

    public String getzAddress1() {
        return zAddress1;
    }

    public void setzAddress1(final String zAddress1) {
        this.zAddress1 = zAddress1;
    }

    public String getzAddress2() {
        return zAddress2;
    }

    public void setzAddress2(final String zAddress2) {
        this.zAddress2 = zAddress2;
    }

    public String getzCity() {
        return zCity;
    }

    public void setzCity(final String zCity) {
        this.zCity = zCity;
    }

    public String getzState() {
        return zState;
    }

    public void setzState(final String zState) {
        this.zState = zState;
    }

    public String getzPostalCode() {
        return zPostalCode;
    }

    public void setzPostalCode(final String zPostalCode) {
        this.zPostalCode = zPostalCode;
    }

    public String getzCountry() {
        return zCountry;
    }

    public void setzCountry(final String zCountry) {
        this.zCountry = zCountry;
    }
}
