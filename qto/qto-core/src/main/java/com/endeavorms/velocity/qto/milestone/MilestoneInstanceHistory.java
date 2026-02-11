package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * @author rcasey
 * @since 2/17/2023
 */
@Entity
@Table(name = "milestone_instance_history")
public class MilestoneInstanceHistory extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_instance_history_id")
    private Long id;

    @Column(name = "milestone_instance_id")
    private Long milestoneInstanceId;

    @Column(name = "old_date")
    private Date oldDate;

    @Column(name = "new_date")
    private Date newDate;

    @Column(name = "note")
    private String note;

    @Column(name = "update_by")
    private String updateBy;

    @Override
    public Long getId() {
        return id;
    }

    public Long getMilestoneInstanceId() {
        return milestoneInstanceId;
    }

    public void setMilestoneInstanceId(final Long milestoneInstanceId) {
        this.milestoneInstanceId = milestoneInstanceId;
    }

    public Date getOldDate() {
        return oldDate;
    }

    public void setOldDate(final Date oldDate) {
        this.oldDate = oldDate;
    }

    public Date getNewDate() {
        return newDate;
    }

    public void setNewDate(final Date newDate) {
        this.newDate = newDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(final String updateBy) {
        this.updateBy = updateBy;
    }
}
