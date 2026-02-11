package com.endeavorms.velocity.qto.disconnect;

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
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.*;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static com.endeavorms.velocity.qto.disconnect.QDisconnectView.disconnectView;

/**
 * @author fcurran
 * @since 1.3.0
 */
@Component
public class DisconnectViewJpaDao extends AbstractMasterCustomerJpaDao<DisconnectView> {

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
     * Retrieves all disconnect views matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<DisconnectView> findBySearchCriteria(final DisconnectViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<DisconnectView>(entityManager)
                .from(disconnectView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given DisconnectViewSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevent where clause.
     */
    private Predicate getExpression(final DisconnectViewSearchCriteria criteria) {
        BooleanExpression expression = disconnectView.id.isNotNull();
        expression = expression.and(disconnectView.orderType.eq("Disconnect"));
        expression = addMasterCustomerAndTenantFilter(expression, disconnectView.masterCustomerId,
                disconnectView.tenantId, true);

        //search
        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    disconnectView.locationId.stringValue().contains(criteria.getSearch())
                            .or(disconnectView.clientLocationId.contains(criteria.getSearch()))
                            .or(disconnectView.companyName.contains(criteria.getSearch()))
                            .or(disconnectView.parentCompanyName.contains(criteria.getSearch()))
                            .or(disconnectView.type.contains(criteria.getSearch()))
                            .or(disconnectView.address.contains(criteria.getSearch()))
                            .or(disconnectView.provisioner.contains(criteria.getSearch()))
                            .or(disconnectView.disconnectReason.contains(criteria.getSearch()))
                            .or(disconnectView.status.contains(criteria.getSearch()))
                            .or(disconnectView.provider.contains(criteria.getSearch()))
                            .or(disconnectView.providerOrderSubmitted.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.providerOrderNumber.contains(criteria.getSearch()))
                            .or(disconnectView.customerRequestedDisconnect.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.networkProviderFoc.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.complete.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.created.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.statusAge.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.mrc.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.earlyTerminationFee.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.latestNote.contains(criteria.getSearch()))
                            .or(disconnectView.billingReviewComplete.stringValue().contains(criteria.getSearch()))
                            .or(disconnectView.projectName.contains(criteria.getSearch()))
                            .or(disconnectView.serviceBilledTo.contains(criteria.getSearch()))
                            .or(disconnectView.parentCompanyClientId.contains(criteria.getSearch()))
                            .or(disconnectView.endCustomerClientId.contains(criteria.getSearch()))
                            .or(disconnectView.parentCompanyName.contains(criteria.getSearch()))
                            .or(disconnectView.parentCompanyClientId.contains(criteria.getSearch()))
                            .or(disconnectView.clientServiceId.contains(criteria.getSearch()))
                            .or(disconnectView.latestNote.contains(criteria.getSearch()))

            );
        }

        //quick filters
        if (criteria.isPendingDisconnect()) {
            List<String> terminalStatuses = Arrays.asList("Disconnect Complete", "Service Cancelled", "Disconnect Cancelled", "Change In Assignment");
            expression = expression.and(disconnectView.status.notIn(terminalStatuses));
        }
        //advanced filters
        if (criteria.getLocationId() != null) {
            expression = expression.and(disconnectView.locationId.eq(criteria.getLocationId()));
        }
        expression = getContainsExpression(expression, disconnectView.clientLocationId, criteria.getClientLocationId());
        expression = getContainsExpression(expression, disconnectView.companyName, criteria.getCompanyName());
        expression = getContainsExpression(expression, disconnectView.parentCompanyName, criteria.getParentCompanyName());
        expression = getContainsExpression(expression, disconnectView.type, criteria.getServiceType());
        expression = getContainsExpression(expression, disconnectView.address, criteria.getAddress());
        expression = getContainsExpression(expression, disconnectView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, disconnectView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, disconnectView.city, criteria.getCity());
        expression = getContainsExpression(expression, disconnectView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, disconnectView.postalCode, criteria.getPostalCode());
        expression = getContainsExpression(expression, disconnectView.provisioner, criteria.getProvisioner());
        expression = getContainsExpression(expression, disconnectView.serviceBilledTo, criteria.getServiceBilledTo());
        expression = getContainsExpression(expression, disconnectView.disconnectReason, criteria.getDisconnectReason());
        expression = getContainsExpression(expression, disconnectView.status, criteria.getStatus());
        expression = getContainsExpression(expression, disconnectView.provider, criteria.getProvider());
        expression = getDateComparisonExpression(expression, disconnectView.providerOrderSubmitted, criteria.getProviderOrderSubmitted(), criteria.getProviderOrderSubmittedRange());
        expression = getContainsExpression(expression, disconnectView.providerOrderNumber, criteria.getProviderOrderNumber());
        expression = getDateComparisonExpression(expression, disconnectView.customerRequestedDisconnect, criteria.getCustomerRequestedDisconnect(), criteria.getCustomerRequestedDisconnectRange());
        expression = getDateComparisonExpression(expression, disconnectView.networkProviderFoc, criteria.getNetworkProviderFoc(), criteria.getNetworkProviderFocRange());
        expression = getDateComparisonExpression(expression, disconnectView.complete, criteria.getComplete(), criteria.getCompleteRange());
        expression = getDateComparisonExpression(expression, disconnectView.created, criteria.getCreated(), criteria.getCreatedRange());
        expression = getNumericComparisonExpression(expression, disconnectView.statusAge, criteria.getStatusAge(), criteria.getStatusAgeRange());
        expression = getNumericComparisonExpression(expression, disconnectView.mrc, criteria.getMrc(), criteria.getMrcRange());
        expression = getNumericComparisonExpression(expression, disconnectView.earlyTerminationFee, criteria.getEarlyTerminationFee(), criteria.getEarlyTerminationFeeRange());
        expression = getContainsExpression(expression, disconnectView.latestNote, criteria.getLatestNote());
        expression = getDateComparisonExpression(expression, disconnectView.billingReviewComplete, criteria.getBillingReviewComplete(), criteria.getBillingReviewCompleteRange());
        expression = getContainsExpression(expression, disconnectView.projectName, criteria.getProjectName());
        expression = getContainsExpression(expression, disconnectView.parentCompanyClientId, criteria.getParentCompanyClientId());
        expression = getContainsExpression(expression, disconnectView.endCustomerClientId, criteria.getEndCustomerClientId());
        return expression;
    }

