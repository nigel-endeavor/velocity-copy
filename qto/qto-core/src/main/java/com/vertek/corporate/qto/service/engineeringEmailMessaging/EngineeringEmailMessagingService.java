package com.vertek.corporate.qto.service.engineeringEmailMessaging;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "engineering_email_messaging")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringEmailMessagingService extends Service {
    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_EMAIL_MESSAGING.getServiceName());
    }
}
