package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import org.hibernate.annotations.Formula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.List;

/**
 * @author fcurran
 * @since 9/5/2024
 */
@Entity
@Table(name = "task_group")
public class TaskGroup extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_group_id")
    private Long id;

    @Column(name = "template_name")
    private String name;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_group_id", referencedColumnName = "task_group_id")
    @OrderBy("sortOrder")
    private List<Task> tasks;

    @Formula("(SELECT REPLACE(GROUP_CONCAT(t.value ORDER BY t.sort_order), ',', ', ') FROM task t" +
            " WHERE t.task_group_id = task_group_id" +
            " ORDER BY t.sort_order)")
    private String taskNames;

    @Formula("(SELECT REPLACE(GROUP_CONCAT(c.company_name), ',', ', ') FROM company c " +
            "WHERE c.task_group_id = task_group_id)")
    private String usedBy;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getTaskNames() {
        return taskNames;
    }

    public void setTaskNames(final String taskNames) {
        this.taskNames = taskNames;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(final String usedBy) {
        this.usedBy = usedBy;
    }
}
