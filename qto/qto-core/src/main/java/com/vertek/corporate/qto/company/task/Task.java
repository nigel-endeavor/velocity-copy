package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;
import com.vertek.corporate.qto.common.lookup.LookupValue;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author fcurran
 * @since 9/5/2024
 */
@Entity
@Table(name = "task")
public class Task extends StandardVersionedBaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "task_group_id")
    private Long taskGroupId;

    @OneToOne
    @JoinColumn(name = "lookup_value_id")
    private LookupValue lookupValue;

    @Column(name = "value")
    private String value;

    @Column(name = "sort_order")
    private Long sortOrder;

    @Column(name = "required")
    private boolean required;

    @Override
    public Long getId() {
        return id;
    }

    public Long getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(final Long taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public LookupValue getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(final LookupValue lookupValue) {
        this.lookupValue = lookupValue;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }
}
