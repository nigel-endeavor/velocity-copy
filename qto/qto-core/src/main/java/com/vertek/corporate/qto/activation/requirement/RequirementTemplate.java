package com.vertek.corporate.qto.activation.requirement;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author rcasey 
 * @since 3/3/2023
 */
@Entity
@Table(name = "requirement_template")
public class RequirementTemplate extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_template_id")
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "template_name")
    private String name;

    @Column(name = "global")
    private boolean global;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "ttu_equivalent")
    private BigDecimal ttuEquivalent;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "requirement_template_id", referencedColumnName = "requirement_template_id")
    private List<Requirement> requirements;

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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(final boolean global) {
        this.global = global;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public BigDecimal getTtuEquivalent() {
        return ttuEquivalent;
    }

    public void setTtuEquivalent(final BigDecimal ttuEquivalent) {
        this.ttuEquivalent = ttuEquivalent;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(final List<Requirement> requirements) {
        this.requirements = requirements;
    }
}
