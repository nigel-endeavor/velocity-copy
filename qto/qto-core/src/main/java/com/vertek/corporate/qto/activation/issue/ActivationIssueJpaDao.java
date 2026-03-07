package com.vertek.corporate.qto.activation.issue;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.activation.issue.QActivationIssue.activationIssue;

/**
 * @author rcasey
 * @since 3/29/2023
 */
@Stateless
public class ActivationIssueJpaDao extends AbstractMasterCustomerJpaDao<ActivationIssue> {

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
     * Finds all ActivationIssues with the given activation attempt id, ordered by rank.
     * @param attemptId activation attempt id
     * @return list of corresponding ActivationIssues
     */
    public List<ActivationIssue> findByAttemptId(final Long attemptId) {
        BooleanExpression expression = activationIssue.activationAttemptId.eq(attemptId);
        expression = addMasterCustomerAndTenantFilter(expression, activationIssue.masterCustomerId, activationIssue.tenantId, true);
        return new JPAQuery<ActivationIssue>(entityManager)
                .from(activationIssue)
                .where(expression)
                .orderBy(new OrderSpecifier<>(Order.ASC, activationIssue.rank))
                .fetch();
    }
}
