package com.vertek.corporate.qto.service.engineeringInfoProtection;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "engineering_info_protection")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringInfoProtectionService extends Service {

    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_INFO_PROTECTION.getServiceName());
    }
}
