package com.vertek.corporate.qto.fileimport.importactivity;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.activation.ActivationView;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMultitenantJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.activation.QActivationView.activationView;
import static com.vertek.corporate.qto.fileimport.importactivity.QImportActivity.importActivity;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Stateless
public class ImportActivityJpaDao extends AbstractMultitenantJpaDao<ImportActivity, Long> {
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

    public PaginatedResult<ImportActivity> findBySearchCriteria(final ImportActivitySearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ImportActivity>(entityManager).from(importActivity)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given ImportActivitySearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant results.
     */
    private Predicate getExpression(final ImportActivitySearchCriteria criteria) {
        BooleanExpression expression = importActivity.id.isNotNull();
        expression = addTenantFilter(expression, importActivity.tenantId, true);
        if (criteria.getImportType() != null && !criteria.getImportType().isEmpty()) {
            expression = expression.and(importActivity.importType.in(criteria.getImportType()));
        } else {
            // exclude order imports
            expression = expression.and(importActivity.importType.ne("Order"));
        }
        if (criteria.getUploadedBy() != null && !criteria.getUploadedBy().isEmpty()) {
            expression = expression.and(importActivity.fileAttachment.uploadedByUserName.in(criteria.getUploadedBy()));
        }
        return expression;
    }

    private OrderSpecifier getOrderBy(final ImportActivitySearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, importActivity.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<ImportActivity> pathBuilder = new PathBuilder<>(ImportActivity.class, importActivity.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }
}
