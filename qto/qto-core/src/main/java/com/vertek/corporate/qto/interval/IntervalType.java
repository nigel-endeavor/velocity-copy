package com.vertek.corporate.qto.interval;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Entity
@Table(name = "interval_type")
public class IntervalType extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interval_type_id")
    private Long id;

    @Column(name = "interval_type_desc")
    private String description;

    @Column(name = "interval_type_code")
    private String code;

    @Column(name = "open_milestone_id")
    private Long openMilestoneId;

    @Column(name = "close_milestone_id")
    private Long closeMilestoneId;

    @Override
    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long getOpenMilestoneId() {
        return openMilestoneId;
    }

    public void setOpenMilestoneId(final Long openMilestoneId) {
        this.openMilestoneId = openMilestoneId;
    }

    public Long getCloseMilestoneId() {
        return closeMilestoneId;
    }

    public void setCloseMilestoneId(final Long closeMilestoneId) {
        this.closeMilestoneId = closeMilestoneId;
    }
}
