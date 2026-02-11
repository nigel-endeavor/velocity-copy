package com.endeavorms.velocity.qto.activation.schedule;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "activation_schedule_custom")
public class ActivationScheduleCustom extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_schedule_custom_id")
    private Long id;

    @Column(name = "activation_schedule_id")
    private Long activationScheduleId;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "field_value")
    private String fieldValue;

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(final String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
