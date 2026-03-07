
package com.vertek.corporate.qto.quote;

import com.vertek.corporate.qto.common.StandardBaseEntity;
import com.vertek.corporate.qto.solution.Solution;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Standard representation of a vendor's quote.
 * @author fcurran
 * @since 1.0.0
 */
@Entity
@Table(name = "quote")
public class Quote extends StandardBaseEntity {
    /** ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quote_id")
    private Long id;
    /** quoteId. */
    @Column(name = "vendor_quote_id")
    private String vendorQuoteId;
    /** accountId. */
    @Column(name = "account_id")
    private Integer accountId;
    /** accountName. */
    @Column(name = "account_name")
    private String accountName;
    /** userEmail. */
    @Column(name = "user_email")
    private String userEmail;
    /** userName. */
    @Column(name = "user_name")
    private String userName;
    /** orderId. */
    @Column(name = "order_id")
    private Long orderId;
    /** so. */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "quote")
    private List<Solution> solutions;
    /** quoteNumber. */
    @Column(name = "quote_number")
    private String quoteNumber;
    /** The original payload for reference. */
    @Column(name = "quote_string")
    private String quoteString;
    /** Any noted additional properties. */
    @Column(name = "additional_properties")
    private String additionalProperties;
    /** When the solution had finished being process. */
    @Column(name = "quote_handled_time")
    private Date handledTime;
    /** The tenant the quote order came from. */
    @Column(name = "tenant_id")
    private Long tenantId;
    /** The quoting provider the quote came from. */
    @Column(name = "quote_provider")
    private String quoteProvider;

    @Override
    public Long getId() {
        return id;
    }

    public String getVendorQuoteId() {
        return vendorQuoteId;
    }

    public void setVendorQuoteId(final String vendorQuoteId) {
        this.vendorQuoteId = vendorQuoteId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(final Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(final String accountName) {
        this.accountName = accountName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(final String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(final List<Solution> solutions) {
        this.solutions = solutions;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public void setQuoteNumber(final String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getQuoteString() {
        return quoteString;
    }

    public void setQuoteString(final String quoteString) {
        this.quoteString = quoteString;
    }

    public String getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(final String additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Date getHandledTime() {
        return handledTime;
    }

    public void setHandledTime(final Date handledTime) {
        this.handledTime = handledTime;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getQuoteProvider() {
        return quoteProvider;
    }

    public void setQuoteProvider(final String quoteProvider) {
        this.quoteProvider = quoteProvider;
    }
}
