package com.vertek.corporate.qto.config;


import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.config.QCompanyConfigurationProperty.companyConfigurationProperty;

/**
 * JPA DAO implementation for the CompanyConfigurationProperty.
 *
 * @author fcuran
 * @since 2.7.0
 */
@Stateless
public class CompanyConfigPropertyJpaDao<CompanyConfigKey extends Enum>
        extends AbstractMultitenantJpaDao<CompanyConfigurationProperty, Long> {

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager entityManager) {
        this.platformEntityManager = entityManager;
    }

    /**
     * DAO for Companies.
     */
    @Inject
    private CompanyJpaDao companyDao;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Returns the value which is associated with the specified configuration key and company id.
     *
     * @param companyId the id of associated company
     * @param key       the configuration key for which an associated value is being requested
     * @return the value which is associated with the specified key
     */
    public CompanyConfigurationProperty findByCompanyAndKey(final Long companyId, final CompanyConfigKey key) {
        return new JPAQuery<CompanyConfigurationProperty>(entityManager)
                .from(companyConfigurationProperty)
                .where(companyConfigurationProperty.key.eq(key.name())
                        .and(companyConfigurationProperty.companyId.eq(companyId)))
                .fetchOne();
    }

    /**
     * This method will replace the current value of the specified configuration property with the specified value.
     *
     * @param companyId the id of associated company
     * @param key       the key of the value which should be replaced with the specified value
     * @param value     the new value which should be assigned to the specified key
     * @return a reference to the updated/new configuration property
     */
    public CompanyConfigurationProperty setValue(final Long companyId, final CompanyConfigKey key, final String value) {
        // Update the existing property, if one exists
        if (exists(companyId, key)) {
            // Find the company configuration property by name
            CompanyConfigurationProperty configProperty = findByCompanyAndKey(companyId, key);

            // Set the new value
            configProperty.setValue(value);

            // Save the updated value
//            if (AuthorizationUtils.isPermitted(Permissions.COMPANY_ADMIN)) {
//                getEntityManager().merge(configProperty);
//                getEntityManager().flush();
//                return configProperty;
//            } else {
            return super.edit(configProperty);
//            }
        } else {
            // Otherwise, create a new company configuration property
            Company company = companyDao.retrieve(companyId);
            CompanyConfigurationProperty property = new CompanyConfigurationProperty(companyId, key.name(), value);
            property.setTenantId(company.getTenantId());

//            if (AuthorizationUtils.isPermitted(Permissions.COMPANY_ADMIN)) {
//                getEntityManager().persist(property);
//                getEntityManager().flush();
//                getEntityManager().refresh(property);
//                return property;
//
//            } else {
            return super.create(property);
//            }
        }
    }

    /**
     * Returns true if the specified configuration key for specified company id has an associated value
     * and false, otherwise. An exception will
     * be thrown if multiple key values are found in the database.
     *
     * @param companyId the id of associated company
     * @param key       the configuration key for which an associated value is being requested
     * @return true if the specified configuration key for specified company id has an associated value
     * and false, otherwise
     */
    public boolean exists(final Long companyId, final CompanyConfigKey key) {
        JPAQuery query =  new JPAQuery<Long>(entityManager)
                .select(companyConfigurationProperty.id)
                .from(companyConfigurationProperty)
                .where(companyConfigurationProperty.key.eq(key.name())
                        .and(companyConfigurationProperty.companyId.eq(companyId)));

        // company admins may query these for any company
//        if (!AuthorizationUtils.isPermitted(Permissions.COMPANY_ADMIN)) {
//            addTenantFilter(companyConfigProperty.tenantId, query);
//        }

        List<Long> id = query.fetch();

        return (id.size() > 0);
    }

    /**yes
     *
     * Returns the modifiable table data that are associated with the specified company id.
     */
    public List<CompanyConfigurationProperty> getAllModifiableProperties(final Long companyId) {
        return new JPAQuery<CompanyConfigurationProperty>(entityManager)
                .from(companyConfigurationProperty)
                .where(companyConfigurationProperty.modifiable.isTrue()
                .and(companyConfigurationProperty.companyId.eq(companyId)))
                .fetch();
    }
}
