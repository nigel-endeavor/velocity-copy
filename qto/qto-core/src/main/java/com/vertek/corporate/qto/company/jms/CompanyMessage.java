package com.vertek.corporate.qto.company.jms;

import java.io.Serializable;

public class CompanyMessage implements Serializable {

    private Long companyId;

    private String messageType;

    public CompanyMessage(final Long companyId, final String messageType) {
        this.companyId = companyId;
        this.messageType = messageType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

}
