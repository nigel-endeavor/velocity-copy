package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author rcasey 
 * @since 4/12/2023
 */
@Entity
@Table(name = "ftdi_equipment_type")
public class FtdiEquipmentType extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_equipment_type_id")
    private Long id;

    @Column(name = "equipment_type")
    private String equipmentType;

    @Column(name = "part_number")
    private String partNumber;

    @Column(name = "client_part_number")
    private String clientPartNumber;

    @Column(name = "item_number")
    private String itemNumber;

    @Column(name = "active")
    private boolean active;

    @Override
    public Long getId() {
        return id;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(final String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(final String partNumber) {
        this.partNumber = partNumber;
    }

    public String getClientPartNumber() {
        return clientPartNumber;
    }

    public void setClientPartNumber(final String clientPartNumber) {
        this.clientPartNumber = clientPartNumber;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(final String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }
}
