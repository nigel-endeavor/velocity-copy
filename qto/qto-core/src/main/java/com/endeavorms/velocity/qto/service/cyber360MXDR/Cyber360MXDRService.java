package com.endeavorms.velocity.qto.service.cyber360MXDR;

import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceType;

import jakarta.persistence.*;

@Entity
@Table(name = "service_cyber360_mxdr")
@PrimaryKeyJoinColumn(name = "service_id")
public class Cyber360MXDRService extends Service {

    @Column(name = "number_of_endpoints_devices")
    private String numberOfEndpointsDevices;

    @PrePersist
    void prePersist() { setType(ServiceType.CYBER360MXDR.getServiceName());}

    public String getNumberOfEndpointsDevices() {
        return numberOfEndpointsDevices;
    }

    public void setNumberOfEndpointsDevices(String numberOfEndpointsDevices) {
        this.numberOfEndpointsDevices = numberOfEndpointsDevices;
    }
}
