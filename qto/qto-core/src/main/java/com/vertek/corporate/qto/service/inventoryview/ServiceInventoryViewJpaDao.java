package com.vertek.corporate.qto.service.inventoryview;

import com.google.common.base.Strings;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.common.Tenant;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.location.inventoryview.InventoryWorklistMeta;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.vertek.corporate.qto.service.inventoryview.QServiceInventoryView.serviceInventoryView;


/**
 * @author rcasey
 * @since 2/23/2023
 */
@Stateless
public class ServiceInventoryViewJpaDao extends AbstractMasterCustomerJpaDao<ServiceInventoryView> {

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
     * Retrieves service views matching the given criteria, for populating the Network Inventory worklist.
     * @param criteria the criteria to filter by.
     * @return matching service views.
     */
    public PaginatedResult<ServiceInventoryView> findInventoryBySearchCriteria(final ServiceInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        return new PaginatedResult<>(new JPAQuery<ServiceInventoryView>(entityManager).from(serviceInventoryView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    public PaginatedResult<ServiceInventoryView> findBySearchCriteriaForLink(final ServiceInventoryViewSearchCriteria criteria) {
        criteria.setLocationId(null);
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        expression = expression.and(serviceInventoryView.active.isTrue());
        expression = expression.and(serviceInventoryView.billable.isTrue());
        expression = expression.and(serviceInventoryView.childIds.isNull());

        //get service ids that are not allowed to be linked because they already exist in the service tree
        Query query = entityManager.createNativeQuery(
                "WITH RECURSIVE ServiceHierarchy AS ( " +
                        "    SELECT service_id, parent_service_id " +
                        "    FROM service " +
                        "    WHERE service_id = :given_service_id " +
                        "    UNION ALL" +
                        "    SELECT s.service_id, s.parent_service_id " +
                        "    FROM service s " +
                        "    JOIN ServiceHierarchy sh ON s.parent_service_id = sh.service_id " +
                        ") " +
                        "SELECT DISTINCT service_id " +
                        "FROM ServiceHierarchy;");
        query.setParameter("given_service_id", criteria.getServiceId());
        List<Integer> serviceIds = query.getResultList();
        if (!serviceIds.isEmpty()) {
            expression = expression.and(serviceInventoryView.id.notIn(
                    serviceIds.stream().map(Integer::longValue).collect(Collectors.toList())));
        }
        return new PaginatedResult<>(new JPAQuery<ServiceInventoryView>(entityManager)
                .from(serviceInventoryView)
                .where(expression)
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
    private Predicate getExpression(final ServiceInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = serviceInventoryView.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, serviceInventoryView.masterCustomerId,
                serviceInventoryView.tenantId, true);

        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    serviceInventoryView.locationId.stringValue().contains(criteria.getSearch())
                            .or(serviceInventoryView.address.contains(criteria.getSearch()))
                            .or(serviceInventoryView.status.contains(criteria.getSearch()))
                            .or(serviceInventoryView.provisioner.contains(criteria.getSearch()))
                            .or(serviceInventoryView.projectManager.contains(criteria.getSearch()))
                            .or(serviceInventoryView.provider.contains(criteria.getSearch()))
                            .or(serviceInventoryView.clientServiceId.contains(criteria.getSearch()))
                            .or(serviceInventoryView.clientLocationId.contains(criteria.getSearch()))
                            .or(serviceInventoryView.companyName.contains(criteria.getSearch()))
                            .or(serviceInventoryView.parentCompanyName.contains(criteria.getSearch()))
                            .or(serviceInventoryView.providerCircuitId.contains(criteria.getSearch()))
                            .or(serviceInventoryView.type.contains(criteria.getSearch()))
                            .or(serviceInventoryView.mrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceInventoryView.nrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceInventoryView.annualRecurringCost.stringValue().contains(criteria.getSearch()))
                            .or(serviceInventoryView.speed.contains(criteria.getSearch()))
                            .or(serviceInventoryView.orderType.contains(criteria.getSearch()))
                            .or(serviceInventoryView.subOrderType.contains(criteria.getSearch()))
                            .or(serviceInventoryView.contractTerm.contains(criteria.getSearch()))
                            .or(serviceInventoryView.accountNumber.contains(criteria.getSearch()))
                            .or(serviceInventoryView.summaryBill.contains(criteria.getSearch()))
                            .or(serviceInventoryView.clientLocationType.contains(criteria.getSearch()))
                            .or(serviceInventoryView.openDisputeMrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceInventoryView.openDisputeNrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceInventoryView.id.stringValue().contains(criteria.getSearch()))
                            .or(serviceInventoryView.serviceBilledTo.contains(criteria.getSearch()))
                            .or(serviceInventoryView.parentCompanyClientId.contains(criteria.getSearch()))
                            .or(serviceInventoryView.endCustomerClientId.contains(criteria.getSearch()))
                            .or(serviceInventoryView.summaryBill.contains(criteria.getSearch()))
                            .or(serviceInventoryView.contractTerm.contains(criteria.getSearch()))
                            .or(serviceInventoryView.clientLocationType.contains(criteria.getSearch()))
                            .or(serviceInventoryView.clientLocationInfo.contains(criteria.getSearch()))
                            .or(serviceInventoryView.speed.contains(criteria.getSearch()))
                            .or(serviceInventoryView.activeInactive.contains(criteria.getSearch()))
            );
        }
        if (criteria.isDisputeOpen()) {
            expression = expression.and(serviceInventoryView.countOpenDisputes.gt(0L));
        }
        if (criteria.isMacdOpen()) {
            expression = expression.and(serviceInventoryView.childIds.length().gt(0L));
        }

        if (criteria.getLocationId() != null) {
            expression = expression.and(serviceInventoryView.locationId.eq(criteria.getLocationId()));
        }
        if (criteria.getCompanyId() != null) {
            expression = expression.and(serviceInventoryView.companyId.eq(criteria.getCompanyId()));
        }
        if (!"All".equals(criteria.getActiveInactive())) {
            if(Strings.isNullOrEmpty(criteria.getActiveInactive())){
                criteria.setActiveInactive("Active");
            }
            expression = expression.and(serviceInventoryView.activeInactive.eq(criteria.getActiveInactive()));
        }
        expression = getContainsExpression(expression, serviceInventoryView.parentCompanyClientId, criteria.getParentCompanyClientId());
        expression = getContainsExpression(expression, serviceInventoryView.endCustomerClientId, criteria.getEndCustomerClientId());
        expression = getContainsExpression(expression, serviceInventoryView.address, criteria.getAddress());
        expression = getContainsExpression(expression, serviceInventoryView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, serviceInventoryView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, serviceInventoryView.city, criteria.getCity());
        expression = getContainsExpression(expression, serviceInventoryView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, serviceInventoryView.postalCode, criteria.getPostalCode());
        expression = getContainsExpression(expression, serviceInventoryView.contractTerm, criteria.getContractTerm());
        expression = getContainsExpression(expression, serviceInventoryView.disputeTypes, criteria.getDisputeTypes());
//        expression = getContainsExpression(expression, serviceInventoryView.openDisputeNrc, criteria.getOpenDisputeNrc());
        expression = getContainsExpression(expression, serviceInventoryView.accountNumber, criteria.getAccountNumber());
        expression = getContainsExpression(expression, serviceInventoryView.summaryBill, criteria.getSummaryBill());
        expression = getContainsExpression(expression, serviceInventoryView.serviceBilledTo, criteria.getServiceBilledTo());
        expression = getInExpression(expression, serviceInventoryView.status, criteria.getStatus());
        expression = getInExpression(expression, serviceInventoryView.provisioner, criteria.getProvisioner());
        expression = getInExpression(expression, serviceInventoryView.projectManager, criteria.getProjectManager());
        expression = getInExpression(expression, serviceInventoryView.provider, criteria.getProvider());
        expression = getContainsExpression(expression, serviceInventoryView.clientLocationId, criteria.getClientLocationId());
        expression = getContainsExpression(expression, serviceInventoryView.clientServiceId, criteria.getClientServiceId());
        expression = getContainsExpression(expression, serviceInventoryView.clientLocationInfo, criteria.getClientLocationInfo());
        expression = getContainsExpression(expression, serviceInventoryView.clientLocationType, criteria.getClientLocationType());
        expression = getContainsExpression(expression, serviceInventoryView.companyName, criteria.getCompanyName());
        expression = getInExpression(expression, serviceInventoryView.parentCompanyName, criteria.getParentCompanyName());
        expression = getContainsExpression(expression, serviceInventoryView.providerCircuitId, criteria.getProviderCircuitId());
        expression = getInExpression(expression, serviceInventoryView.type, criteria.getServiceType());
        expression = getContainsExpression(expression, serviceInventoryView.speed, criteria.getSpeed());
        expression = getInExpression(expression, serviceInventoryView.childOrderTypes, criteria.getOrderType());
        expression = getInExpression(expression, serviceInventoryView.childSubOrderTypes, criteria.getSubOrderType());

        expression = getNumericComparisonExpression(expression, serviceInventoryView.mrc, criteria.getMrc(), criteria.getMrcRange());
        expression = getNumericComparisonExpression(expression, serviceInventoryView.nrc, criteria.getNrc(), criteria.getNrcRange());
        expression = getNumericComparisonExpression(expression, serviceInventoryView.annualRecurringCost, criteria.getAnnualRecurringCost(), criteria.getAnnualRecurringCostRange());


        return expression;
    }

    private OrderSpecifier getOrderBy(final ServiceInventoryViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, serviceInventoryView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<ServiceInventoryView> pathBuilder = new PathBuilder<>(ServiceInventoryView.class, serviceInventoryView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    public InventoryWorklistMeta getInventoryWorklistMeta(final ServiceInventoryViewSearchCriteria criteria) {
        BooleanExpression expression = (BooleanExpression) getExpression(criteria);
        StringTemplate childIdsCount = Expressions.stringTemplate("(CHAR_LENGTH({0}) - CHAR_LENGTH(REPLACE({0}, ',', '')) + 1)", serviceInventoryView.childIds);
        StringTemplate countNew = Expressions.stringTemplate(
                "(CHAR_LENGTH({0}) - CHAR_LENGTH(REPLACE({0}, 'New', ''))) / 3",
                serviceInventoryView.childOrderTypes
        );

        NumberTemplate<Long> sumChildIdsCount = Expressions.numberTemplate(Long.class, "SUM(" + childIdsCount + ")");
        NumberTemplate<Long> sumCountNew = Expressions.numberTemplate(Long.class, "SUM(" + countNew + ")");

        Tuple metaCounts = new JPAQuery<Tuple>(entityManager)
                .from(serviceInventoryView)
                .select(
                        serviceInventoryView.mrc.sum(),
                        serviceInventoryView.openDisputeMrc.sum(),
                        serviceInventoryView.openDisputeNrc.sum(),
                        serviceInventoryView.annualRecurringCost.sum(),
                        sumChildIdsCount,
                        sumCountNew,
                        serviceInventoryView.mrr.sum(),
                        serviceInventoryView.nrr.sum()
                ).where(expression)
                .fetchOne();

        InventoryWorklistMeta meta = new InventoryWorklistMeta();
        meta.setMrc(metaCounts.get(0, BigDecimal.class));
        meta.setOpenDisputeMrc(metaCounts.get(1, BigDecimal.class));
        meta.setOpenDisputeNrc(metaCounts.get(2, BigDecimal.class));
        meta.setAnnualRecurring(metaCounts.get(3, BigDecimal.class));
        meta.setMrr(metaCounts.get(6, BigDecimal.class));
        meta.setNrr(metaCounts.get(7, BigDecimal.class));

        Long macdCount = metaCounts.get(4, Long.class) == null ? 0L : metaCounts.get(4, Long.class);
        Long newCount = metaCounts.get(5, Long.class) == null ? 0L : metaCounts.get(5, Long.class);
        meta.setMacdCount(macdCount - newCount);
        return meta;
    }

    public PaginatedResult<ServiceInventoryView> getServiceInventoryViewsForLink(ServiceInventoryViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ServiceInventoryView>(entityManager)
                .from(serviceInventoryView)
                .where(serviceInventoryView.locationId.eq(criteria.getLocationId())
                        .and(serviceInventoryView.active.isTrue())
                        .and(serviceInventoryView.linked.isFalse())
                        .and(serviceInventoryView.bundled.isFalse())
                        .and(serviceInventoryView.id.ne(criteria.getServiceId()))
                        .and(serviceInventoryView.status.ne("Disconnect Complete")))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

     public PaginatedResult<ServiceInventoryView> getServiceInventoryViewsForBundle(ServiceInventoryViewSearchCriteria criteria) {

        ServiceInventoryView existing = retrieve(criteria.getServiceId());
        List<String> terminalStatuses = Arrays.asList("Service Cancelled", "Change In Assignment");
        BooleanExpression expression = serviceInventoryView.locationId.eq(criteria.getLocationId())
                .and(serviceInventoryView.linked.isFalse())
                .and(serviceInventoryView.bundled.isFalse())
                .and(serviceInventoryView.id.ne(criteria.getServiceId()))
                .and(serviceInventoryView.status.notIn(terminalStatuses)
                .and(serviceInventoryView.status.ne("Disconnect Complete")));

        if (existing.getProvider() != null) {
            expression = expression.and(serviceInventoryView.provider.eq(existing.getProvider())
                    .or(serviceInventoryView.provider.isNull()));
        } else {
            expression = expression.and(serviceInventoryView.provider.isNull());
        }


        return new PaginatedResult<>(new JPAQuery<ServiceInventoryView>(entityManager)
                .from(serviceInventoryView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());


    }

    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(serviceInventoryView.type)
                .from(serviceInventoryView)
                .where(serviceInventoryView.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
