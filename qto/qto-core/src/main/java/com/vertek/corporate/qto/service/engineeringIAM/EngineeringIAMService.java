package com.vertek.corporate.qto.service.engineeringIAM;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "engineering_iam")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringIAMService extends Service {

    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_IAM.getServiceName());
    }
}

