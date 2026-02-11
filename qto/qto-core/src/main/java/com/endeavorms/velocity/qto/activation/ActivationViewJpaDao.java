package com.endeavorms.velocity.qto.activation;

import com.google.common.base.Strings;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.endeavorms.velocity.qto.activation.QActivationView.activationView;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@Component
public class ActivationViewJpaDao extends AbstractMasterCustomerJpaDao<ActivationView> {

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
     * Retrieves all activation views matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<ActivationView> findBySearchCriteria(final ActivationViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ActivationView>(entityManager)
                .from(activationView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given ActivationViewSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevent where clause.
     */
    private Predicate getExpression(final ActivationViewSearchCriteria criteria) {
        BooleanExpression expression = activationView.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, activationView.masterCustomerId, activationView.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    activationView.clientServiceId.contains(criteria.getSearch())
                            .or(activationView.scheduledAttemptStatus.contains(criteria.getSearch()))
                            .or(activationView.internalTechAssigned.contains(criteria.getSearch()))
                            .or(activationView.scheduledCheckInTime.stringValue().contains(criteria.getSearch()))
                            .or(activationView.lastUpdateBy.contains(criteria.getSearch()))
                            .or(activationView.clientLocationType.contains(criteria.getSearch()))
                            .or(activationView.clientLocationInfo.contains(criteria.getSearch()))
                            .or(activationView.clientLocationId.contains(criteria.getSearch()))
                            .or(activationView.parentCompanyName.contains(criteria.getSearch()))
            );
        }

        expression = getContainsExpression(expression, activationView.clientServiceId, criteria.getClientServiceId());
        if (!criteria.getScheduledAttemptStatus().isEmpty()) {
            expression = getContainsExpression(expression, activationView.scheduledAttemptStatus, criteria.getScheduledAttemptStatus());
        }
        expression = getContainsExpression(expression, activationView.internalTechAssigned, criteria.getInternalTechAssigned());
        expression = getDateComparisonExpression(expression, activationView.scheduledCheckInTime, criteria.getScheduledCheckInTime(), criteria.getScheduledCheckInTimeRange());
        expression = getContainsExpression(expression, activationView.lastUpdateBy, criteria.getLastUpdateBy());
        expression = getContainsExpression(expression, activationView.clientLocationType, criteria.getClientLocationType());
        expression = getContainsExpression(expression, activationView.clientLocationInfo, criteria.getClientLocationInfo());
        expression = getContainsExpression(expression, activationView.parentCompanyName, criteria.getParentCompanyName());

        return expression;
    }

    private OrderSpecifier getOrderBy(final ActivationViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, activationView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<ActivationView> pathBuilder = new PathBuilder<>(ActivationView.class, activationView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    public ActivationWorklistMeta getWorklistMeta(final ActivationViewSearchCriteria criteria) {
        ActivationWorklistMeta meta = new ActivationWorklistMeta();
        List<Tuple> statusCounts = new JPAQuery<Tuple>(entityManager)
                .from(activationView)
                .select(activationView.scheduledAttemptStatus, activationView.count())
                .where(getExpression(criteria))
                .groupBy(activationView.scheduledAttemptStatus)
                .fetch();
        for (Tuple statusCount : statusCounts) {
            meta.getStatusCounts().put(statusCount.get(0, String.class), statusCount.get(1, Long.class));
        }

        List<String> terminalStatuses = Arrays.asList("Complete", "Cancelled", "Incomplete - Pending Re-Schedule");
        BigDecimal ttuEquivTotal = new JPAQuery<Tuple>(entityManager)
                .from(activationView)
                .select(activationView.ttuEquivalent.sum())
                .where(((BooleanExpression) getExpression(criteria))
                        .and(activationView.scheduledAttemptStatus.notIn(terminalStatuses)))
                .fetchOne();

        meta.setTtuEquivalentTotal((ttuEquivTotal == null) ? BigDecimal.ZERO : ttuEquivTotal);
        return meta;
    }
}
