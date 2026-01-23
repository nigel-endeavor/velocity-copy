package com.vertek.corporate.qto.dispute;

import com.google.common.base.Strings;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.vertek.corporate.qto.dispute.QDisputeView.disputeView;

/**
 * @author fcurran
 *  * @since 9/18/2023
 */
@Stateless
public class DisputeViewJpaDao extends AbstractMasterCustomerJpaDao<DisputeView> {

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

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    /**
     * Retrieves all service views matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<DisputeView> findBySearchCriteria(final DisputeViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<DisputeView>(entityManager)
                .from(disputeView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }


    /**
     * Gets the where clause for a given ServiceViewSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevent where clause.
     */
    private Predicate getExpression(final DisputeViewSearchCriteria criteria) {
        BooleanExpression expression = disputeView.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, disputeView.masterCustomerId, disputeView.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression
                    .and(disputeView.locationId.stringValue().contains(criteria.getSearch())
                    .or(disputeView.parentCompanyName.contains(criteria.getSearch()))
                    .or(disputeView.companyName.contains(criteria.getSearch()))
                    .or(disputeView.address.contains(criteria.getSearch()))
                    .or(disputeView.serviceType.contains(criteria.getSearch()))
                    .or(disputeView.disputeType.contains(criteria.getSearch()))
                    .or(disputeView.disputeStatus.contains(criteria.getSearch()))
                    .or(disputeView.provider.contains(criteria.getSearch()))
                    .or(disputeView.amountDisputedMrc.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.amountDisputedNrc.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.openDate.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.billingReviewCompleteDate.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.disputeClosedDate.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.serviceMrc.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.serviceNrc.stringValue().contains(criteria.getSearch()))
                    .or(disputeView.disputeAssignment.contains(criteria.getSearch()))
                    .or(disputeView.serviceBilledTo.contains(criteria.getSearch()))
                    .or(disputeView.clientServiceId.contains(criteria.getSearch()))
                    .or(disputeView.clientLocationId.contains(criteria.getSearch()))
            );
        }

        if (criteria.getLocationId() != null) {
            expression = expression.and(disputeView.locationId.eq(criteria.getLocationId()));
        }
        if (criteria.isDisputeOpen()) {
            expression = expression.and(disputeView.openDate.isNotNull()).and(disputeView.disputeClosedDate.isNull());
        }

        expression = getContainsExpression(expression, disputeView.address, criteria.getAddress());
        expression = getContainsExpression(expression, disputeView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, disputeView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, disputeView.city, criteria.getCity());
        expression = getContainsExpression(expression, disputeView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, disputeView.postalCode, criteria.getPostalCode());
        expression = getContainsExpression(expression, disputeView.parentCompanyName, criteria.getParentCompanyName());
        expression = getContainsExpression(expression, disputeView.companyName, criteria.getCompanyName());
        expression = getContainsExpression(expression, disputeView.serviceType, criteria.getServiceType());
        expression = getContainsExpression(expression, disputeView.disputeType, criteria.getDisputeType());
        expression = getContainsExpression(expression, disputeView.disputeStatus, criteria.getDisputeStatus());
        expression = getContainsExpression(expression, disputeView.disputeAssignment, criteria.getDisputeAssignment());
        expression = getContainsExpression(expression, disputeView.provider, criteria.getProvider());
        expression = getContainsExpression(expression, disputeView.serviceBilledTo, criteria.getServiceBilledTo());
        expression = getContainsExpression(expression, disputeView.speed, criteria.getSpeed());
        expression = getNumericComparisonExpression(expression, disputeView.amountDisputedMrc, criteria.getAmountDisputedMrc(), criteria.getAmountDisputedMrcRange());
        expression = getNumericComparisonExpression(expression, disputeView.amountDisputedNrc, criteria.getAmountDisputedNrc(), criteria.getAmountDisputedNrcRange());
        expression = getDateComparisonExpression(expression, disputeView.openDate, criteria.getOpenDate(), criteria.getOpenDateRange());
        expression = getDateComparisonExpression(expression, disputeView.billingReviewCompleteDate, criteria.getBillingReviewCompleteDate(), criteria.getBillingReviewCompleteDateRange());
        expression = getDateComparisonExpression(expression, disputeView.disputeClosedDate, criteria.getDisputeClosedDate(), criteria.getDisputeClosedDateRange());
        //icb
        expression = getNumericComparisonExpression(expression, disputeView.serviceMrc, criteria.getServiceMrc(), criteria.getServiceMrcRange());
        expression = getNumericComparisonExpression(expression, disputeView.serviceNrc, criteria.getServiceNrc(), criteria.getServiceNrcRange());

        return expression;
    }

    private OrderSpecifier getOrderBy(final DisputeViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, disputeView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<DisputeView> pathBuilder = new PathBuilder<>(DisputeView.class, disputeView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    /**
     * Gets the meta data for the worklist built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the worklist.
     */
    public DisputeWorklistMeta getDisputeWorklistMeta(final DisputeViewSearchCriteria criteria) {
        Tuple metaCounts = new JPAQuery<Tuple>(entityManager)
                .from(disputeView)
                .select(
                        disputeView.amountDisputedMrc.sum(),
                        disputeView.amountDisputedNrc.sum()
                ).where(getExpression(criteria))
                .fetchOne();
        DisputeWorklistMeta meta = new DisputeWorklistMeta();
        meta.setTotalDisputeMrc(metaCounts.get(0, BigDecimal.class));
        meta.setTotalDisputeNrc(metaCounts.get(1, BigDecimal.class));
        return meta;
    }
    /**
     * Gets the services types for the worklist.
     * @return The service types for the worklist.
     */
    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(disputeView.serviceType)
                .from(disputeView)
                .where(disputeView.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
