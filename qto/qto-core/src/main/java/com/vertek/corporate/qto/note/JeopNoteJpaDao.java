package com.vertek.corporate.qto.note;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.note.QJeopNote.jeopNote;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Stateless
public class JeopNoteJpaDao extends AbstractMasterCustomerJpaDao<JeopNote> {

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

    public PaginatedResult<JeopNote> findBySearchCriteria(final JeopNoteSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<JeopNote>(entityManager).from(jeopNote)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given LocationSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    public Predicate getExpression(final JeopNoteSearchCriteria criteria) {
        BooleanExpression expression = jeopNote.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, jeopNote.masterCustomerId, jeopNote.tenantId, true);
        if (criteria.getJeopInstanceId() != null) {
            expression = expression.and(jeopNote.jeopInstanceId.eq(criteria.getJeopInstanceId()));
        }

        //todo: filter expression

        return expression;
    }

    public List<JeopNote> findByJeopId(Long jeopId) {
        return new JPAQuery<JeopNote>(entityManager).from(jeopNote)
                .where(jeopNote.jeopInstanceId.eq(jeopId))
                .fetch();
    }
}
