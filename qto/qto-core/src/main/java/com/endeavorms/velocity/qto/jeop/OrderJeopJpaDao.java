package com.endeavorms.velocity.qto.jeop;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.jeop.QOrderJeop.orderJeop;

/**
 * @author llevit
 */
@Component
public class OrderJeopJpaDao extends AbstractJpaDao<OrderJeop, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets list of all open jeops for an Order.
     *
     * @param orderId of the Order.
     * @return List of open jeops.
     */
    public List<OrderJeop> getOpen(final Long orderId) {
        PreconditionsUtil.checkArgument(orderId, "An Order is required");
         JPQLQuery<OrderJeop> query = new JPAQuery<OrderJeop>(entityManager)
                .from(orderJeop)
                .where(orderJeop.orderId.eq(orderId)
                        .and(orderJeop.endDate.isNull()));
        return query.fetch();
    }

        public List<OrderJeop> findByOrderId(final Long orderId) {
        PreconditionsUtil.checkArgument(orderId, "An Order is required");
         JPQLQuery<OrderJeop> query = new JPAQuery<OrderJeop>(entityManager)
                .from(orderJeop)
                .where(orderJeop.orderId.eq(orderId));
        return query.fetch();
    }
}
