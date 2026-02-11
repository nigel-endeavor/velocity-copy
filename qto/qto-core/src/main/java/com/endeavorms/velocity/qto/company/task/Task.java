package com.endeavorms.velocity.qto.company.task;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;
import com.endeavorms.velocity.qto.common.lookup.LookupValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
