package com.vertek.corporate.qto.activation.schedule;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "activation_schedule_equipment")
public class ActivationScheduleEquipment extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_schedule_equipment_id")
    private Long id;

    @Column(name = "activation_schedule_id")
    private Long activationScheduleId;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "item_number")
    private String itemNumber;

    @Override
    public Long getId() {
        return id;
    }

    public Long getActivationScheduleId() {
        return activationScheduleId;
    }

    public void setActivationScheduleId(final Long activationScheduleId) {
        this.activationScheduleId = activationScheduleId;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(final String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final String itemNumber) {
        this.itemNumber = itemNumber;
    }
}
