package com.endeavorms.velocity.qto.service.crossconnect;

import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * Provides classes related to the 'service' entity for Cross Connect (XC).
 * @author fcurran
 * @since 1/12/2024
 */
@Entity
@Table(name = "cross_connect_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class CrossConnectService extends Service {
    /** Provider unique ID assigned  to the connection. */
    @Column(name = "cross_connect_id")
    private String crossConnectId;
    /** Room Identifier where the physical Cross Connect exists. */
    @Column(name = "cross_connect_room")
    private String crossConnectRoom;
    /** Rack Identifier for the cross connect. */
    @Column(name = "cross_connect_rack")
    private String crossConnectRack;
    /** Port Identifier for the Cross Connect. */
    @Column(name = "cross_connect_port")
    private String crossConnectPort;
    /** Media type of Cross connect. */
    @Column(name = "cross_connect_type")
    private String crossConnectType;
    /** Name of the Data Center or colocation point the cross connect is located. */
    @Column(name = "cross_connect_data_center_name")
    private String crossConnectDataCenterName;

    @PrePersist
    void prePersist() {
        setType(ServiceType.CROSSCONNECT.getServiceName());
    }

    public String getCrossConnectId() {
        return crossConnectId;
    }

    public void setCrossConnectId(final String crossConnectId) {
        this.crossConnectId = crossConnectId;
    }

    public String getCrossConnectRoom() {
        return crossConnectRoom;
    }

    public void setCrossConnectRoom(final String crossConnectRoom) {
        this.crossConnectRoom = crossConnectRoom;
    }

    public String getCrossConnectRack() {
        return crossConnectRack;
    }

    public void setCrossConnectRack(final String crossConnectRack) {
        this.crossConnectRack = crossConnectRack;
    }

    public String getCrossConnectPort() {
        return crossConnectPort;
    }

    public void setCrossConnectPort(final String crossConnectPort) {
        this.crossConnectPort = crossConnectPort;
    }

    public String getCrossConnectType() {
        return crossConnectType;
    }

    public void setCrossConnectType(final String crossConnectType) {
        this.crossConnectType = crossConnectType;
    }

    public String getCrossConnectDataCenterName() {
        return crossConnectDataCenterName;
    }

    public void setCrossConnectDataCenterName(final String crossConnectDataCenterName) {
        this.crossConnectDataCenterName = crossConnectDataCenterName;
    }
}
