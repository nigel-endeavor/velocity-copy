package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author fcurran
 * @since 9/5/2024
 */
@Entity
@Table(name = "company_task")
public class CompanyTask extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_task_id")
    private Long id;

    @Column(name = "company_id")
    private Long companyId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", updatable = false)
    private Task task;

    @Column(name = "value")
    private String value;

    @Column(name = "sort_order")
    private Long sortOrder;

    @Column(name = "complete_date")
    private Date completeDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "assigned_to")
    private String assignedTo;

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

    public Task getTask() {
        return task;
    }

    public void setTask(final Task task) {
        this.task = task;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(final String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
