package com.vertek.corporate.qto.config;


import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.AbstractManager;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.lookup.LookupType;
import com.vertek.corporate.qto.common.lookup.LookupTypeManager;
import com.vertek.corporate.qto.common.lookup.LookupValue;
import com.vertek.corporate.qto.common.lookup.LookupValueManager;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import jakarta.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.vertek.corporate.qto.config.CompanyConfigKey.*;


/**
 * Business tier manager for interacting with instances of the CompanyConfigurationProperty entity.
 * @author fcurran
 * @since 2.7.0
 */
@Stateless
public class CompanyConfigPropertyManager <CompanyConfigKey extends Enum>
        extends AbstractManager<CompanyConfigurationProperty, Long> {

     /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyConfigPropertyManager.class);


    /**
     * Data access methods for CompanyConfigurationProperty.
     */
    @Inject
    private CompanyConfigPropertyJpaDao<CompanyConfigKey> dao;

    /** Business methods for Companies.*/
    @Inject
    private CompanyManager companyManager;

    @Override
    protected CompanyConfigPropertyJpaDao<CompanyConfigKey> getDao() {
        return dao;
    }

    @Inject
    private LookupTypeManager lookupTypeManager;

    @Inject
    private LookupValueManager lookupValueManager;


  /**
     * Returns true if the specified configuration key for specified company id has an associated value
     * and false, otherwise. An exception will
     * be thrown if multiple key values are found in the database.
     * @param companyId the id of associated company
     * @param key the configuration key for which an associated value is being requested
     * @return true if the specified configuration key for specified company id has an associated value
     * and false, otherwise
     */
    public boolean exists(final Long companyId, final CompanyConfigKey key) {
        return getDao().exists(companyId, key);
    }

    /**
     * Returns the value which is associated with the specified configuration key and company id.
     * @param companyId the id of associated company
     * @param key the configuration key for which an associated value is being requested
     * @return the value which is associated with the specified key and company id.
     */
    public String getString(final Long companyId, final CompanyConfigKey key) {
        CompanyConfigurationProperty property = getDao().findByCompanyAndKey(companyId, key);

        if (null == property) {
            LOGGER.warn("Configuration property " + key.name() + " for companyId " + companyId + " does not exist.");
            return null;
        }
        return property.getValue();
    }

    /**
     * Returns an long representation of the specified key value. An exception will be thrown if the value cannot be
     * parsed as a long value.
     * @param companyId the id of associated company
     * @param key the configuration key for which an associated value is being requested
     * @return a long representation of the value which is associated with the specified key
     */
    public Long getLong(final Long companyId, final CompanyConfigKey key) {
        String value = getString(companyId, key);

        if (null == value) {
            LOGGER.warn("Configuration property " + key.name() + " for companyId " + companyId + " does not exist.");
            return null;
        }
        return Long.parseLong(value);
    }

    /**
     * Returns an integer representation of the specified key value. An exception will be thrown if the value cannot be
     * parsed as an integer value.
     * @param companyId the id of associated company
     * @param key the configuration key for which an associated value is being requested
     * @return an integer representation of the value which is associated with the specified key
     */
    public Integer getInteger(final Long companyId, final CompanyConfigKey key) {
        String value = getString(companyId, key);

        if (null == value) {
            LOGGER.warn("Configuration property " + key.name() + " for companyId " + companyId + " does not exist.");
            return null;
        }

        return Integer.parseInt(value);
    }

    /**
     * Returns true if the specified configuration property contains the string value "true" and false otherwise.
     * @param companyId the id of associated company
     * @param key the configuration key for which an associated value is being requested
     * @return true if the string value is "true" and false otherwise
     */
    public Boolean getBoolean(final Long companyId, final CompanyConfigKey key) {
        String value = getString(companyId, key);

        if (null == value) {
            LOGGER.warn("Configuration property " + key.name() + " for companyId " + companyId + " does not exist.");
            return null;
        }

        return Boolean.parseBoolean(value);
    }

    /**
     * This method will replace the current value of the specified configuration property with the specified value
     * and company id.
     * @param companyId the id of associated company
     * @param key the key of the value which should be replaced with the specified value
     * @param value the new value which should be assigned to the specified key
     */
    public void setLong(final Long companyId, final CompanyConfigKey key, final Long value) {
        if (null == value) {
            getDao().setValue(companyId, key, null);
            return;
        }
        getDao().setValue(companyId, key, Long.toString(value));
    }

    /**
     * This method will replace the current value of the specified configuration property with the specified value.
     * @param companyId the id of associated company
     * @param key the key of the value which should be replaced with the specified value
     * @param value the new value which should be assigned to the specified key
     */
    public void setInteger(final Long companyId, final CompanyConfigKey key, final Integer value) {
        if (null == value) {
            getDao().setValue(companyId, key, null);
            return;
        }

        getDao().setValue(companyId, key, Integer.toString(value));
    }

    /**
     * This method will replace the current value of the specified configuration property with the specified value.
     * @param companyId the id of associated company
     * @param key the key of the value which should be replaced with the specified value
     * @param value the new value which should be assigned to the specified key
     */
    public void setBoolean(final Long companyId, final CompanyConfigKey key, final Boolean value) {
        if (null == value) {
            getDao().setValue(companyId, key, null);
            return;
        }

        getDao().setValue(companyId, key, Boolean.toString(value));
    }

    /**
     * This method will replace the current value of the specified configuration property with the specified value.
     * @param companyId the id of associated company
     * @param key the key of the value which should be replaced with the specified value
     * @param value the new value which should be assigned to the specified key
     */
    public void setString(final Long companyId, final CompanyConfigKey key, final String value) {
        getDao().setValue(companyId, key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompanyConfigurationProperty create(final CompanyConfigurationProperty configProperty) {
        if (configProperty.getTenantId() == null) {
            Company company = companyManager.retrieve(configProperty.getCompanyId());
            configProperty.setTenantId(company.getTenantId());
        }
        return getDao().create(configProperty);
    }

    @Override
    public CompanyConfigurationProperty edit (final CompanyConfigurationProperty entity) {
        if ("ASSIGNED_SERVICE_TYPES".equals(entity.getKey())) {
            //add the selected service types to the lookup value table
            LookupType type = lookupTypeManager.findByType("TENANT_SERVICE_TYPES");
            type.getValues().clear();
            if (!Strings.isNullOrEmpty(entity.getValue())) {
                String[] serviceValues = entity.getValue().split(",");
                for (String inValue : serviceValues) {
                    inValue = inValue.trim();
                    LookupValue value = lookupValueManager.retrieveByTypeAndValue("TENANT_SERVICE_TYPES", inValue);
                    if (value == null) {
                        value = new LookupValue();
                        value.setActive(true);
                        value.setDisplay(inValue);
                    }
                    type.getValues().add(value);
                }
            }
            lookupTypeManager.setValues(type);
        }
        CompanyConfigurationProperty config = retrieve(entity.getId());
        Long tenantId = config.getTenantId();
        entity.setTenantId(tenantId);
        return super.edit(entity);
    }

    /**
     * Gets an encrypted value and decrypts it.
     * @param companyId the identifier of an existing Tenant.
     * @param key the configuration key for which an associated value is being requested.
     * @return String decrypted value
     */
    public String getDecryptedString(final Long companyId, final CompanyConfigKey key) {

        String cipherText = getString(companyId, key);
        if (Strings.isNullOrEmpty(cipherText)) {
            return null;
        }

        try {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
            byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");
            byte[] cipherTextBytes = DatatypeConverter.parseBase64Binary(cipherText);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

            return new String(cipher.doFinal(cipherTextBytes));

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Encrypts property value and stores it.
     * @param companyId the identifier of an existing Tenant.
     * @param key the configuration key for which an associated value is being requested.
     * @param value the new value which should be assigned to the specified key
     */
    public void encryptAndSetString(final Long companyId, final CompanyConfigKey key, final String value) {
        try {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
            byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

            String cipherText = DatatypeConverter.printBase64Binary(cipher.doFinal(value.getBytes()));

            setString(companyId, key, cipherText);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

      /**
     * Gets a list of CompanyConfigProperty instances for the given company identifier.
     * @param companyId the identifier of an existing Tenant.
     * @return a list of CompanyConfigProperty instances for the given company identifier.
     */
    public CompanyConfigPropertiesDto findByCompanyId(final Long companyId) {
        PreconditionsUtil.checkArgument(companyId, "A Company Id is required");

        CompanyConfigPropertiesDto dto = new CompanyConfigPropertiesDto();
        dto.setCompanyId(companyId);
        dto.setDisconnectDelay(getInteger(companyId, (CompanyConfigKey) DISCONNECT_DELAY));
        dto.setClientIdUniqueConstraint(getBoolean(companyId, (CompanyConfigKey) CLIENT_ID_UNIQUE_CONSTRAINT));
        dto.setAutoCreateClientServiceId(getBoolean(companyId, (CompanyConfigKey) AUTO_CREATE_CLIENT_SERVICE_ID));
        return dto;
    }

    /**
     * Returns the modifiable columns that are associated with the specified configuration key and company id.
     */
    public List<CompanyConfigurationProperty> getAllModifiableProperties(final Long companyId) {
        List<CompanyConfigurationProperty> configs = dao.getAllModifiableProperties(companyId);
        return configs;
    }

    public CompanyConfigurationProperty findByKey(final Long companyId, final CompanyConfigKey key) {
        return getDao().findByCompanyAndKey(companyId, key);
    }

    public String decryptString(final String value) {

        try {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
            byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");
            byte[] cipherTextBytes = DatatypeConverter.parseBase64Binary(value);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

            return new String(cipher.doFinal(cipherTextBytes));

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptString(final String value) {
        try {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
            byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

            String cipherText = DatatypeConverter.printBase64Binary(cipher.doFinal(value.getBytes()));
            return cipherText;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
