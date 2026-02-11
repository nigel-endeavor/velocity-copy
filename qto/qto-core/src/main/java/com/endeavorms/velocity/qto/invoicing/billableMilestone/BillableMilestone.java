package com.endeavorms.velocity.qto.invoicing.billableMilestone;

import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;
import com.endeavorms.velocity.qto.milestone.Milestone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "billable_milestone")
public class BillableMilestone extends AbstractTenantOwnedEntity {
    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billable_milestone_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "milestone_id", referencedColumnName = "milestone_id", nullable = false)
    private Milestone milestone;

    @Column(name = "milestone_level")
    private String level;

    @Column(name = "percentage")
    private Integer percentage;


    @Override
    public Long getId() {
        return id;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(final Milestone milestone) {
        this.milestone = milestone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(final Integer percentage) {
        this.percentage = percentage;
    }
}
