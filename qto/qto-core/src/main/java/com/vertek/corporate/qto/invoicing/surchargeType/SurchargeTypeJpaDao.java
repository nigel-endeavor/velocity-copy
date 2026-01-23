package com.vertek.corporate.qto.invoicing.surchargeType;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.subject.CompanySubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.Date;

import static com.vertek.corporate.qto.company.QCompany.company;
import static com.vertek.corporate.qto.invoicing.surchargeType.QSurchargeType.surchargeType;

/**
 * Persistence layer for Surcharges.
 * @author fcurran
 * @since 2023-07-11
 */
@Stateless
public class SurchargeTypeJpaDao extends AbstractMultitenantJpaDao<SurchargeType, Long> {

    @Inject
    private CompanySubjectManager companySubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    /**
     * Find a SurchargeType by type and tenantId.
     * @param type surcharge type.
     * @param tenantId tenant id.
     * @return SurchargeType.
     */
    public SurchargeType findByType(final String type, final Long tenantId) {
        return new JPAQuery<SurchargeType>(entityManager).from(surchargeType)
                .where(surchargeType.type.eq(type).and(surchargeType.tenantId.eq(tenantId))).fetchOne();
    }

    /**
     * Find SurchargeTypes by search criteria.
     * @param criteria search criteria.
     * @return PaginatedResult<SurchargeType>.
     */
    public PaginatedResult<SurchargeType> findBySearchCriteria(final SurchargeTypeSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getCompanyId(), "companyId is required");
        if (!companySubjectManager.verifyCompany(criteria.getCompanyId())) {
            return new PaginatedResult<>();
        }
        return new PaginatedResult<>(new JPAQuery<SurchargeType>(entityManager).from(surchargeType)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .orderBy(surchargeType.level.asc(), surchargeType.type.asc())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given SurchargeTypeSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant results.
     */
    private Predicate getExpression(final SurchargeTypeSearchCriteria criteria) {
        BooleanExpression expression = surchargeType.id.isNotNull();
        expression = expression.and(surchargeType.tenantId.eq(
                JPAExpressions.select(company.tenantId)
                        .from(company)
                        .where(company.id.eq(criteria.getCompanyId())
                        )));

        expression = expression.and(surchargeType.endDate.isNull().or(surchargeType.endDate.after(new Date())));

        return expression;
    }
}
