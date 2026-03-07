package com.vertek.corporate.qto.service.engineeringInfoProtection;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "engineering_info_protection")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringInfoProtectionService extends Service {

    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_INFO_PROTECTION.getServiceName());
    }
}
