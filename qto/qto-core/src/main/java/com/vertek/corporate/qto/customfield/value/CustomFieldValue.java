package com.vertek.corporate.qto.customfield.value;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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
