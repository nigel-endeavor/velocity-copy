package com.endeavorms.velocity.qto.common.lookup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Models a Looked up value.
 *
 * @author rconnolly
 * @since 1.0
 */
@Entity
@Table(name = "lookup_value")
public class LookupValue extends AbstractTenantOwnedEntity {

    /** System identifier.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lookup_value_id")
    private Long id;

    @Column(name = "lookup_display")
    private String display;

    /** The value which should be used for back-end processing. */
    @Column(name = "lookup_value")
    private String value;

    /** Flag indicating whether this value is active or not.*/
    @Column(name = "lookup_value_active", columnDefinition = "bit default 1")
    private boolean active;

    /** A sequence used for sorting.*/
    @Column(name = "sort_seq")
    private Integer sortSequence;

    /** The owning LookupType. */
    @ManyToOne
    @JoinColumn(name = "lookup_type_id")
    @JsonIgnore
    private LookupType lookupType;

    /** Parent lookup value id. */
    @Column(name = "parent_lookup_value_id")
    private Long parentId;

    @Override
    public Long getId() {
        return id;
    }


    public String getDisplay() {
        return display;
    }
    public void setDisplay(final String display) {
        this.display = display;
    }


    public String getValue() {
        return value;
    }
    public void setValue(final String value) {
        this.value = value;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }


    public Integer getSortSequence() {
        return sortSequence;
    }
    public void setSortSequence(final Integer sortSequence) {
        this.sortSequence = sortSequence;
    }


    public LookupType getLookupType() {
        return lookupType;
    }
    public void setLookupType(final LookupType lookupType) {
        this.lookupType = lookupType;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }
}