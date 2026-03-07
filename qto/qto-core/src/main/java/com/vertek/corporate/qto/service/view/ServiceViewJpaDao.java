package com.vertek.corporate.qto.service.view;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.vertek.corporate.qto.service.view.QServiceView.serviceView;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Stateless
public class ServiceViewJpaDao extends AbstractMasterCustomerJpaDao<ServiceView> {

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
     *
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<ServiceView> findBySearchCriteria(final ServiceViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ServiceView>(entityManager)
                .from(serviceView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given ServiceViewSearchCriteria.
     *
     * @param criteria the criteria to filter by.
     * @return relevent where clause.
     */
    private Predicate getExpression(final ServiceViewSearchCriteria criteria) {
        BooleanExpression expression = serviceView.id.isNotNull();
        if (Strings.isNullOrEmpty(criteria.getRecordSource())) {
            expression = expression.and(serviceView.recordSource.ne(RecordSource.INVENTORY_IMPORT.getName()));
        }
        expression = addMasterCustomerAndTenantFilter(expression, serviceView.masterCustomerId, serviceView.tenantId, true);

        expression = expression.and(serviceView.orderType.ne("Disconnect").or(serviceView.orderType.isNull()));
        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    serviceView.address.contains(criteria.getSearch())
                            .or(serviceView.status.contains(criteria.getSearch()))
                            .or(serviceView.provisioner.contains(criteria.getSearch()))
                            .or(serviceView.projectManager.contains(criteria.getSearch()))
                            .or(serviceView.vertekProjectManager.contains(criteria.getSearch()))
                            .or(serviceView.provider.contains(criteria.getSearch()))
                            .or(serviceView.clientLocationType.contains(criteria.getSearch()))
                            .or(serviceView.clientLocationInfo.contains(criteria.getSearch()))
                            .or(serviceView.clientServiceId.contains(criteria.getSearch()))
                            .or(serviceView.clientLocationId.contains(criteria.getSearch()))
                            .or(serviceView.companyName.contains(criteria.getSearch()))
                            .or(serviceView.parentCompanyName.contains(criteria.getSearch()))
                            .or(serviceView.type.contains(criteria.getSearch()))
                            .or(serviceView.serviceBilledTo.contains(criteria.getSearch()))
                            .or(serviceView.lconPhone.contains(criteria.getSearch()))
                            .or(serviceView.mrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.nrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.speed.contains(criteria.getSearch()))
                            .or(serviceView.levelOfEffort.contains(criteria.getSearch()))
                            .or(serviceView.latestNote.contains(criteria.getSearch()))
                            .or(serviceView.qaManager.contains(criteria.getSearch()))
                            .or(serviceView.projectName.contains(criteria.getSearch()))
                            .or(serviceView.recordSource.contains(criteria.getSearch()))
                            .or(serviceView.openJeopResponsibilities.contains(criteria.getSearch()))
                            .or(serviceView.locationId.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.id.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.customerRequestedInstall.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.siteSurveyDue.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.siteSurveySubmit.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.providerOrderSubmitted.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.networkProviderFoc.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.dataProvisioningComplete.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.followUpDate.stringValue().contains(criteria.getSearch()))
                            .or(serviceView.orderType.contains(criteria.getSearch()))
                            .or(serviceView.subStatus.contains(criteria.getSearch()))
                            .or(serviceView.greatestMilestoneName.contains(criteria.getSearch()))
                            .or(serviceView.openJeop.contains(criteria.getSearch()))
                            .or(serviceView.latestNote.contains(criteria.getSearch()))
                            .or(serviceView.openJeopResponsibilities.contains(criteria.getSearch()))

            );
        }

        if (criteria.getHideTerminalStatuses() || !Strings.isNullOrEmpty(criteria.getWorkflowView())) {
            List<String> terminalStatuses = Arrays.asList("Service Complete", "Service Cancelled", "Change In Assignment");
            expression = expression.and(serviceView.status.notIn(terminalStatuses));
        }
        if (criteria.isMacOnly()) {
            List<String> macOrderTypes = Arrays.asList("Move", "Add", "Change");
            expression = expression.and(serviceView.orderType.in(macOrderTypes));
        }

        if (criteria.getActiveOnly()) {
            expression = expression.and(serviceView.active.isTrue());
        }

        if (criteria.getLocationId() != null) {
            expression = expression.and(serviceView.locationId.eq(criteria.getLocationId()));
        }
        expression = getContainsExpression(expression, serviceView.address, criteria.getAddress());
        expression = getContainsExpression(expression, serviceView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, serviceView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, serviceView.city, criteria.getCity());
        expression = getContainsExpression(expression, serviceView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, serviceView.postalCode, criteria.getPostalCode());
        expression = getInExpression(expression, serviceView.status, criteria.getStatus());
        expression = getInExpression(expression, serviceView.provisioner, criteria.getProvisioner());
        expression = getInExpression(expression, serviceView.qaManager, criteria.getQaManager());
        expression = getInExpression(expression, serviceView.projectManager, criteria.getProjectManager());
        expression = getInExpression(expression, serviceView.vertekProjectManager, criteria.getVertekProjectManager());
        expression = getInExpression(expression, serviceView.provider, criteria.getProvider());
        expression = getDateComparisonExpression(expression, serviceView.customerRequestedInstall, criteria.getCustomerRequestedInstall(), criteria.getCustomerRequestedInstallRange());
        expression = getDateComparisonExpression(expression, serviceView.siteSurveyDue, criteria.getSiteSurveyDue(), criteria.getSiteSurveyDueRange());
        expression = getDateComparisonExpression(expression, serviceView.siteSurveySubmit, criteria.getSiteSurveySubmit(), criteria.getSiteSurveySubmitRange());
        expression = getDateComparisonExpression(expression, serviceView.providerOrderSubmitted, criteria.getProviderOrderSubmitted(), criteria.getProviderOrderSubmittedRange());
        expression = getDateComparisonExpression(expression, serviceView.networkProviderFoc, criteria.getNetworkProviderFoc(), criteria.getNetworkProviderFocRange());
        expression = getDateComparisonExpression(expression, serviceView.dataProvisioningComplete, criteria.getDataProvisioningComplete(), criteria.getDataProvisioningCompleteRange());
        expression = getDateComparisonExpression(expression, serviceView.qaCheckOpen, criteria.getQaCheckOpen(), criteria.getQaCheckOpenRange());
        expression = getDateComparisonExpression(expression, serviceView.firstVendorInvoice, criteria.getFirstVendorInvoice(), criteria.getFirstVendorInvoiceRange());
        expression = getDateComparisonExpression(expression, serviceView.returnedToOrderGroup, criteria.getReturnedToOrderGroup(), criteria.getReturnedToOrderGroupRange());
        expression = getDateComparisonExpression(expression, serviceView.returnedToSales, criteria.getReturnedToSales(), criteria.getReturnedToSalesRange());
        expression = getDateComparisonExpression(expression, serviceView.billingReviewComplete, criteria.getBillingReviewComplete(), criteria.getBillingReviewCompleteRange());
        expression = getDateComparisonExpression(expression, serviceView.followUpDate, criteria.getFollowUpDate(), criteria.getFollowUpDateRange(), true);
        expression = getContainsExpression(expression, serviceView.clientLocationInfo, criteria.getClientLocationInfo());
        expression = getContainsExpression(expression, serviceView.clientLocationType, criteria.getClientLocationType());
        expression = getDateComparisonExpression(expression, serviceView.created, criteria.getCreated(), criteria.getCreatedRange());
        expression = getContainsExpression(expression, serviceView.clientLocationId, criteria.getClientLocationId());
        expression = getContainsExpression(expression, serviceView.clientServiceId, criteria.getClientServiceId());
        expression = getInExpression(expression, serviceView.companyName, criteria.getCompanyName());
        expression = getInExpression(expression, serviceView.parentCompanyName, criteria.getParentCompanyName());
        expression = getInExpression(expression, serviceView.type, criteria.getServiceType());
        expression = getContainsExpression(expression, serviceView.speed, criteria.getSpeed());
        expression = getNumericComparisonExpression(expression, serviceView.mrc, criteria.getMrc(), criteria.getMrcRange());
        expression = getNumericComparisonExpression(expression, serviceView.nrc, criteria.getNrc(), criteria.getNrcRange());
        expression = getContainsExpression(expression, serviceView.lconPhone, criteria.getLconPhone());
        expression = getInExpression(expression, serviceView.levelOfEffort, criteria.getLevelOfEffort());
        expression = getContainsExpression(expression, serviceView.latestNote, criteria.getLatestNote());
        expression = getContainsExpression(expression, serviceView.projectName, criteria.getProjectName());
        expression = getContainsExpression(expression, serviceView.openJeopResponsibilities,
                criteria.getOpenJeopResponsibilities());
        expression = getContainsExpression(expression, serviceView.serviceBilledTo, criteria.getServiceBilledTo());

        if (!Strings.isNullOrEmpty(criteria.getWorkflowView())) {
            if ("Order Receipt".equals(criteria.getWorkflowView())) {
                expression = expression.and(serviceView.provisioner.eq("Unassigned")).and(serviceView.status.ne("On Hold"));
            } else if ("Pending Order Submission".equals(criteria.getWorkflowView())) {
                expression = expression.and(serviceView.status.in(Arrays.asList("Engineer Assigned", "Site Survey Complete")));
            } else if ("Site Surveys Pending".equals(criteria.getWorkflowView())) {
                expression = expression.and(serviceView.status.eq("Site Survey In Progress"));
            } else if ("Construction Pending".equals(criteria.getWorkflowView())) {
                expression = expression.and(serviceView.status.eq("Network Provider Construction"));
            } else if ("Pending FOC Assignment".equals(criteria.getWorkflowView())) {
                expression = expression.and(
                        serviceView.providerOrderSubmitted.isNotNull().and(serviceView.accessCircuitFoc.isNull())
                                .or(serviceView.providerOrderSubmitted.isNotNull().and(serviceView.networkProviderFoc.isNull()))
                                .or(serviceView.status.eq("Network Provider Construction Complete"))
                );
            } else if ("Upcoming FOC".equals(criteria.getWorkflowView())) {
                LocalDate now = (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                expression = expression.and(
                        serviceView.providerOrderSubmitted.isNotNull()
                                .and(serviceView.accessCircuitFoc.dayOfYear().goe(now.getDayOfYear())
                                        .and(serviceView.accessCircuitFoc.year().eq(now.getYear()))
                                        .or(serviceView.accessCircuitFoc.year().gt(now.getYear())))
                                .and(serviceView.networkProviderFoc.isNull())
                                .or(serviceView.providerOrderSubmitted.isNotNull()
                                        .and(serviceView.networkProviderFoc.dayOfYear().goe(now.getDayOfYear())
                                                .and(serviceView.networkProviderFoc.year().eq(now.getYear()))
                                                .or(serviceView.networkProviderFoc.year().gt(now.getYear())))
                                        .and(serviceView.dataProvisioningComplete.isNull()))
                );
            } else if ("Past Due FOC".equals(criteria.getWorkflowView())) {
                LocalDate now = (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                expression = expression.and(
                        serviceView.providerOrderSubmitted.isNotNull()
                                .and(serviceView.accessCircuitFoc.dayOfYear().lt(now.getDayOfYear())
                                        .and(serviceView.accessCircuitFoc.year().eq(now.getYear()))
                                        .or(serviceView.accessCircuitFoc.year().lt(now.getYear())))
                                .and(serviceView.networkProviderFoc.isNull())
                                .or(serviceView.providerOrderSubmitted.isNotNull()
                                        .and(serviceView.networkProviderFoc.dayOfYear().lt(now.getDayOfYear())
                                                .and(serviceView.networkProviderFoc.year().eq(now.getYear()))
                                                .or(serviceView.networkProviderFoc.year().lt(now.getYear())))
                                        .and(serviceView.dataProvisioningComplete.isNull()))
                );
            } else if ("Circuit Complete".equals(criteria.getWorkflowView())) {
                expression = expression.and(serviceView.status.eq("Circuit Complete"));
            } else if ("On Hold".equals(criteria.getWorkflowView())) {
                expression = expression.and(serviceView.status.eq("On Hold"));
            } else if ("Today's Follow Ups".equals(criteria.getWorkflowView())) {
                LocalDate now = (new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                expression = expression.and(serviceView.followUpDate.dayOfYear().loe(now.getDayOfYear()))
                        .and(serviceView.followUpDate.year().loe(now.getYear()));
            }
        }

        return expression;
    }

    private OrderSpecifier getOrderBy(final ServiceViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, serviceView.id);
        if (!Strings.isNullOrEmpty(criteria.getWorkflowView())) {
            if ("Order Receipt".equals(criteria.getWorkflowView())
                    || "Pending Order Submission".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.created);
            } else if ("Site Surveys Pending".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.siteSurveySubmit);
            } else if ("Construction Pending".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.networkProviderFoc);
            } else if ("Pending FOC Assignment".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.providerOrderSubmitted);
            } else if ("Upcoming FOC".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.networkProviderFoc);
            } else if ("Past Due FOC".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.providerOrderSubmitted);
            } else if ("Circuit Complete".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.dataProvisioningComplete);
            } else if ("On Hold".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.onHold);
            } else if ("Today's Follow Ups".equals(criteria.getWorkflowView())) {
                return new OrderSpecifier(Order.ASC, serviceView.followUpDate);            }
        }
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<ServiceView> pathBuilder = new PathBuilder<>(ServiceView.class, serviceView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }

    public PaginatedResult<ServiceView> getServiceViewsForLink(ServiceViewSearchCriteria criteria) {
        List<String> terminalStatuses = Arrays.asList("Service Cancelled", "Change In Assignment");
        return new PaginatedResult<>(new JPAQuery<ServiceView>(entityManager)
                .from(serviceView)
                .where(serviceView.locationId.eq(criteria.getLocationId())
                        .and(serviceView.linked.isFalse())
                        .and(serviceView.bundled.isFalse())
                        .and(serviceView.id.ne(criteria.getServiceId()))
                        .and(serviceView.status.notIn(terminalStatuses)))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    public PaginatedResult<ServiceView> getServiceViewsForBundle(ServiceViewSearchCriteria criteria) {

        ServiceView existing = retrieve(criteria.getServiceId());
        List<String> terminalStatuses = Arrays.asList("Service Cancelled", "Change In Assignment");
        BooleanExpression expression = serviceView.locationId.eq(criteria.getLocationId())
                .and(serviceView.linked.isFalse())
                .and(serviceView.bundled.isFalse())
                .and(serviceView.id.ne(criteria.getServiceId()))
                .and(serviceView.status.notIn(terminalStatuses));

        if (existing.getProvider() != null) {
            expression = expression.and(serviceView.provider.eq(existing.getProvider())
                    .or(serviceView.provider.isNull()));
        } else {
            expression = expression.and(serviceView.provider.isNull());
        }


        return new PaginatedResult<>(new JPAQuery<ServiceView>(entityManager)
                .from(serviceView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());


    }

    public List<String> findServiceTypes() {
        Tenant selectedTenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<String>(entityManager)
                .select(serviceView.type)
                .from(serviceView)
                .where(serviceView.tenantId.eq(selectedTenant.getId()))
                .distinct().fetch();
    }
}
