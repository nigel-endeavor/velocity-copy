package com.vertek.corporate.qto.invocing.jms;

import java.io.Serializable;

public class InvoiceChargeMessage implements Serializable {

    /** Invoice id. */
    private Long invoiceId;

    /** Subject id. */
    private Long subjectId;

    /** Message type. */
    private String messageType;

    /**
     * Constructor.
     * @param invoiceId invoice ID
     * @param messageType message type
     */
    public InvoiceChargeMessage(final Long invoiceId, final Long subjectId, final String messageType) {
        this.invoiceId = invoiceId;
        this.subjectId = subjectId;
        this.messageType = messageType;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "InvoiceChargeMessage{"
                + "invoiceId=" + invoiceId
                + ", subjectId='" + subjectId + '\''
                + ", messageType='" + messageType + '\''
                + '}';
    }
}
