package com.endeavorms.velocity.qto.costhistory;

import com.google.common.base.Strings;
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
import java.time.LocalDate;
import java.util.List;

import static com.endeavorms.velocity.qto.costhistory.QCostHistory.costHistory;

/**
 * @author rcasey
 * @since 11/1/2023
 */
@Component
public class CostHistoryJpaDao extends AbstractMasterCustomerJpaDao<CostHistory> {

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

    public PaginatedResult<CostHistory> findBySearchCriteria(final CostHistorySearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<CostHistory>(entityManager)
                .from(costHistory)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .fetchResults());
    }

    private Predicate getExpression(final CostHistorySearchCriteria criteria) {
        BooleanExpression expression = costHistory.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, costHistory.masterCustomerId, costHistory.tenantId, true);
        expression = expression.and(costHistory.serviceId.in(criteria.getServiceIds()));
        expression = getContainsExpression(expression, costHistory.costType, criteria.getCostType());
        return expression;
    }

    private OrderSpecifier getOrderBy(final CostHistorySearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, costHistory.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<CostHistory> pathBuilder
                    = new PathBuilder<>(CostHistory.class, costHistory.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    /**
     * Gets the metadata for the cost history grid.
     * @param criteria the criteria to filter by.
     * @return the metadata for the grid.
     */
    public CostHistoryMeta getCostHistoryMeta(final CostHistorySearchCriteria criteria) {
        BooleanExpression expression = costHistory.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, costHistory.masterCustomerId, costHistory.tenantId, true);
        expression = expression.and(costHistory.serviceId.in(criteria.getServiceIds()));
        expression = expression.and(costHistory.costType.eq("MRC"));
        LocalDate startDate = LocalDate.now().minusYears(1);

        BigDecimal currentCost = new JPAQuery<BigDecimal>(entityManager)
                .from(costHistory)
                .select(costHistory.newValue)
                .where(expression)
                .orderBy(costHistory.id.desc())
                .fetchFirst();

        BigDecimal costLastYear = new JPAQuery<BigDecimal>(entityManager)
                .from(costHistory)
                .select(costHistory.newValue)
                .where(expression.and(costHistory.updateDate.after(java.sql.Date.valueOf(startDate))))
                .orderBy(costHistory.id.asc())
                .fetchFirst();

        BigDecimal originalCost = new JPAQuery<BigDecimal>(entityManager)
                .from(costHistory)
                .select(costHistory.newValue)
                .where(expression.and(costHistory.changeReason.eq("Initial Cost")))
                .fetchFirst();

        BigDecimal originalMrc = new JPAQuery<BigDecimal>(entityManager)
                .from(costHistory)
                .select(costHistory.newValue)
                .where(expression.and(costHistory.changeReason.eq("Original MRC")))
                .fetchFirst();

        CostHistoryMeta meta = new CostHistoryMeta();
        if ((currentCost == null && costLastYear == null) || currentCost == null) {
            meta.setCostChangeThisYear(BigDecimal.ZERO);
            meta.setCostChangeLifetime(BigDecimal.ZERO);
        } else if (costLastYear == null) {
            meta.setCostChangeThisYear(BigDecimal.ZERO);
            meta.setCostChangeLifetime(
                    originalMrc != null ? currentCost.subtract(originalMrc) : currentCost.subtract(originalCost));
        } else {
            meta.setCostChangeThisYear(currentCost.subtract(costLastYear));
            meta.setCostChangeLifetime(
                    originalMrc != null ? currentCost.subtract(originalMrc) : currentCost.subtract(originalCost));
        }
        return meta;
    }

    public List<CostHistory> findByServiceId(Long serviceId) {
        return new JPAQuery<CostHistory>(entityManager)
                .from(costHistory)
                .where(costHistory.serviceId.eq(serviceId))
                .fetch();
    }
}
