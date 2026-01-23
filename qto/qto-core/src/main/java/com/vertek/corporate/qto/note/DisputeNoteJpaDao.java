package com.vertek.corporate.qto.note;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.note.QDisputeNote.disputeNote;

/**
 * @author fcurran
 * @since 9/25/2023
 */
@Stateless
public class DisputeNoteJpaDao extends AbstractMasterCustomerJpaDao<DisputeNote> {

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

    public PaginatedResult<DisputeNote> findBySearchCriteria(final DisputeNoteSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<DisputeNote>(entityManager).from(disputeNote)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    public List<DisputeNote> findByDisputeId(final Long disputeId) {
        return new JPAQuery<DisputeNote>(entityManager)
                .from(disputeNote)
                .where(disputeNote.disputeId.eq(disputeId))
                .fetch();
    }

    /**
     * Gets the where clause for a given LocationSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    public Predicate getExpression(final DisputeNoteSearchCriteria criteria) {
        BooleanExpression expression = disputeNote.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, disputeNote.masterCustomerId, disputeNote.tenantId, true);

        if (criteria.getDisputeId() != null) {
            expression = expression.and(disputeNote.disputeId.eq(criteria.getDisputeId()));
        }

        return expression;
    }
}
