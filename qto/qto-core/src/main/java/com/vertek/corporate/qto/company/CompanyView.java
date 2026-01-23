package com.vertek.corporate.qto.company;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author rcasey
 * @since 1/2/2024
 */
@Entity
@Table(name = "v_company")
public class
CompanyView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "company_type")
    private String type;

    @Column(name = "company_active")
    private boolean active;

    @Column(name = "company_uuid")
    private String uuid;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "legacy_id")
    private Long legacyId;

    @Column(name = "billing_contact_name")
    private String billingContactName;

    @Column(name = "billing_contact_email")
    private String billingContactEmail;

    @Column(name = "billing_contact_phone")
    private String billingContactPhone;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "inventory_location_count")
    private Long inventoryLocationCount;

    @Column(name = "inventory_mrc")
    private BigDecimal inventoryMrc;

    @Column(name = "inventory_mrr")
    private BigDecimal inventoryMrr;

    @Column(name = "inventory_nrr")
    private BigDecimal inventoryNrr;

    @Column(name = "task_group_id")
    private Long taskGroupId;

    @Column(name = "status")
    private String status;

    @Column(name = "last_completed_task")
    private String lastCompletedTask;

    @Column(name = "next_task")
    private String nextTask;

    @Column(name = "next_task_assigned_to")
    private String nextTaskAssignedTo;

    @Column(name = "remaining_tasks")
    private Long remainingTasks;

    @Column(name = "progress_percentage")
    private BigDecimal progressPercentage;

    @Column(name = "account_manager_name")
    private String accountManager;

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

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public String getBillingContactName() {
        return billingContactName;
    }

    public void setBillingContactName(final String billingContactName) {
        this.billingContactName = billingContactName;
    }

    public String getBillingContactEmail() {
        return billingContactEmail;
    }

    public void setBillingContactEmail(final String billingContactEmail) {
        this.billingContactEmail = billingContactEmail;
    }

    public String getBillingContactPhone() {
        return billingContactPhone;
    }

    public void setBillingContactPhone(final String billingContactPhone) {
        this.billingContactPhone = billingContactPhone;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(final String tenantName) {
        this.tenantName = tenantName;
    }

    public Long getInventoryLocationCount() {
        return inventoryLocationCount;
    }

    public void setInventoryLocationCount(final Long inventoryLocationCount) {
        this.inventoryLocationCount = inventoryLocationCount;
    }

    public BigDecimal getInventoryMrc() {
        return inventoryMrc;
    }

    public void setInventoryMrc(final BigDecimal inventoryMrc) {
        this.inventoryMrc = inventoryMrc;
    }

    public BigDecimal getInventoryMrr() {
        return inventoryMrr;
    }

    public void setInventoryMrr(final BigDecimal inventoryMrr) {
        this.inventoryMrr = inventoryMrr;
    }

    public BigDecimal getInventoryNrr() {
        return inventoryNrr;
    }

    public void setInventoryNrr(final BigDecimal inventoryNrr) {
        this.inventoryNrr = inventoryNrr;
    }

    @JsonProperty("parentCompanyId")
    public Long getParentCompanyId() {
        return super.getMasterCustomerId();
    }

    public Long getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(final Long taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getLastCompletedTask() {
        return lastCompletedTask;
    }

    public void setLastCompletedTask(final String lastCompletedTask) {
        this.lastCompletedTask = lastCompletedTask;
    }

    public String getNextTask() {
        return nextTask;
    }

    public void setNextTask(final String nextTask) {
        this.nextTask = nextTask;
    }

    public String getNextTaskAssignedTo() {
        return nextTaskAssignedTo;
    }

    public void setNextTaskAssignedTo(final String nextTaskAssignedTo) {
        this.nextTaskAssignedTo = nextTaskAssignedTo;
    }

    public Long getRemainingTasks() {
        return remainingTasks;
    }

    public void setRemainingTasks(final Long remainingTasks) {
        this.remainingTasks = remainingTasks;
    }

    public BigDecimal getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(final BigDecimal progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(final String accountManager) {
        this.accountManager = accountManager;
    }
}
