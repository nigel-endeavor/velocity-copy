package com.vertek.corporate.qto.service.riskMDR;

import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "service_riskmdr")
@PrimaryKeyJoinColumn(name = "service_id")
public class RiskMDRService extends Service {

    @Column(name = "number_of_assets")
    private String numberOfAssets;

    @Column(name = "number_of_ips")
    private String numberOfIPs;

    @Column(name = "ip_technical_notes")
    private String ipTechnicalNotes;

    @PrePersist
    void prePersist() {
        setType(ServiceType.RISKMDR.getServiceName());
    }

    public String getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(final String numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public String getNumberOfIPs() {
        return numberOfIPs;
    }

    public void setNumberOfIPs(final String numberOfIPs) {
        this.numberOfIPs = numberOfIPs;
    }

    public String getIPTechnicalNotes() {
        return ipTechnicalNotes;
    }

    public void setIPTechnicalNotes(final String IPTechnicalNotes) {
        this.ipTechnicalNotes = IPTechnicalNotes;
    }
}
