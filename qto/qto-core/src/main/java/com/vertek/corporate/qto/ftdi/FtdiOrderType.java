package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey 
 * @since 4/12/2023
 */
@Entity
@Table(name = "ftdi_order_type")
public class FtdiOrderType extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_order_type_id")
    private Long id;

    @Column(name = "vendor_order_type_id")
    private String vendorOrderTypeId;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "category")
    private String category;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "sort1")
    private String sort1;

    @Column(name = "sort2")
    private String sort2;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "last_update")
    private Date lastUpdate;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ftdi_order_type_equipment",
        joinColumns = @JoinColumn(name = "ftdi_order_type_id"),
        inverseJoinColumns = @JoinColumn(name = "ftdi_equipment_type_id"))
    private List<FtdiEquipmentType> equipmentTypes = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "ftdi_order_type_custom_fields",
        joinColumns = @JoinColumn(name = "ftdi_order_type_id"),
        inverseJoinColumns = @JoinColumn(name = "ftdi_custom_field_id"))
    private List<FtdiCustomField> customFields = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public String getVendorOrderTypeId() {
        return vendorOrderTypeId;
    }

    public void setVendorOrderTypeId(final String vendorOrderTypeId) {
        this.vendorOrderTypeId = vendorOrderTypeId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(final String orgId) {
        this.orgId = orgId;
    }

    public String getSort1() {
        return sort1;
    }

    public void setSort1(final String sort1) {
        this.sort1 = sort1;
    }

    public String getSort2() {
        return sort2;
    }

    public void setSort2(final String sort2) {
        this.sort2 = sort2;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<FtdiEquipmentType> getEquipmentTypes() {
        return equipmentTypes;
    }

    public void setEquipmentTypes(final List<FtdiEquipmentType> equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
    }

    public List<FtdiCustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(final List<FtdiCustomField> customFields) {
        this.customFields = customFields;
    }
}
