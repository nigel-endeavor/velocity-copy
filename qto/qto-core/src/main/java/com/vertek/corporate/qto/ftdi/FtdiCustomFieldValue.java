package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 4/12/2023
 */
@Entity
@Table(name = "ftdi_custom_field_values")
public class FtdiCustomFieldValue extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_custom_field_value_id")
    private Long id;

    @Column(name = "custom_field_id")
    private Long customFieldId;

    @Column(name = "field_value")
    private String fieldValue;

    @Override
    public Long getId() {
        return id;
    }

    public Long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(final Long customFieldId) {
        this.customFieldId = customFieldId;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(final String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
