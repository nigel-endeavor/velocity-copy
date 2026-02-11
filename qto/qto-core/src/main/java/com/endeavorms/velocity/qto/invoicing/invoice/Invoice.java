package com.endeavorms.velocity.qto.invoicing.invoice;

import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mwelicka
 * @since 7/30/2023
 */
@Entity
@Table(name = "invoice")
public class Invoice extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_status")
    private String invoiceStatus;

    @Column(name = "total_charges")
    private BigDecimal totalCharges;

    @Column(name = "invoice_start")
    private Date invoiceStart;

    @Column(name = "invoice_end")
    private Date invoiceEnd;

    @Column(name = "generated_by")
    private String generatedBy;

    @Column(name = "generated_date")
    private Date generatedDate;

    @Override
    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(final String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public BigDecimal getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(final BigDecimal totalCharges) {
        this.totalCharges = totalCharges;
    }

    public Date getInvoiceStart() {
        return invoiceStart;
    }

    public void setInvoiceStart(final Date invoiceStart) {
        this.invoiceStart = invoiceStart;
    }

    public Date getInvoiceEnd() {
        return invoiceEnd;
    }

    public void setInvoiceEnd(final Date invoiceEnd) {
        this.invoiceEnd = invoiceEnd;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(final String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public Date getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(final Date generatedDate) {
        this.generatedDate = generatedDate;
    }
}
