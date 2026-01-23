package com.vertek.corporate.qto.message;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.message.QMessageThread.messageThread;

/**
 * @author rcasey
 * @since 4/26/2023
 */
@Stateless
public class MessageThreadJpaDao extends AbstractMasterCustomerJpaDao<MessageThread> {
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

    public List<MessageThread> findByLocationId(final Long locationId) {
        BooleanExpression expression = messageThread.locationId.eq(locationId);
        expression = addMasterCustomerAndTenantFilter(expression, messageThread.masterCustomerId, messageThread.tenantId, true);
        return new JPAQuery<MessageThread>(entityManager)
                .from(messageThread)
                .where(expression)
                .orderBy(new OrderSpecifier<>(Order.DESC, messageThread.id))
                .fetch();
    }
}
