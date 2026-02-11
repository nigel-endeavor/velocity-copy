package com.endeavorms.velocity.qto.company.task;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.company.task.QTask.task;

@Component
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