    private OrderSpecifier getOrderBy(final DisconnectViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, disconnectView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<DisconnectView> pathBuilder
                    = new PathBuilder<>(DisconnectView.class, disconnectView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    public DisconnectWorklistMeta getWorklistMeta(final DisconnectViewSearchCriteria criteria) {

        NumberPath<BigDecimal> mrc = Expressions.numberPath(BigDecimal.class, "mrc");
        NumberPath<BigDecimal> mrr = Expressions.numberPath(BigDecimal.class, "mrr");
        NumberPath<BigDecimal> earlyTerminationFee = Expressions.numberPath(BigDecimal.class, "earlyTerminationFee");

        Expression<BigDecimal> sumMrcExpression = Expressions.cases()
                .when(mrc.sum().isNull()).then(BigDecimal.ZERO)
                .otherwise(mrc.sum());

        Expression<BigDecimal> sumMrrExpression = Expressions.cases()
                .when(mrr.sum().isNull()).then(BigDecimal.ZERO)
                .otherwise(mrr.sum());

        Expression<BigDecimal> sumETFExpression = Expressions.cases()
                .when(earlyTerminationFee.sum().isNull()).then(BigDecimal.ZERO)
                .otherwise(earlyTerminationFee.sum());

        Tuple metaCounts = new JPAQuery<Tuple>(entityManager)
                .from(disconnectView)
                .select(
                        sumMrcExpression,
                        sumETFExpression,
                        sumMrrExpression
                )
                .where(getExpression(criteria))
                .fetchOne();

        DisconnectWorklistMeta meta = new DisconnectWorklistMeta();
        meta.setTotalMrc(metaCounts.get(sumMrcExpression));
        meta.setTotalMrr(metaCounts.get(sumMrrExpression));
        meta.setTotalEarlyTerminationFee(metaCounts.get(sumETFExpression));

        return meta;
    }

    /**
     * Gets the services types for the worklist.
     * @return The service types for the worklist.
     */
    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(disconnectView.type)
                .from(disconnectView)
                .where(disconnectView.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
