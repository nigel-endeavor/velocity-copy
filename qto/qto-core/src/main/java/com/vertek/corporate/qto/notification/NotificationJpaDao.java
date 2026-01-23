package com.vertek.corporate.qto.notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.notification.QNotification.notification;

/**
 * @author rcasey
 * @since 6/15/2023
 */
@Stateless
public class NotificationJpaDao extends AbstractJpaDao<Notification, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Notification> findBySubjectId(final Long subjectId, final boolean includeDismissed) {
        BooleanExpression expression = notification.subjectId.eq(subjectId);
        if (!includeDismissed) {
            expression = expression.and(notification.dismissed.isFalse());
        }
        return new JPAQuery<Notification>(entityManager)
                .from(notification)
                .where(expression)
                .orderBy(notification.createdDate.desc())
                .fetch();
    }
}
