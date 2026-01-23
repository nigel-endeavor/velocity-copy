package com.vertek.corporate.qto.config;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import jakarta.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.security.GeneralSecurityException;

/**
 * Manager for ConfigurationProperty objects used by OMS.
 * todo: This is using String based property names instead of requiring Java enums.  Discuss and move this to
 * todo: platform if it makes sense.  Should discuss whether we want to force use of enums when the property
 * todo: name on a ConfigurationProperty object is a String anyway.
 * @author mmeehan
 * @since 2.0.0
 */
@Stateless
public class ConfigPropertyManager extends StandardManager<ConfigurationProperty> {

    /**
     * Private logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigPropertyManager.class);

    /**
     * The DAO implementation which should be used to interact with persistent instances of ConfigurationProperty.
     */
    @Inject
    private ConfigPropertyJpaDao configPropertyJpaDao;

    @Override
    protected ConfigPropertyJpaDao getDao() {
        return configPropertyJpaDao;
    }


    /**
     * Find a ConfigurationProperty by its property key.
     *
     * @param key     the property key.
     * @return a ConfigurationProperty.
     */
    public ConfigurationProperty findByKey(@NotNull final String key) {
        return configPropertyJpaDao.findByPropertyName(key);
    }

    public String getDecryptedString(final String key) {

        ConfigurationProperty cipherText = findByKey(key);

        if (Strings.isNullOrEmpty(cipherText.getValue())) {
            return null;
        }

        try {
            byte[] keyBytes = DatatypeConverter.parseBase64Binary("WZ6EA4iIluQJ6yAgrVF2ZQ==");
            byte[] ivBytes = DatatypeConverter.parseBase64Binary("DkcP5+idr8uXBVWuHAVxUA==");
            byte[] cipherTextBytes = DatatypeConverter.parseBase64Binary(cipherText.getValue());

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(ivBytes));

            return new String(cipher.doFinal(cipherTextBytes));

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Find all and return sorted by category, name.
     *
     * @return a List of ConfigurationProperty objects.
     */
    public PaginatedResult<ConfigurationProperty> findAll() {
        return configPropertyJpaDao.findAll();
    }

    @Override
    public ConfigurationProperty edit(final ConfigurationProperty configProperty) {
        return super.edit(configProperty);
    }

    @Override
    public ConfigurationProperty create(final ConfigurationProperty configProperty) {
        return super.create(configProperty);
    }

    @Override
    public ConfigurationProperty retrieve(final Long aLong) {
        return super.retrieve(aLong);
    }
}
