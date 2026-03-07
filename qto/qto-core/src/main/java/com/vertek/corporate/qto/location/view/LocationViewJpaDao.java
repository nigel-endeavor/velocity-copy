package com.vertek.corporate.qto.location.view;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.RecordSource;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.*;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vertek.corporate.qto.location.view.QLocationView.locationView;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Stateless
public class LocationViewJpaDao extends AbstractMasterCustomerJpaDao<LocationView> {
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
     * Retrieves all location views matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<LocationView> findBySearchCriteria(final LocationViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<LocationView>(entityManager).from(locationView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * retrieves locationViews for relocate Service feature to populate the relocate service worklist.
     * @param criteria
     * @return
     */
    public PaginatedResult<LocationView> findBySearchCriteriaForServiceRelocate(final LocationViewSearchCriteria criteria) {
        criteria.setHideTerminalStatuses(true);
        BooleanExpression expression = (BooleanExpression)getExpression(criteria);
         if (criteria.getRelocateLocationId() != null) {
             expression = expression.and(locationView.id.ne(criteria.getRelocateLocationId()));
         }
        return new PaginatedResult<>(new JPAQuery<LocationView>(entityManager).from(locationView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given LocationViewSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final LocationViewSearchCriteria criteria) {
        BooleanExpression expression = locationView.id.isNotNull();
        if (Strings.isNullOrEmpty(criteria.getRecordSource())) {
            expression = expression.and(locationView.recordSource.ne(RecordSource.INVENTORY_IMPORT.getName()));
        }
        expression = addMasterCustomerAndTenantFilter(expression, locationView.masterCustomerId, locationView.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                            locationView.id.stringValue().contains(criteria.getSearch())
                                    .or(locationView.companyName.contains(criteria.getSearch()))
                                    .or(locationView.address.contains(criteria.getSearch()))
                                    .or(locationView.parentCompanyName.contains(criteria.getSearch()))
                                    .or(locationView.provisioner.contains(criteria.getSearch()))
                                    .or(locationView.clientOrderId.contains(criteria.getSearch()))
                                    .or(locationView.clientLocationId.contains(criteria.getSearch()))
                                    .or(locationView.locationName.contains(criteria.getSearch()))
                                    .or(locationView.locationStatus.contains(criteria.getSearch()))
                                    .or(locationView.countServices.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.services.contains(criteria.getSearch()))
                                    .or(locationView.completionDate.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.openJeops.contains(criteria.getSearch()))
                                    .or(locationView.openJeopResponsibilities.contains(criteria.getSearch()))
                                    .or(locationView.orderId.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.clientProjectManager.contains(criteria.getSearch()))
                                    .or(locationView.clientLocationInfo.contains(criteria.getSearch()))
                                    .or(locationView.clientLocationType.contains(criteria.getSearch()))
                                    .or(locationView.mrc.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.nrc.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.annualRecurringCost.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.parentCompanyClientId.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.endCustomerClientId.stringValue().contains(criteria.getSearch()))
                                    .or(locationView.vertekProjectManager.contains(criteria.getSearch()))
                                    .or(locationView.clientProjectManager.contains(criteria.getSearch()))
                                    .or(locationView.recordSource.contains(criteria.getSearch()))
            );
        }

        if (criteria.getHideTerminalStatuses()) {
            List<String> terminalStatuses = Arrays.asList("Location Complete", "Location Cancelled", "Change In Assignment");
            expression = expression.and(locationView.locationStatus.notIn(terminalStatuses));
        }
        if (criteria.isMacOnly()) {
            expression = expression.and(locationView.macCount.gt(0));
        }

        if (criteria.getActiveOnly()) {
            expression = expression.and(locationView.active.isTrue());
        }

        if (criteria.getId() != null) {
            expression = expression.and(locationView.id.eq(criteria.getId()));
        }

        if (criteria.getOrderId() != null){
            expression = expression.and(locationView.orderId.eq(criteria.getOrderId()));
        }

        if (criteria.getParentCompanyId() != null){
            expression = expression.and(locationView.masterCustomerId.eq(criteria.getParentCompanyId()));
        }

        expression = getContainsExpression(expression, locationView.address, criteria.getAddress());
        expression = getContainsExpression(expression, locationView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, locationView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, locationView.city, criteria.getCity());
        expression = getContainsExpression(expression, locationView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, locationView.postalCode, criteria.getPostalCode());
        expression = getContainsExpression(expression, locationView.companyName, criteria.getCompanyName());
        expression = getContainsExpression(expression, locationView.parentCompanyName, criteria.getParentCompanyName());
        expression = getInExpression(expression, locationView.provisioner, criteria.getProvisioner());
        expression = getContainsExpression(expression, locationView.clientOrderId, criteria.getClientOrderId());
        expression = getContainsExpression(expression, locationView.clientLocationId, criteria.getClientLocationId());
        expression = getContainsExpression(expression, locationView.locationName, criteria.getLocationName());
        expression = getContainsExpression(expression, locationView.clientProjectManager, criteria.getClientProjectManager());
        expression = getContainsExpression(expression, locationView.vertekProjectManager, criteria.getVertekProjectManager());
        expression = getContainsExpression(expression, locationView.clientLocationInfo, criteria.getClientLocationInfo());
        expression = getContainsExpression(expression, locationView.clientLocationType, criteria.getClientLocationType());
        expression = getInExpression(expression, locationView.locationStatus, criteria.getLocationStatus());
        expression = getContainsExpression(expression, locationView.parentCompanyClientId, criteria.getParentCompanyClientId());
        expression = getContainsExpression(expression, locationView.endCustomerClientId, criteria.getEndCustomerClientId());
        if (!criteria.getCountServices().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationView.countServices, criteria.getCountServices(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (!criteria.getMrc().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationView.mrc, criteria.getMrc(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (!criteria.getNrc().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationView.nrc, criteria.getNrc(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (!criteria.getAnnualRecurringCost().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationView.annualRecurringCost, criteria.getAnnualRecurringCost(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        expression = getContainsExpression(expression, locationView.services, criteria.getServices());
        expression = getDateComparisonExpression(expression, locationView.completionDate, criteria.getCompletionDate(),
                criteria.getCompletionDateComparison());
        expression = getContainsExpression(expression, locationView.openJeops, criteria.getOpenJeops());
        expression = getContainsExpression(expression, locationView.openJeopResponsibilities,
                criteria.getOpenJeopResponsibilities());

        return expression;
    }

    private OrderSpecifier getOrderBy(final LocationViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, locationView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<LocationView> pathBuilder = new PathBuilder<>(LocationView.class, locationView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    /**
     * Gets the services types for the worklist.
     * @return The service types for the worklist.
     */
    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(locationView.services)
                .from(locationView)
                .where(locationView.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
