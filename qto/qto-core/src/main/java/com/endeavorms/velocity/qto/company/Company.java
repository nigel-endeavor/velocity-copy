package com.endeavorms.velocity.qto.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;
import com.endeavorms.velocity.qto.contact.Contact;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rcasey
 * @since 1/13/2023
 */
@Entity
@Table(name = "company")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_type")
    private String type;

    @Column(name = "company_active")
    private boolean active;

    @Column(name = "company_uuid")
    private String uuid;

    @Column(name = "client_id")
    private String clientId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @Where(clause = "contact_type = 'BILLING'")
    private List<Contact> billingContacts = new ArrayList<>();

    @Column(name="legacy_id")
    private Long legacyId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "master_customer_id", referencedColumnName = "company_id",
            insertable = false, updatable = false)
    private Company parentCompany;

    @Column(name = "inventory_location_count")
    private Long inventoryLocationCount;

    @Column(name = "inventory_mrc")
    private BigDecimal inventoryMrc;

    @Column(name = "inventory_mrr")
    private BigDecimal inventoryMrr;

    @Column(name = "inventory_nrr")
    private BigDecimal inventoryNrr;

    @Column(name = "account_notes")
    private String accountNotes;

    @Column(name = "duplicated_master_customer_details")
    private boolean duplicatedMasterCustomerDetails;

    @Column(name = "account_manager")
    private Long accountManager;

    @Column(name = "provisioner")
    private Long provisioner;

    @Column(name = "i90_project_manager")
    private Long i90ProjectManager;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "task_group_id")
    private Long taskGroupId;

    @Column(name="automate_email_addresses")
    private String automateEmailAddresses;

    @Column(name= "automated_emails_enabled")
    private Boolean automatedEmailsEnabled;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public List<Contact> getBillingContacts() {
        return billingContacts;
    }

    public void setBillingContacts(final List<Contact> billingContacts) {
        this.billingContacts = billingContacts;
    }

    public Contact getBillingContact() {
        if (billingContacts != null && !billingContacts.isEmpty()) {
            return billingContacts.get(0);
        }
        return null;
    }

    public void setBillingContact(final Contact contact) {
        this.billingContacts = Collections.singletonList(contact);
    }

    public Company getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(final Company parentCompany) {
        this.parentCompany = parentCompany;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Long getInventoryLocationCount() {
        return inventoryLocationCount;
    }

    public void setInventoryLocationCount(final Long inventoryLocationCount) {
        this.inventoryLocationCount = inventoryLocationCount;
    }

    public BigDecimal getInventoryMrc() {
        return inventoryMrc;
    }

    public void setInventoryMrc(final BigDecimal inventoryMrc) {
        this.inventoryMrc = inventoryMrc;
    }

    public BigDecimal getInventoryMrr() {
        return inventoryMrr;
    }

    public void setInventoryMrr(final BigDecimal inventoryMrr) {
        this.inventoryMrr = inventoryMrr;
    }

    public BigDecimal getInventoryNrr() {
        return inventoryNrr;
    }

    public void setInventoryNrr(final BigDecimal inventoryNrr) {
        this.inventoryNrr = inventoryNrr;
    }

    public String getAccountNotes() {
        return accountNotes;
    }

    public void setAccountNotes(final String accountNotes) {
        this.accountNotes = accountNotes;
    }

    public boolean isDuplicatedMasterCustomerDetails() {
        return duplicatedMasterCustomerDetails;
    }

    public void setDuplicatedMasterCustomerDetails(final boolean duplicatedMasterCustomerDetails) {
        this.duplicatedMasterCustomerDetails = duplicatedMasterCustomerDetails;
    }

    public Long getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(final Long accountManager) {
        this.accountManager = accountManager;
    }

    public Long getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final Long provisioner) {
        this.provisioner = provisioner;
    }

    public Long getI90ProjectManager() {
        return i90ProjectManager;
    }

    public void setI90ProjectManager(final Long i90ProjectManager) {
        this.i90ProjectManager = i90ProjectManager;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public Long getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(final Long taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public String getAutomateEmailAddresses() {
        return automateEmailAddresses;
    }

    public void setAutomateEmailAddresses(String automateEmailAddresses) {
        this.automateEmailAddresses = automateEmailAddresses;
    }

    public Boolean getAutomatedEmailsEnabled() {
        return automatedEmailsEnabled;
    }

    public void setAutomatedEmailsEnabled(Boolean automatedEmailsEnabled) {
        this.automatedEmailsEnabled = automatedEmailsEnabled;
    }
}
