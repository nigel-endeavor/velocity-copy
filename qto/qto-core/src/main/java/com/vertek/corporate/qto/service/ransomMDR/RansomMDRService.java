package com.vertek.corporate.qto.service.ransomMDR;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_ransommdr")
@PrimaryKeyJoinColumn(name = "service_id")
public class RansomMDRService extends Service {

    @Column(name = "agent_deployment")
    private String agentDeployment;

    @PrePersist
    void prePersist() {
        setType(ServiceType.RANSOMMDR.getServiceName());
    }

    public String getAgentDeployment() {
        return agentDeployment;
    }

    public void setAgentDeployment(final String agentDeployment) {
        this.agentDeployment = agentDeployment;
    }
}