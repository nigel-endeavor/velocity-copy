package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ftdi_order_type_custom_fields")
public class FtdiOrderTypeCustomFields extends StandardBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_order_type_custom_fields_id")
    private Long id;

    @Column(name = "ftdi_order_type_id")
    private Long orderTypeId;

    @Column(name = "ftdi_custom_field_id")
    private Long customFieldId;

    @Override
    public Long getId() {
        return null;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(final Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public Long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(final Long customFieldId) {
        this.customFieldId = customFieldId;
    }
}
