package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
