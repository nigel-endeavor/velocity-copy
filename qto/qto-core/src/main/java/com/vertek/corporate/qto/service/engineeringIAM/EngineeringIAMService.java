package com.vertek.corporate.qto.service.engineeringIAM;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "engineering_iam")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringIAMService extends Service {

    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_IAM.getServiceName());
    }
}

