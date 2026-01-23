package com.vertek.corporate.qto.jeop;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.PreconditionsUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.jeop.QOrderJeop.orderJeop;

/**
 * @author llevit
 */
@Stateless
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
