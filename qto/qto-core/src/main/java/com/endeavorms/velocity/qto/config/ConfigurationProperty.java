package com.endeavorms.velocity.qto.config;

import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Config property model.
 */
@Entity
@Table(name = "config_property")
public class ConfigurationProperty extends StandardVersionedBaseEntity {
    /**
     * The unique identifier for this class.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_property_id")
    protected Long id;

    /**
     * The key name of the configuration property.
     */
    @Column(name = "config_property_key")
    protected String key;

    /**
     * Config property value.
     */
    @Column(name = "config_property_value")
    private String value;

    @Override
    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
