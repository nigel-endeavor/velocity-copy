package com.vertek.corporate.qto.service.engineeringEndpoint;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "engineering_endpoint")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringEndpointService extends Service {

    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_ENDPOINT.getServiceName());
    }
}
