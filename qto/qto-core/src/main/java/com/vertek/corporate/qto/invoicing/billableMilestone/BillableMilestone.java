package com.vertek.corporate.qto.invoicing.billableMilestone;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;
import com.vertek.corporate.qto.milestone.Milestone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
