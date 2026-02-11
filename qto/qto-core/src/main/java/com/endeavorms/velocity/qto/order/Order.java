package com.endeavorms.velocity.qto.order;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.contact.order.OrderContact;
import com.endeavorms.velocity.qto.location.Location;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Entity
@Table(name = "orders")
public class Order extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "client_order_id")
    private String clientOrderId;

    @Column(name = "quote_id")
    private String quoteId;

    @Column(name = "provisioner")
    private Long provisioner;

    @Column(name = "client_project_manager")
    private String clientProjectManager;

    @Column(name = "vertek_project_manager")
    private Long vertekProjectManager;

    @Column(name = "activation_engineer")
    private Long activationEngineer;

    @Column(name = "qa_manager")
    private Long qaManager;

    @Transient
    private BigDecimal mrc;

    @Transient
    private BigDecimal nrc;

    @Transient
    private BigDecimal mrr;

    @Transient
    private BigDecimal nrr;

    @Transient
    private BigDecimal icb;

    @Transient
    private BigDecimal osp;

    @Transient
    private BigDecimal annualRecurringCost;

    @Column(name = "order_status")
    private String status;

    /** The currency indicated in the source quote. */
    @Column(name = "quote_number")
    private String quoteNumber;

    @Column(name="legacy_id")
    private Long legacyId;



    @Column(name = "inventory_order_id")
    private Long inventoryOrderId;

    @Column(name = "provisioning_order_id")
    private Long provisioningOrderId;

    @Column(name = "current_inventory")
    private boolean isCurrentInventory;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @Where(clause = "marked_for_deletion = false")
    @OrderBy("sortOrder ASC, id ASC")
    private List<Location> locations = new ArrayList<>();

    @Transient
    private List<OrderContact> contacts;

    @Formula("(select c.company_name from company c "
            + "where c.tenant_id = tenant_id and c.company_type = 'Vertek Client')")
    private String vertekClient;

    /** Creation date.*/
    @Formula("(Select mi.milestone_date from order_milestone_instance om "
            + " left join milestone_instance mi on om.milestone_instance_id = mi.milestone_instance_id "
            + " left join milestone m on mi.milestone_id = m.milestone_id "
            + " where m.milestone_code = 'CREATED' and mi.historic = 0 "
            + "and om.order_id = order_id "
            + " limit 1)")
    private Date createdDate;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "final_update")
    private boolean finalUpdate;

    @Column(name = "eligible_for_inventory")
    private boolean eligibleForInventory;

    @PrePersist
    @PreUpdate
    void preUpdate() {
        setLastUpdateDate(new Date());
        setLastUpdateBy(SecurityUtils.getLoggedInUser());
    }

    @PostLoad
    void postLoad() {
        this.mrc = locations.stream().map(Location::getMrc).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.nrc = locations.stream().map(Location::getNrc).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.mrr = locations.stream().map(Location::getMrr).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.nrr = locations.stream().map(Location::getNrr).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.icb = locations.stream().map(Location::getIcb).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.osp = locations.stream().map(Location::getOsp).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.annualRecurringCost = locations.stream().map(Location::getAnnualRecurringCost).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long getId() {
        return id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(final Company company) {
        this.company = company;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(final String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(final String quoteId) {
        this.quoteId = quoteId;
    }

    public Long getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final Long provisioner) {
        this.provisioner = provisioner;
    }

    public String getClientProjectManager() {
        return clientProjectManager;
    }

    public void setClientProjectManager(final String clientProjectManager) {
        this.clientProjectManager = clientProjectManager;
    }

    public Long getVertekProjectManager() {
        return vertekProjectManager;
    }

    public void setVertekProjectManager(final Long vertekProjectManager) {
        this.vertekProjectManager = vertekProjectManager;
    }

    public Long getActivationEngineer() {
        return activationEngineer;
    }

    public void setActivationEngineer(final Long activationEngineer) {
        this.activationEngineer = activationEngineer;
    }

    public Long getQaManager() {
        return qaManager;
    }

    public void setQaManager(final Long qaManager) {
        this.qaManager = qaManager;
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

    public BigDecimal getMrr() {
        return mrr;
    }

    public void setMrr(final BigDecimal mrr) {
        this.mrr = mrr;
    }

    public BigDecimal getNrr() {
        return nrr;
    }

    public void setNrr(final BigDecimal nrr) {
        this.nrr = nrr;
    }

    public BigDecimal getIcb() {
        return icb;
    }

    public void setIcb(final BigDecimal icb) {
        this.icb = icb;
    }

    public BigDecimal getOsp() {
        return osp;
    }

    public void setOsp(final BigDecimal osp) {
        this.osp = osp;
    }

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(final List<Location> locations) {
        this.locations = locations;
    }

    public List<OrderContact> getContacts() {
        return contacts;
    }

    public void setContacts(final List<OrderContact> contacts) {
        this.contacts = contacts;
    }

    public String getVertekClient() {
        return vertekClient;
    }

    public String getQuoteNumber() {
        return quoteNumber;
    }

    public void setQuoteNumber(final String quoteNumber) {
        this.quoteNumber = quoteNumber;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(final String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(final Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Long getInventoryOrderId() {
        return inventoryOrderId;
    }

    public void setInventoryOrderId(final Long inventoryOrderId) {
        this.inventoryOrderId = inventoryOrderId;
    }

    public Long getProvisioningOrderId() {
        return provisioningOrderId;
    }

    public void setProvisioningOrderId(final Long provisioningOrderId) {
        this.provisioningOrderId = provisioningOrderId;
    }

    public boolean isCurrentInventory() {
        return isCurrentInventory;
    }

    public void setCurrentInventory(final boolean isCurrentInventory) {
        this.isCurrentInventory = isCurrentInventory;
    }

    public boolean getFinalUpdate() {
        return finalUpdate;
    }

    public void setFinalUpdate(final boolean finalUpdate) {
        this.finalUpdate = finalUpdate;
    }

    public boolean isEligibleForInventory() {
        return eligibleForInventory;
    }

    public void setEligibleForInventory(final boolean eligibleForInventory) {
        this.eligibleForInventory = eligibleForInventory;
    }
}
