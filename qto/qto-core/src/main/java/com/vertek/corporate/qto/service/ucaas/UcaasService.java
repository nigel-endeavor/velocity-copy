package com.vertek.corporate.qto.service.ucaas;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@Entity
@Table(name = "ucaas_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class UcaasService extends Service {

    @Column(name = "published_tn")
    private String publishedTn;

    @Column(name = "temporary_tn")
    private String temporaryTn;

    @Column(name = "number_of_handsets")
    private Long numberOfHandsets;

    @PrePersist
    void prePersist() {
        setType(ServiceType.UCAAS.getServiceName());
    }

    public String getPublishedTn() {
        return publishedTn;
    }

    public void setPublishedTn(final String publishedTn) {
        this.publishedTn = publishedTn;
    }

    public String getTemporaryTn() {
        return temporaryTn;
    }

    public void setTemporaryTn(final String temporaryTn) {
        this.temporaryTn = temporaryTn;
    }

    public Long getNumberOfHandsets() {
        return numberOfHandsets;
    }

    public void setNumberOfHandsets(final Long numberOfHandsets) {
        this.numberOfHandsets = numberOfHandsets;
    }
}
