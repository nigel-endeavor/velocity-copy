package com.vertek.corporate.qto.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import com.vertek.corporate.qto.common.SecurityUtils;
import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * @author rcasey
 * @since 1/13/2023
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "contact")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "contact_active")
    private boolean active;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    private ContactType type;

    /** Lookup value CONTACT_ROLE. */
    @Column(name = "role")
    private String role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Formula("(SELECT CASE " +
                "WHEN contact_type = 'LCON' THEN 1 " +
                "ELSE 0 END)")
    private int sortOrder;

    @Column(name = "parent_contact_id")
    private Long parentContactId;

    @PrePersist
    @PreUpdate
    void preUpdate() {
        setLastUpdateDate(new Date());
        setLastUpdateBy(SecurityUtils.getLoggedInUser());
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(final String notes) {
        this.notes = notes;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(final ContactType type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
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

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Long getParentContactId() {
        return parentContactId;
    }

    public void setParentContactId(final Long parentContactId) {
        this.parentContactId = parentContactId;
    }
}
