package com.endeavorms.velocity.qto.config;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotNull;


import static com.endeavorms.velocity.qto.config.QConfigurationProperty.configurationProperty;



/**
 * DAO for ConfigurationProperty objects used by OMS.
 * @author mmeehan
 * @since 2.0.0
 */
@Component
public class ConfigPropertyJpaDao extends AbstractJpaDao<ConfigurationProperty, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationProperty.class);

    /**
     * Find a ConfigurationProperty by its property name.
     * @param key the property name (key).
     * @return a ConfigurationProperty.
     */
    public ConfigurationProperty findByPropertyName(@NotNull final String key) {
        return ((JPAQuery<ConfigurationProperty>) new JPAQuery(entityManager)
                .from(configurationProperty)
                .where(configurationProperty.key.eq(key)))
                .fetchOne();
    }

    /**
     * Find all and return sorted by category, name.
     * @return a List of ConfigurationProperty objects.
     */
    public PaginatedResult<ConfigurationProperty> findAll() {

        return new PaginatedResult<>(new JPAQuery<ConfigurationProperty>(entityManager)
                .from(configurationProperty)
                .fetchResults());

    }


       @Override
    public ConfigurationProperty create(final ConfigurationProperty configProperty) {
        return super.create(configProperty);
    }

    @Override
    public ConfigurationProperty edit(final ConfigurationProperty configProperty) {
        LOGGER.trace("edit for: {}", configProperty);

        // Get the transient value to be set on the merged object a few lines below.  The merge will lose the new
        // value on the original object.
        String transientValue = configProperty.getValue();

        ConfigurationProperty updated = super.edit(configProperty);

        updated.setValue(transientValue);
        return updated;
    }
}

