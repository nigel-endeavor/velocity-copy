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
