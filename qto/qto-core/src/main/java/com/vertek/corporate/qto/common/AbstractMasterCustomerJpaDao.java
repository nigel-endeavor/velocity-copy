package com.vertek.corporate.qto.common;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.vertek.corporate.qto.common.SecurityUtils.SCHEDULER;
import static com.vertek.corporate.qto.subject.QCompanySubject.companySubject;

/**
 * @author rcasey
 * @since 10/12/2023
 */
public abstract class AbstractMasterCustomerJpaDao<T extends AbstractMasterCustomerOwnedEntity> extends AbstractMultitenantJpaDao<T, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMasterCustomerJpaDao.class);

    /**
     * Gets the currently selected Master Customer id for the currently authenticated subject.
     * @return currently selected Tenant identifier.
     */
    public Long getCompanyId() {
        String username = SecurityUtils.getLoggedInUser();
        JPAQuery companyIdQuery = (JPAQuery) new JPAQuery(entityManager)
                .select(companySubject.company.id)
                .from(companySubject)
                .where(companySubject.subject.emailAddress.toLowerCase().eq(username)
                        .and(companySubject.isSelected.eq(true)));

        Long companyId = (Long) companyIdQuery.fetchOne();

        return companyId;
    }

    /**
     * Gets a list of master customer id's that the current user is allowed to see.
     * @return a List of Longs.
     */
    protected List<Long> getAllowedCompanyIds() {
        String username = SecurityUtils.getLoggedInUser();

        JPAQuery query = (JPAQuery) new JPAQuery(entityManager)
                .select(companySubject.company.id)
                .from(companySubject)
                .where(companySubject.subject.emailAddress.toLowerCase().eq(username))
                .distinct();

        List<Long> allowedIds = query.fetch();
        LOGGER.trace("allowed company ids: {}", allowedIds);
        return allowedIds;
    }

    /**
     * Verify that the current user has access to an entity.
     * @param entity the entity to be verified.
     * @return true if the user has access, false otherwise.
     */
    @Override
    protected boolean verifyTenant(final TenantOwnedEntity entity) {
        String username = SecurityUtils.getLoggedInUser();

        // "scheduler@vertek.com" subject has access to all tenants
        if (username.equals(SCHEDULER)) {
            return true;
        }

        if (((AbstractMasterCustomerOwnedEntity) entity).getMasterCustomerId() == null) {
            return super.verifyTenant(entity);
        }

        return getAllowedCompanyIds().contains(((AbstractMasterCustomerOwnedEntity) entity).getMasterCustomerId())
                || getAllowedTenantIds().contains(entity.getTenantId());
    }

    protected BooleanExpression addMasterCustomerAndTenantFilter(final BooleanExpression expression,
                                              final NumberPath<Long> masterCustomerIdProperty,
                                              final NumberPath<Long> tenantIdProperty,
                                              final Boolean includeAllCompanies) {
        String loggedInUser = SecurityUtils.getLoggedInUser();

         List<Long> allowedCompanyIds = getAllowedCompanyIds();

         //if includeAllCompanies is True and there are entries in the subject_company table, then the user has access to only those companies.
        //if includeAllCompanies is True and  there are no entries, then the user has access to all companies for the tenant.
        //if includeAllCompanies is False, then the user has access to only the company that is selected in the subject_company table.

        // "scheduler@vertek.com" subject has access to all tenants so just return
        if (!loggedInUser.equals(SCHEDULER)) {
            if (Boolean.TRUE.equals(includeAllCompanies) && allowedCompanyIds.isEmpty()) {
                return expression.and(tenantIdProperty.in(getAllowedTenantIds())
                );
            } else if (Boolean.TRUE.equals(includeAllCompanies) && !allowedCompanyIds.isEmpty()) {
                return expression.and(
                        masterCustomerIdProperty.in(allowedCompanyIds)
                                .and(tenantIdProperty.eq(getTenantId()))
                );
            } else {
                return expression.and(
                        masterCustomerIdProperty.eq(getCompanyId())
                                .and(tenantIdProperty.eq(getTenantId()))
                );
            }
        } else {
            return expression;
        }
    }


}
