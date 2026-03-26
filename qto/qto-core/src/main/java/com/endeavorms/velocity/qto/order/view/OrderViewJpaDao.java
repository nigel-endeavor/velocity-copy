package com.endeavorms.velocity.qto.order.view;

import com.google.common.base.Strings;
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

import static com.endeavorms.velocity.qto.order.view.QOrderView.orderView;

import com.querydsl.core.types.Order;

/**
 * QueryDSL-based DAO for the OrderView (v_manage_orders) read-only entity.
 * Mirrors the ServiceViewJpaDao pattern for consistency.
 */
@Component
public class OrderViewJpaDao extends AbstractMasterCustomerJpaDao<OrderView> {

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

    public PaginatedResult<OrderView> findBySearchCriteria(final OrderViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<OrderView>(entityManager)
                .from(orderView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    private Predicate getExpression(final OrderViewSearchCriteria criteria) {
        BooleanExpression expression = orderView.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(
                expression, orderView.masterCustomerId, orderView.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            String search = criteria.getSearch();
            expression = expression.and(
                    orderView.clientOrderId.containsIgnoreCase(search)
                            .or(orderView.companyName.containsIgnoreCase(search))
                            .or(orderView.status.containsIgnoreCase(search))
                            .or(orderView.vertekClient.containsIgnoreCase(search))
                            .or(orderView.id.stringValue().contains(search))
            );
        }

        if (criteria.getStatus() != null && !criteria.getStatus().isEmpty()) {
            expression = expression.and(orderView.status.in(criteria.getStatus()));
        }

        return expression;
    }

    private OrderSpecifier getOrderBy(final OrderViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, orderView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<OrderView> pathBuilder =
                    new PathBuilder<>(OrderView.class, orderView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(),
                    pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }
}
