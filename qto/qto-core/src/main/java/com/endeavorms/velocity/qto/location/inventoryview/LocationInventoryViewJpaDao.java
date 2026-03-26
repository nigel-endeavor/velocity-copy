package com.endeavorms.velocity.qto.location.inventoryview;

import com.google.common.base.Strings;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.*;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.endeavorms.velocity.qto.location.inventoryview.QLocationInventoryView.locationInventoryView;


/**
 * @author rcasey
 * @since 1/9/2023
 */
@Component
public class LocationInventoryViewJpaDao extends AbstractMasterCustomerJpaDao<LocationInventoryView> {

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
     * Retrieves location views matching the given criteria, for populating the Network Inventory worklist.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<LocationInventoryView> findInventoryBySearchCriteria(final LocationInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        PaginatedResult<LocationInventoryView> result = new PaginatedResult<>(new JPAQuery<LocationInventoryView>(entityManager).from(locationInventoryView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
        return result;
    }

    public PaginatedResult<LocationInventoryView> findBySearchCriteriaForLink(final LocationInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        expression = expression.and(locationInventoryView.locationStatus.eq("Location Complete"));
        return new PaginatedResult<>(new JPAQuery<LocationInventoryView>(entityManager)
                .from(locationInventoryView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given LocationInventoryViewSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    private Predicate getExpression(final LocationInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = locationInventoryView.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, locationInventoryView.masterCustomerId, locationInventoryView.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                            locationInventoryView.id.stringValue().contains(criteria.getSearch())
                                    .or(locationInventoryView.orderId.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.companyName.contains(criteria.getSearch()))
                                    .or(locationInventoryView.address.contains(criteria.getSearch()))
                                    .or(locationInventoryView.parentCompanyName.contains(criteria.getSearch()))
                                    .or(locationInventoryView.provisioner.contains(criteria.getSearch()))
                                    .or(locationInventoryView.clientOrderId.contains(criteria.getSearch()))
                                    .or(locationInventoryView.clientLocationId.contains(criteria.getSearch()))
                                    .or(locationInventoryView.locationName.contains(criteria.getSearch()))
                                    .or(locationInventoryView.locationStatus.contains(criteria.getSearch()))
                                    .or(locationInventoryView.activeServiceCount.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.services.contains(criteria.getSearch()))
                                    .or(locationInventoryView.inventoryAddedDate.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.macdCount.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.subOrderTypes.contains(criteria.getSearch()))
                                    .or(locationInventoryView.openDisputeMrc.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.openDisputeNrc.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.activeCompleteMrc.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.activeCompleteNrc.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.annualRecurringCost.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.parentCompanyClientId.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.endCustomerClientId.stringValue().contains(criteria.getSearch()))
                                    .or(locationInventoryView.clientProjectManager.contains(criteria.getSearch()))
                                    .or(locationInventoryView.vertekProjectManager.contains(criteria.getSearch()))
                                    .or(locationInventoryView.clientLocationInfo.contains(criteria.getSearch()))
                                    .or(locationInventoryView.clientLocationType.contains(criteria.getSearch()))
            );
        }

//        if (criteria.getActiveOnly()) {
//            expression = expression.and(locationInventoryView.active.isTrue());
//        }
        if (criteria.isMacdOpen()) {
            expression = expression.and(locationInventoryView.macdCount.gt(0L));
        }
        if (criteria.isDisputeOpen()) {
            expression = expression.and(locationInventoryView.countOpenDisputes.gt(0L));
        }

        if (criteria.getId() != null) {
            expression = expression.and(locationInventoryView.id.eq(criteria.getId()));
        }
        if (criteria.getOrderId() != null) {
            expression = expression.and(locationInventoryView.orderId.eq(criteria.getOrderId()));
        }
        if (!"All".equals(criteria.getActiveInactive())) {
            if(Strings.isNullOrEmpty(criteria.getActiveInactive())){
                criteria.setActiveInactive("Active");
            }
            expression = expression.and(locationInventoryView.activeInactive.eq(criteria.getActiveInactive()));
        }

        expression = getContainsExpression(expression, locationInventoryView.address, criteria.getAddress());
        expression = getContainsExpression(expression, locationInventoryView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, locationInventoryView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, locationInventoryView.city, criteria.getCity());
        expression = getContainsExpression(expression, locationInventoryView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, locationInventoryView.postalCode, criteria.getPostalCode());
        expression = getContainsExpression(expression, locationInventoryView.companyName, criteria.getCompanyName());
        expression = getInExpression(expression, locationInventoryView.parentCompanyName, criteria.getParentCompanyName());
        expression = getContainsExpression(expression, locationInventoryView.provisioner, criteria.getProvisioner());
        expression = getContainsExpression(expression, locationInventoryView.clientOrderId, criteria.getClientOrderId());
        expression = getContainsExpression(expression, locationInventoryView.clientLocationId, criteria.getClientLocationId());
        expression = getContainsExpression(expression, locationInventoryView.locationName, criteria.getLocationName());
        expression = getContainsExpression(expression, locationInventoryView.locationStatus, criteria.getLocationStatus());
        expression = getContainsExpression(expression, locationInventoryView.parentCompanyClientId, criteria.getParentCompanyClientId());
        expression = getContainsExpression(expression, locationInventoryView.endCustomerClientId, criteria.getEndCustomerClientId());
        expression = getInExpression(expression, locationInventoryView.subOrderTypes, criteria.getSubOrderTypes());
        if (criteria.getCountServices() != null && !criteria.getCountServices().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationInventoryView.countServices, criteria.getCountServices(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (criteria.getActiveServiceCount() != null && !criteria.getActiveServiceCount().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationInventoryView.activeServiceCount, criteria.getActiveServiceCount(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        expression = getDateComparisonExpression(expression, locationInventoryView.inventoryAddedDate,
                criteria.getInventoryAddedDate(), criteria.getInventoryAddedDateComparison());
        if (criteria.getMacdCount() != null && !criteria.getMacdCount().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationInventoryView.macdCount, criteria.getMacdCount(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (criteria.getParentCompanyId() != null && !criteria.getParentCompanyId().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationInventoryView.masterCustomerId, criteria.getParentCompanyId(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (criteria.getOpenDisputeMrc() != null && !criteria.getOpenDisputeMrc().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationInventoryView.openDisputeMrc, criteria.getOpenDisputeMrc(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        if (criteria.getOpenDisputeNrc() != null && !criteria.getOpenDisputeNrc().isEmpty()) {
            expression = getNumericComparisonExpression(expression, locationInventoryView.openDisputeNrc, criteria.getOpenDisputeNrc(),
                    new ArrayList<>(List.of(RangeType.EQ)));
        }
        expression = getNumericComparisonExpression(expression, locationInventoryView.activeCompleteMrc, criteria.getActiveCompleteMrc(), criteria.getActiveCompleteMrcRange());
        expression = getNumericComparisonExpression(expression, locationInventoryView.activeCompleteNrc, criteria.getActiveCompleteNrc(), criteria.getActiveCompleteNrcRange());
        expression = getNumericComparisonExpression(expression, locationInventoryView.annualRecurringCost, criteria.getAnnualRecurringCost(), criteria.getAnnualRecurringCostRange());

        // Doing the services filter this way because for some reason if you use the getContainsExpression method it
        // returns the services all lowercase
        if (criteria.getServices() != null) {
            for (String service : criteria.getServices()) {
                expression = expression.and(locationInventoryView.services.like("%" + service.toLowerCase() + "%"));
            }
        }

        return expression;
    }

    private OrderSpecifier getOrderBy(final LocationInventoryViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, locationInventoryView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<LocationInventoryView> pathBuilder = new PathBuilder<>(LocationInventoryView.class, locationInventoryView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    public InventoryWorklistMeta getInventoryWorklistMeta(final LocationInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        Tuple metaCounts = new JPAQuery<Tuple>(entityManager)
                .from(locationInventoryView)
                .select(
                        locationInventoryView.activeCompleteMrc.sum(),
                        locationInventoryView.openDisputeMrc.sum(),
                        locationInventoryView.openDisputeNrc.sum(),
                        locationInventoryView.annualRecurringCost.sum(),
                        locationInventoryView.macdCount.sum(),
                        locationInventoryView.activeCompleteMrr.sum(),
                        locationInventoryView.activeCompleteNrr.sum()
                ).where(expression)
                .fetchOne();
        InventoryWorklistMeta meta = new InventoryWorklistMeta();
        meta.setMrc(metaCounts.get(0, BigDecimal.class));
        meta.setAnnualRecurring(metaCounts.get(3, BigDecimal.class));
        meta.setOpenDisputeMrc(metaCounts.get(1, BigDecimal.class));
        meta.setOpenDisputeNrc(metaCounts.get(2, BigDecimal.class));
        meta.setMacdCount(metaCounts.get(4, Long.class));
        meta.setMrr(metaCounts.get(5, BigDecimal.class));
        meta.setNrr(metaCounts.get(6, BigDecimal.class));
        return meta;
    }

    /**
     * Retrieves location views for the inventory service relocation.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<LocationInventoryView> findBySearchCriteriaForServiceRelocate(
            final LocationInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        if (criteria.getRelocateLocationId() != null) {
            expression = expression.and(locationInventoryView.id.ne(criteria.getRelocateLocationId()));
        }
        return new PaginatedResult<>(new JPAQuery<LocationInventoryView>(entityManager).from(locationInventoryView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(locationInventoryView.services)
                .from(locationInventoryView)
                .where(locationInventoryView.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
