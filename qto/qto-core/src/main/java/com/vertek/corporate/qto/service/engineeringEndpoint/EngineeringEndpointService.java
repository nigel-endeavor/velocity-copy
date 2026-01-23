package com.vertek.corporate.qto.service.engineeringEndpoint;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "engineering_endpoint")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringEndpointService extends Service {

    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_ENDPOINT.getServiceName());
    }
}
