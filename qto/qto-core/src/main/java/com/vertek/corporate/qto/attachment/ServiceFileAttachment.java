package com.vertek.corporate.qto.attachment;

import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Entity
@Table(name = "service_file_attachment")
public class ServiceFileAttachment extends FileAttachment {

    @Column(name = "service_id")
    private Long serviceId;

    @Formula("(SELECT CONCAT(s.service_type, IF(s.provider IS NULL OR s.provider = '', '', CONCAT(': ', s.provider))) FROM service s WHERE s.service_id = service_id)")
    private String serviceDisplayText;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDisplayText() {
        return serviceDisplayText;
    }

    public void setServiceDisplayText(final String serviceDisplayText) {
        this.serviceDisplayText = serviceDisplayText;
    }

}
