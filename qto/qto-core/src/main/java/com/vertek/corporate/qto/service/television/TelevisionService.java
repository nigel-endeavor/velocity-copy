package com.vertek.corporate.qto.service.television;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/** A television service. */
@Entity
@Table(name = "television_service")
@PrimaryKeyJoinColumn(name = "service_id")
public class TelevisionService extends Service {

    @Column(name = "plan")
    private String plan;

    @Column(name = "dvr_included")
    private Boolean dvrIncluded;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "receiver_mac")
    private String receiverMac;

    @Column(name = "dvr")
    private String dvr;

    @Column(name = "dvr_mac")
    private String dvrMac;

    @PrePersist
    void prePersist() {
        setType(ServiceType.TELEVISION.getServiceName());
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(final String plan) {
        this.plan = plan;
    }

    public Boolean getDvrIncluded() {
        return dvrIncluded;
    }

    public void setDvrIncluded(final Boolean dvrIncluded) {
        this.dvrIncluded = dvrIncluded;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(final String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverMac() {
        return receiverMac;
    }

    public void setReceiverMac(final String receiverMac) {
        this.receiverMac = receiverMac;
    }

    public String getDvr() {
        return dvr;
    }

    public void setDvr(final String dvr) {
        this.dvr = dvr;
    }

    public String getDvrMac() {
        return dvrMac;
    }

    public void setDvrMac(final String dvrMac) {
        this.dvrMac = dvrMac;
    }
}
