package com.endeavorms.velocity.qto.notification;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.notification.QNotification.notification;

/**
 * @author rcasey
 * @since 6/15/2023
 */
@Component
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
