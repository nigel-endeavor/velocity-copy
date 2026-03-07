package com.vertek.corporate.qto.company.task;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.vertek.corporate.qto.company.task.QTaskGroup.taskGroup;

/**
 * @author fcurran
 * @since 9/5/2024
 */
@Stateless
public class TaskGroupJpaDao extends AbstractMasterCustomerJpaDao<TaskGroup> {
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

    public TaskGroup getDefaultTaskGroup(final Long id) {
        Long tenantId = id;
        if (tenantId == null) {
           tenantId = getTenantId();
        }
        return new JPAQuery<TaskGroup>(entityManager)
                .from(taskGroup)
                .where(taskGroup.isDefault.eq(true)
                        .and(taskGroup.active.eq(true))
                        .and(taskGroup.tenantId.eq(tenantId)))
                .fetchOne();
    }

    /**
     * Returns task groups that match the provided search criteria.
     * @param criteria what to match task groups on.
     * @return the matching task groups, if any.
     */
    public PaginatedResult<TaskGroup> findBySearchCriteria(final TaskGroupSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<TaskGroup>(entityManager).from(taskGroup)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .orderBy(getOrderBy(criteria).toArray(new OrderSpecifier[0]))
                .fetchResults());
    }

    /**
     * Gets the where clause for a given criteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final TaskGroupSearchCriteria criteria) {
        BooleanExpression expression = taskGroup.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, taskGroup.masterCustomerId, taskGroup.tenantId, true);
        if (criteria.getActive() != null) {
            expression = expression.and(taskGroup.active.eq(criteria.getActive()));
        }

        return expression;
    }

    /**
     * Gets the order by clause for a given criteria.
     * @param criteria the criteria to filter by.
     * @return relevant order by.
     */
    private List<OrderSpecifier> getOrderBy(final TaskGroupSearchCriteria criteria) {
        List<OrderSpecifier> orderBys = new ArrayList<>();
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            final PathBuilder<TaskGroup> pathBuilder = new PathBuilder<>(TaskGroup.class, "taskGroup");
            orderBys.add(new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField())));
        } else {
            orderBys.add(new OrderSpecifier(Order.DESC, taskGroup.isDefault));
            orderBys.add(new OrderSpecifier(Order.DESC, taskGroup.name));
        }
        return orderBys;
    }

    /**
     * Finds task groups by task name.
     * @param taskName the task name to match.
     * @return the matching task groups, if any.
     */
    public List<TaskGroup> findByTask(final String taskName) {
        return new JPAQuery<TaskGroup>(entityManager)
                .from(taskGroup)
                .where(taskGroup.tasks.any().lookupValue.value.eq(taskName))
                .fetch();
    }
}
