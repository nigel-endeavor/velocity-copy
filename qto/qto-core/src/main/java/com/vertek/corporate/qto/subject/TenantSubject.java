package com.vertek.corporate.qto.subject;

import com.google.common.base.MoreObjects;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;
import com.vertek.corporate.qto.common.Tenant;
import com.vertek.corporate.qto.subject.Subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * From platform.
 * Models the association between a Subject and a Tenant.
 * @author mmeehan
 * @since 1.5.0 - 11/27/12 10:56 PM
 */
@Entity
@Table(name = "tenant_subject", schema = "platform")
public class TenantSubject extends StandardVersionedBaseEntity {

    /** Unique ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_subject_id")
    private Long id;

    /** Tenant. */
    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id")
    protected Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "subject_id")
    private Subject subject;

    /** Flag to indicate whether this is the selected tenant for the user. */
    @Column(name = "tenant_subject_selected")
    private Boolean isSelected;

    @Override
    public Long getId() {
        return id;
    }


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(final Subject subject) {
        this.subject = subject;
    }

    public Tenant getTenant() {
        return tenant;
    }
    public void setTenant(final Tenant tenant) {
        this.tenant = tenant;
    }


    public Boolean isSelected() {
        return isSelected;
    }
    public void setIsSelected(final Boolean isSelected) {
        this.isSelected = isSelected;
    }



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("subject", subject)
                .add("tenant", tenant)
                .add("isSelected", isSelected)
                .toString();
    }
}
