package com.endeavorms.velocity.qto.service.engineeringIAM;

import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceType;

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

