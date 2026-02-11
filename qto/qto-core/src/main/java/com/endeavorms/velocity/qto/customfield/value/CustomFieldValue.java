package com.endeavorms.velocity.qto.customfield.value;

import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "custom_field_value")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomFieldValue extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_field_value_id")
    private Long id;

    @Column(name = "custom_field_id")
    private Long customFieldId;

    @Column(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public Long getCustomFieldId() {
        return customFieldId;
    }

    public void setCustomFieldId(final Long customFieldId) {
        this.customFieldId = customFieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

}
