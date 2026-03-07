package com.vertek.corporate.qto.company.task;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.company.task.QTask.task;

@Stateless
public class TaskJpaDao extends AbstractJpaDao<Task, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Task> findByLookupValueId(final Long lookupValueId) {
        return new JPAQuery<Task>(entityManager)
                .from(task)
                .where(task.lookupValue.id.eq(lookupValueId))
                .fetch();
    }
}
