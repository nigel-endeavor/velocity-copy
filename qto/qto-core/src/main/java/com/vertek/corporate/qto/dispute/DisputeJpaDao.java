package com.vertek.corporate.qto.dispute;

import com.google.common.base.Strings;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.vertek.corporate.qto.dispute.QDispute.dispute;
import static com.vertek.corporate.qto.service.QService.service;

/**
 * @author llevit
 * @since 9/07/2023
 */
@Stateless
public class DisputeJpaDao extends AbstractMasterCustomerJpaDao<Dispute> {
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
     * Retrieves all dispute views matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<Dispute> findBySearchCriteria(final DisputeSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<Dispute>(entityManager)
                .from(dispute)
                .where(getExpression(criteria))
                .orderBy(dispute.disputeClosedDate.asc())
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given DisputeSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant results.
     */
    public Predicate getExpression(final DisputeSearchCriteria criteria) {
        BooleanExpression expression = dispute.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, dispute.masterCustomerId, dispute.tenantId, true);

          if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    dispute.invoiceNum.contains(criteria.getSearch())
            );
          }

        if (criteria.getServiceId() != null) {
            expression = expression.and(dispute.serviceId.eq(criteria.getServiceId()));
        }
        if (criteria.getLocationId() != null) {
            expression = expression.and(dispute.serviceId.in(
                    JPAExpressions.select(service.id)
                            .from(service)
                            .where(service.locationId.eq(criteria.getLocationId()))
            ));
        }
        if (criteria.isDisputeOpen()) {
            expression = expression.and(dispute.openDate.isNotNull()).and(dispute.disputeClosedDate.isNull());
        }

        return expression;
    }

    private OrderSpecifier getOrderBy(final DisputeSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, dispute.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<Dispute> pathBuilder = new PathBuilder<>(Dispute.class, dispute.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    /**
     * Retrieves all disputes for a given service.
     * @param serviceId the service id to filter by.
     * @return matching disputes.
     */
    public List<Dispute> findByServiceId(final Long serviceId) {
        return new JPAQuery<Dispute>(entityManager)
                .from(dispute)
                .where(dispute.serviceId.eq(serviceId))
                .fetch();
    }

    /**
     * Retrieves all open disputes for the given service IDs.
     * @param serviceIds list of services IDs.
     * @return matching disputes.
     */
    public List<Dispute> findOpenDisputesByServiceId(final Long serviceId) {
        return new JPAQuery<Dispute>(entityManager)
                .from(dispute)
                .where(dispute.serviceId.eq(serviceId)
                        .and(dispute.disputeClosedDate.isNull()))
                .fetch();
    }

    /**
     * Gets the meta data for the grid built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the grid.
     */
    public DisputeGridMeta getDisputesGridMeta(final DisputeSearchCriteria criteria) {
        criteria.setDisputeOpen(true);

        NumberPath<BigDecimal> amountDisputedMrc = Expressions.numberPath(BigDecimal.class, "amountDisputedMrc");
        NumberPath<BigDecimal> amountDisputedNrc = Expressions.numberPath(BigDecimal.class, "amountDisputedNrc");

        Expression<BigDecimal> sumMrcExpression = Expressions.cases()
                .when(amountDisputedMrc.sum().isNull()).then(BigDecimal.ZERO)
                .otherwise(amountDisputedMrc.sum());

        Expression<BigDecimal> sumNrcExpression = Expressions.cases()
                .when(amountDisputedNrc.sum().isNull()).then(BigDecimal.ZERO)
                .otherwise(amountDisputedNrc.sum());

        Tuple metaCounts = new JPAQuery<Tuple>(entityManager)
                .from(dispute)
                .select(
                        sumMrcExpression,
                        sumNrcExpression
                )
                .where(getExpression(criteria))
                .fetchOne();

        DisputeGridMeta meta = new DisputeGridMeta();
        meta.setOpenDisputeMrc(metaCounts.get(sumMrcExpression));
        meta.setOpenDisputeNrc(metaCounts.get(sumNrcExpression));

        return meta;
    }

}
