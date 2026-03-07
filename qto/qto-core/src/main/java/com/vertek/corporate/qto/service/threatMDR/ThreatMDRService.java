package com.vertek.corporate.qto.service.threatMDR;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_threatmdr")
@PrimaryKeyJoinColumn(name = "service_id")
public class ThreatMDRService extends Service {

    @Column(name = "sensors")
    private Integer sensors;

    @Column(name="hot_storage_retention")
    private String hotStorageRetention;

    @Column(name = "usm_anywhere_control_node")
    private String usmAnywhereControlNode;

    @Column(name = "tier_expansion_storage_size")
    private String tierExpansionStorageSize;

    @Column(name = "log_retention_requirement")
    private String logRetentionRequirement;

    @PrePersist
    void prePersist() {
        setType(ServiceType.THREATMDR.getServiceName());
    }

    public Integer getSensors() {
        return sensors;
    }

    public void setSensors(final Integer sensors) {
        this.sensors = sensors;
    }

    public String getHotStorageRetention() {
        return hotStorageRetention;
    }

    public void setHotStorageRetention(final String hotStorageRetention) {
        this.hotStorageRetention = hotStorageRetention;
    }

    public String getUsmAnywhereControlNode() {
        return usmAnywhereControlNode;
    }

    public void setUsmAnywhereControlNode(final String usmAnywhereControlNode) {
        this.usmAnywhereControlNode = usmAnywhereControlNode;
    }

    public String getTierExpansionStorageSize() {
        return tierExpansionStorageSize;
    }

    public void setTierExpansionStorageSize(final String tierExpansionStorageSize) {
        this.tierExpansionStorageSize = tierExpansionStorageSize;
    }

    public String getLogRetentionRequirement() {
        return logRetentionRequirement;
    }

    public void setLogRetentionRequirement(final String logRetentionRequirement) {
        this.logRetentionRequirement = logRetentionRequirement;
    }
}
