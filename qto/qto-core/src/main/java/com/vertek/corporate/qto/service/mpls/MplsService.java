package com.vertek.corporate.qto.service.mpls;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 5/17/2024
 */
@Entity
@Table(name = "mpls_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class MplsService extends Service {

    @Column(name = "last_mile_provider")
    private String lastMileProvider;

    @Column(name = "access_circuit_id")
    private String accessCircuitId;

    @Column(name = "port_circuit_id")
    private String portCircuitId;

    @Column(name = "mpls_type")
    private String mplsType;

    @Column(name = "port_speed")
    private String portSpeed;

    @Column(name = "interface_connector")
    private String interfaceConnector;

    @Column(name = "provider_activation_method")
    private String providerActivationMethod;

    @Column(name = "npa_nxx")
    private String npaNxx;

    @Column(name = "routing_protocol")
    private String routingProtocol;

    @Column(name = "cer_ips")
    private String cerIps;

    @Column(name = "per_ips")
    private String perIps;

    @Column(name = "vlan_tag_1")
    private String vlanTag1;

    @Column(name = "vlan_tag_2")
    private String vlanTag2;

    @Column(name = "vlan_tag_3")
    private String vlanTag3;

    @Column(name = "vlan_tag_4")
    private String vlanTag4;

    @Column(name = "other_technical_notes")
    private String otherTechnicalNotes;

    @PrePersist
    void prePersist() {
        setType(ServiceType.MPLS.getServiceName());
    }

    public String getLastMileProvider() {
        return lastMileProvider;
    }

    public void setLastMileProvider(final String lastMileProvider) {
        this.lastMileProvider = lastMileProvider;
    }

    public String getAccessCircuitId() {
        return accessCircuitId;
    }

    public void setAccessCircuitId(final String accessCircuitId) {
        this.accessCircuitId = accessCircuitId;
    }

    public String getPortCircuitId() {
        return portCircuitId;
    }

    public void setPortCircuitId(final String portCircuitId) {
        this.portCircuitId = portCircuitId;
    }

    public String getInterfaceConnector() {
        return interfaceConnector;
    }

    public void setInterfaceConnector(final String interfaceConnector) {
        this.interfaceConnector = interfaceConnector;
    }

    public String getProviderActivationMethod() {
        return providerActivationMethod;
    }

    public void setProviderActivationMethod(final String providerActivationMethod) {
        this.providerActivationMethod = providerActivationMethod;
    }

    public String getMplsType() {
        return mplsType;
    }

    public void setMplsType(final String mplsType) {
        this.mplsType = mplsType;
    }

    public String getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed(final String portSpeed) {
        this.portSpeed = portSpeed;
    }

    public String getNpaNxx() {
        return npaNxx;
    }

    public void setNpaNxx(final String npaNxx) {
        this.npaNxx = npaNxx;
    }

    public String getRoutingProtocol() {
        return routingProtocol;
    }

    public void setRoutingProtocol(final String routingProtocol) {
        this.routingProtocol = routingProtocol;
    }

    public String getCerIps() {
        return cerIps;
    }

    public void setCerIps(final String cerIps) {
        this.cerIps = cerIps;
    }

    public String getPerIps() {
        return perIps;
    }

    public void setPerIps(final String perIps) {
        this.perIps = perIps;
    }

    public String getVlanTag1() {
        return vlanTag1;
    }

    public void setVlanTag1(final String vlanTag1) {
        this.vlanTag1 = vlanTag1;
    }

    public String getVlanTag2() {
        return vlanTag2;
    }

    public void setVlanTag2(final String vlanTag2) {
        this.vlanTag2 = vlanTag2;
    }

    public String getVlanTag3() {
        return vlanTag3;
    }

    public void setVlanTag3(final String vlanTag3) {
        this.vlanTag3 = vlanTag3;
    }

    public String getVlanTag4() {
        return vlanTag4;
    }

    public void setVlanTag4(final String vlanTag4) {
        this.vlanTag4 = vlanTag4;
    }

    public String getOtherTechnicalNotes() {
        return otherTechnicalNotes;
    }

    public void setOtherTechnicalNotes(final String otherTechnicalNotes) {
        this.otherTechnicalNotes = otherTechnicalNotes;
    }
}
