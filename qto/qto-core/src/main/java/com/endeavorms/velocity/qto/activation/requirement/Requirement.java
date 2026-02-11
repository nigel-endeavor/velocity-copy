package com.endeavorms.velocity.qto.activation.requirement;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;
import com.endeavorms.velocity.qto.common.lookup.LookupValue;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author rcasey 
 * @since 3/3/2023
 */
@Entity
@Table(name = "requirement")
public class Requirement extends StandardVersionedBaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long id;

    @Column(name = "requirement_template_id")
    private Long requirementTemplateId;

    @OneToOne
    @JoinColumn(name = "lookup_value_id")
    private LookupValue lookupValue;

    @Column(name = "sort_order")
    private Long sortOrder;

    @Column(name = "required")
    private boolean required;

    @Override
    public Long getId() {
        return id;
    }

    public Long getRequirementTemplateId() {
        return requirementTemplateId;
    }

    public void setRequirementTemplateId(final Long requirementTemplateId) {
        this.requirementTemplateId = requirementTemplateId;
    }

    public LookupValue getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(final LookupValue lookupValue) {
        this.lookupValue = lookupValue;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }
}
