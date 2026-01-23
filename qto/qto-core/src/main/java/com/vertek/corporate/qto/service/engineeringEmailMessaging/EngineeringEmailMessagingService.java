package com.vertek.corporate.qto.service.engineeringEmailMessaging;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "engineering_email_messaging")
@PrimaryKeyJoinColumn(name = "service_id")
public class EngineeringEmailMessagingService extends Service {
    @PrePersist
    void prePersist() {
        setType(ServiceType.ENGINEERING_EMAIL_MESSAGING.getServiceName());
    }
}
