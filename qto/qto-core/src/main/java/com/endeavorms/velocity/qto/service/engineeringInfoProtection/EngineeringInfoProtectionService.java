package com.endeavorms.velocity.qto.service.engineeringInfoProtection;

import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceType;

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
