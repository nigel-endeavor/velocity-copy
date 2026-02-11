package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ftdi_order_type_equipment")
public class FtdiOrderTypeEquipment extends StandardBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_order_type_equipment_id")
    private Long id;

    @Column(name = "ftdi_order_type_id")
    private Long orderTypeId;

    @Column(name = "ftdi_equipment_type_id")
    private Long equipmentTypeId;

    @Override
    public Long getId() {
        return id;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(final Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }


    public Long getEquipmentTypeId() {
        return equipmentTypeId;
    }

    public void setEquipmentTypeId(final Long equipmentTypeId) {
        this.equipmentTypeId = equipmentTypeId;
    }


}
