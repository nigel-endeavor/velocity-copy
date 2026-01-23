package com.vertek.corporate.qto.config;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
