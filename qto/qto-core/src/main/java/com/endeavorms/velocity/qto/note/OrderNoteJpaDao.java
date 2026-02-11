package com.endeavorms.velocity.qto.note;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.note.QOrderNote.orderNote;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Component
public class OrderNoteJpaDao extends AbstractMasterCustomerJpaDao<OrderNote> {

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

    public PaginatedResult<OrderNote> findBySearchCriteria(final OrderNoteSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<OrderNote>(entityManager).from(orderNote)
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
    public Predicate getExpression(final OrderNoteSearchCriteria criteria) {
        BooleanExpression expression = orderNote.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, orderNote.masterCustomerId, orderNote.tenantId, true);

        if (criteria.getOrderId() != null) {
            expression = expression.and(orderNote.orderId.eq(criteria.getOrderId()));
        }

        //todo: filter expression

        return expression;
    }

        /**
     * Find all notes for a order.
     *
     * @param orderId the order id
     * @return the list of notes
     */
    public List<OrderNote> findByOrderId(Long orderId) {
        return new JPAQuery<OrderNote>(entityManager)
                .from(orderNote).where(orderNote.orderId.eq(orderId)).fetch();
    }
}
