package com.endeavorms.velocity.qto.milestone;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import org.springframework.stereotype.Repository;

import static com.endeavorms.velocity.qto.milestone.QOrderMilestoneInstance.orderMilestoneInstance;

/**
 * Persistence tier for OrderMilestoneInstances.
 *
 * @author fcurran
 */

@Repository
public class OrderMilestoneInstanceJpaDao extends AbstractMilestoneInstanceJpaDao<OrderMilestoneInstance> {
    @Override
    public List<OrderMilestoneInstance> listByRecord(final Long orderId) {
        PreconditionsUtil.checkArgument(orderId, "A orderId is required");
        BooleanExpression expression = orderMilestoneInstance.orderId.eq(orderId);
        expression = addMasterCustomerAndTenantFilter(expression, orderMilestoneInstance.masterCustomerId, orderMilestoneInstance.tenantId, true);
        JPQLQuery<OrderMilestoneInstance> query = new JPAQuery<OrderMilestoneInstance>(entityManager)
                .from(orderMilestoneInstance)
                .where(expression)
                .orderBy(orderMilestoneInstance.count.desc(),
                        orderMilestoneInstance.milestoneDate.desc());
        return query.fetch();
    }

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
     * Checks if an instance exists for the given location and milestone code.
     * @param orderId order id.
     * @param milestoneCode milestone code.
     * @return True if an instance already exists, false otherwise.
     */
    public boolean doesMilestoneExist(final Long orderId, final String milestoneCode) {
        PreconditionsUtil.checkArgument(orderId, "An orderId is required");
        PreconditionsUtil.checkArgument(milestoneCode, "A milestoneCode is required");
        BooleanExpression expression = orderMilestoneInstance.orderId.eq(orderId)
                        .and(orderMilestoneInstance.milestone.code.eq(milestoneCode));

          JPQLQuery<OrderMilestoneInstance> query =  new JPAQuery<OrderMilestoneInstance>(entityManager)
                .from(orderMilestoneInstance)
                .where(expression)
                .limit(1);

          OrderMilestoneInstance mi = query.fetchOne();

        return mi != null;

    }

        /**
     * Retrieves current milestone by name.
     * @param id service id.
     * @param milestoneCode milestone code.
     * @return current order milestone instance.
     */
    public OrderMilestoneInstance retrieveCurrentMilestoneByCode(final Long id, final String milestoneCode) {
                BooleanExpression expression = orderMilestoneInstance.orderId.eq(id)
                        .and(orderMilestoneInstance.milestone.code.eq(milestoneCode));

          JPQLQuery<OrderMilestoneInstance> query =  new JPAQuery<OrderMilestoneInstance>(entityManager)
                .from(orderMilestoneInstance)
                .where(expression)
                .limit(1);

          OrderMilestoneInstance mi = query.fetchOne();
          return mi;
    }

    /**
     * Retrieves current milestone by Order ID.
     * @param orderId invoice id.
     * @return  milestone instances.
     */
    public List<OrderMilestoneInstance> findByOrderId(final Long orderId) {
        return new JPAQuery<OrderMilestoneInstance>(entityManager).from(orderMilestoneInstance)
                .where(orderMilestoneInstance.orderId.eq(orderId)).fetch();
    }
}
