package com.vertek.corporate.qto.service.ransomMDR;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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