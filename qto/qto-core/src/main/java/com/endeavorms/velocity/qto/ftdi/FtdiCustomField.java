package com.endeavorms.velocity.qto.ftdi;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rcasey 
 * @since 4/12/2023
 */
@Entity
@Table(name = "ftdi_custom_fields")
public class FtdiCustomField extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ftdi_custom_field_id")
    private Long id;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_field_id", referencedColumnName = "ftdi_custom_field_id")
    private List<FtdiCustomFieldValue> values = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public List<FtdiCustomFieldValue> getValues() {
        return values;
    }

    public void setValues(final List<FtdiCustomFieldValue> values) {
        this.values = values;
    }
}
