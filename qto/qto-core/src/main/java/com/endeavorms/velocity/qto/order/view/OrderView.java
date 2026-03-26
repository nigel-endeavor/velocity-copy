package com.endeavorms.velocity.qto.order.view;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Read-only flat entity backed by the v_manage_orders database view.
 * Returns exactly the fields needed for the orders list page in a single SQL query,
 * avoiding the EAGER Location → Service chain that caused the original load delay.
 */
@Entity
@Table(name = "v_manage_orders")
public class OrderView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @Column(name = "order_id")
    private Long id;

    @Column(name = "client_order_id")
    private String clientOrderId;

    @Column(name = "order_status")
    private String status;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "vertek_client")
    private String vertekClient;

    @Column(name = "location_count")
    private Integer locationCount;

    @Column(name = "mrc")
    private BigDecimal mrc;

    @Column(name = "nrc")
    private BigDecimal nrc;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "quote_id")
    private String quoteId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(final String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getVertekClient() {
        return vertekClient;
    }

    public void setVertekClient(final String vertekClient) {
        this.vertekClient = vertekClient;
    }

    public Integer getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(final Integer locationCount) {
        this.locationCount = locationCount;
    }

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getNrc() {
        return nrc;
    }

    public void setNrc(final BigDecimal nrc) {
        this.nrc = nrc;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(final Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(final String quoteId) {
        this.quoteId = quoteId;
    }
}
