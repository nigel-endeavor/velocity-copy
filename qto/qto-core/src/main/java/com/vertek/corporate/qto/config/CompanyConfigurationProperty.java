package com.vertek.corporate.qto.config;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * This entity represents a single company specific parameter which must be persisted.
 * @author fcurran
 * @since 2.7.0
 */
@Entity
@Table(name = "company_config_property")
public class CompanyConfigurationProperty extends AbstractTenantOwnedEntity {

    /**
     * The unique identifier for this class.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_config_property_id")
    private Long id;

    /**
     * The company identifier.
     */
    @Column(name = "company_id")
    private Long companyId;

    /**
     * The key name of the configuration property.
     */
    @Column(name = "config_property_key")
    private String key;

    /**
     * The configuration property value.
     */
    @Column(name = "config_property_value")
    private String value;

    @Column(name = "modifiable")
    private Boolean modifiable;

    @Column(name ="description")
    private String description;

    @Column (name = "type" )
    private String type;

    @Transient
    private String decryptedValue;

    /**
     * No-argument constructor.
     */
    public CompanyConfigurationProperty() {
    }

    /**
     * @return the companyId
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Creates a new instance of this class with the specified key and a null value.
     * @param companyId the id of associated company
     * @param key the key of this configuration property
     */
    public CompanyConfigurationProperty(final Long companyId, final String key) {
        this.companyId = companyId;
        this.key = key;
    }

    /**
     * Creates a new instance of this class with the specified companykey and a null value.
     * @param companyId the id of associated company
     * @param key the key of this configuration property
     * @param value the value which should be associated with the key
     */
    public CompanyConfigurationProperty(final Long companyId, final String key, final String value) {
        this.companyId = companyId;
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the unique identifier for this class.
     * @return unique identifier for this class
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the key name of the configuration property.
     * @return key name of the configuration property
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the key name of the configuration property.
     * @param key key name of the configuration property
     */
    public void setKey(final String key) {
        this.key = key;
    }

    /**
     * Returns the configuration property value.
     * @return configuration property value
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the configuration property value.
     * @param value configuration property value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    public Boolean getModifiable() {
        return modifiable;
    }

    public void setModifiable(final Boolean modifiable) {
        this.modifiable = modifiable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDecryptedValue() {
        return decryptedValue;
    }

    public void setDecryptedValue(final String decryptedValue) {
        this.decryptedValue = decryptedValue;
    }
}
