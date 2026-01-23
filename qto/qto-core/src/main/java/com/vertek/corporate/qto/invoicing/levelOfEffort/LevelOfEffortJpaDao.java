package com.vertek.corporate.qto.invoicing.levelOfEffort;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.subject.CompanySubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import static com.vertek.corporate.qto.company.QCompany.company;
import static com.vertek.corporate.qto.invoicing.levelOfEffort.QLevelOfEffort.levelOfEffort1;

/**
 * @author rcasey
 * @since 7/12/2023
 */
@Stateless
public class LevelOfEffortJpaDao extends AbstractMultitenantJpaDao<LevelOfEffort, Long> {

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

    public List<LevelOfEffort> findByCompanyId(final Long companyId) {
        BooleanExpression expression = levelOfEffort1.id.isNotNull();
        //only return active level of efforts
        expression = expression.and(levelOfEffort1.endDate.isNull().or(levelOfEffort1.endDate.gt(new Date())));

        if (companyId == null) {
            List<Long> tenantIds = getAllowedTenantIds();
            List<Long> companyTenantIds = companySubjectManager.getAllowedMasterCustomerTenantIds();
            expression = expression.and(levelOfEffort1.tenantId.in(tenantIds).or(levelOfEffort1.tenantId.in(companyTenantIds)));
        } else {
            expression = expression.and(levelOfEffort1.tenantId.eq(
                    JPAExpressions.select(company.tenantId)
                            .from(company)
                            .where(company.id.eq(companyId)
                            )));
        }

        List<LevelOfEffort> result = new JPAQuery<LevelOfEffort>(entityManager)
                .from(levelOfEffort1)
                .where(expression)
                .orderBy(levelOfEffort1.sortOrder.asc())
                .fetch();
        //remove duplicate level of efforts, but maintain order
        return new ArrayList<>(new TreeSet<>(result));
    }

    /**
     * Returns the current level of effort for the given name and date.
     *
     * @param loe      the name of the level of effort.
     * @param tenantId the tenant.
     * @return the current level of effort.
     */
    public List<LevelOfEffort> findCurrentLoeByName(final String loe, final Long tenantId) {
        return new JPAQuery<LevelOfEffort>(entityManager)
                .from(levelOfEffort1)
                .where(levelOfEffort1.levelOfEffort.eq(loe)
                        .and(levelOfEffort1.tenantId.eq(tenantId)))
                .orderBy(levelOfEffort1.id.asc())
                .fetch();
    }
}
