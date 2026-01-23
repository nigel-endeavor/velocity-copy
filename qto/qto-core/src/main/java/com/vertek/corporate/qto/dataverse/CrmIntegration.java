package com.vertek.corporate.qto.dataverse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "crm_integration")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrmIntegration extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crm_integration_id")
    private Long id;

    @Column(name="opportunity_num")
    private String opportunityNum;

    @Column(name="response")
    private String response;

    @Column(name="request_date")
    private Date requestDate;


    @Override
    public Long getId() {
        return id;
    }

    public String getOpportunityNum() {
        return opportunityNum;
    }

    public void setOpportunityNum(final String opportunityNum) {
        this.opportunityNum = opportunityNum;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(final String response) {
        this.response = response;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(final Date requestDate) {
        this.requestDate = requestDate;
    }
}
