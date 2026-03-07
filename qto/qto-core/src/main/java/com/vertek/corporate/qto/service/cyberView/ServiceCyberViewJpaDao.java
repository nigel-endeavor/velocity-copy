package com.vertek.corporate.qto.service.cyberView;

import com.google.common.base.Strings;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static com.vertek.corporate.qto.service.cyberView.QServiceCyberView.serviceCyberView;

@Stateless
public class ServiceCyberViewJpaDao extends AbstractMasterCustomerJpaDao<ServiceCyberView> {

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
     * Retrieves all service views matching the given criteria.
     *
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<ServiceCyberView> findBySearchCriteria(final ServiceCyberViewSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<ServiceCyberView>(entityManager)
                .from(serviceCyberView)
                .where(getExpression(criteria))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    /**
     * Gets the where clause for a given serviceCyberViewSearchCriteria.
     *
     * @param criteria the criteria to filter by.
     * @return relevent where clause.
     */
    private Predicate getExpression(final ServiceCyberViewSearchCriteria criteria) {
        BooleanExpression expression = serviceCyberView.id.isNotNull();
        expression = addMasterCustomerAndTenantFilter(expression, serviceCyberView.masterCustomerId,
                serviceCyberView.tenantId, true);


        if (criteria.getHideTerminalStatuses()) {
            List<String> terminalStatuses = Arrays.asList("Service Complete", "Service Cancelled", "Change In Assignment", "Disconnect Complete");
            expression = expression.and(serviceCyberView.status.notIn(terminalStatuses));
        }
        if (criteria.isMacOnly()) {
            List<String> macOrderTypes = Arrays.asList("Move", "Add", "Change");
            expression = expression.and(serviceCyberView.orderType.in(macOrderTypes));
        }
        if (!Strings.isNullOrEmpty(criteria.getSearch())) {
            expression = expression.and(
                    serviceCyberView.locationId.stringValue().contains(criteria.getSearch())
                            .or(serviceCyberView.address.contains(criteria.getSearch()))
                            .or(serviceCyberView.status.contains(criteria.getSearch()))
                            .or(serviceCyberView.provisioner.contains(criteria.getSearch()))
                            .or(serviceCyberView.i90ProjectManager.contains(criteria.getSearch()))
                            .or(serviceCyberView.provider.contains(criteria.getSearch()))
                            .or(serviceCyberView.clientLocationId.contains(criteria.getSearch()))
                            .or(serviceCyberView.companyName.contains(criteria.getSearch()))
                            .or(serviceCyberView.parentCompanyName.contains(criteria.getSearch()))
                            .or(serviceCyberView.type.contains(criteria.getSearch()))
                            .or(serviceCyberView.mrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.nrc.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.id.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.parentCompanyClientId.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.endCustomerClientId.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.greatestMilestoneName.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.greatestMilestoneDate.stringValue().contains(criteria.getSearch()))
                            .or(serviceCyberView.latestNote.contains(criteria.getSearch()))
                            .or(serviceCyberView.equipmentTypes.contains(criteria.getSearch()))
                            .or(serviceCyberView.orderType.contains(criteria.getSearch()))

            );
        }

        if (criteria.getLocationId() != null) {
            expression = expression.and(serviceCyberView.locationId.eq(criteria.getLocationId()));
        }
        if (criteria.getCompanyId() != null) {
            expression = expression.and(serviceCyberView.companyId.eq(criteria.getCompanyId()));
        }
        expression = getContainsExpression(expression, serviceCyberView.parentCompanyClientId, criteria.getParentCompanyClientId());
        expression = getContainsExpression(expression, serviceCyberView.endCustomerClientId, criteria.getEndCustomerClientId());
        expression = getContainsExpression(expression, serviceCyberView.address, criteria.getAddress());
        expression = getContainsExpression(expression, serviceCyberView.address1, criteria.getAddress1());
        expression = getContainsExpression(expression, serviceCyberView.address2, criteria.getAddress2());
        expression = getContainsExpression(expression, serviceCyberView.city, criteria.getCity());
        expression = getContainsExpression(expression, serviceCyberView.stateProvince, criteria.getStateProvince());
        expression = getContainsExpression(expression, serviceCyberView.postalCode, criteria.getPostalCode());
        expression = getInExpression(expression, serviceCyberView.status, criteria.getStatus());
        expression = getInExpression(expression, serviceCyberView.provisioner, criteria.getProvisioner());
        expression = getInExpression(expression, serviceCyberView.provider, criteria.getProvider());
        expression = getContainsExpression(expression, serviceCyberView.clientLocationId, criteria.getClientLocationId());
        expression = getInExpression(expression, serviceCyberView.companyName, criteria.getCompanyName());
        expression = getInExpression(expression, serviceCyberView.parentCompanyName, criteria.getParentCompanyName());
        expression = getInExpression(expression, serviceCyberView.type, criteria.getType());
        expression = getDateComparisonExpression(expression, serviceCyberView.created, criteria.getCreated(), criteria.getCreatedRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.customerRequestedInstall, criteria.getCustomerRequestedInstall(), criteria.getCustomerRequestedInstallRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.techDataGatheringFormSent, criteria.getTechDataGatheringFormSent(), criteria.getTechDataGatheringFormSentRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.techDataGatheringMeetingScheduled, criteria.getTechDataGatheringMeetingScheduled(), criteria.getTechDataGatheringMeetingScheduledRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.techDataGatheringMeetingCompleted, criteria.getTechDataGatheringMeetingCompleted(), criteria.getTechDataGatheringMeetingCompletedRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.emailUsmAnywhereTemplateRequirements, criteria.getEmailUsmAnywhereTemplateRequirements(), criteria.getEmailUsmAnywhereTemplateRequirementsRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.inventoryAssignmentVerified, criteria.getInventoryAssignmentVerified(), criteria.getInventoryAssignmentVerifiedRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.newUsmAnywhereServerBuild, criteria.getNewUsmAnywhereServerBuild(), criteria.getNewUsmAnywhereServerBuildRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.implementationQa, criteria.getImplementationQa(), criteria.getImplementationQaRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.verifyAssetsInSiemDb, criteria.getVerifyAssetsInSiemDb(), criteria.getVerifyAssetsInSiemDbRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.verifyLoggingDataSource, criteria.getVerifyLoggingDataSource(), criteria.getVerifyLoggingDataSourceRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.scheduleVulnerabilityScans, criteria.getScheduleVulnerabilityScans(), criteria.getScheduleVulnerabilityScansRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.bulkAlarmTuningPhase, criteria.getBulkAlarmTuningPhase(), criteria.getBulkAlarmTuningPhaseRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.siemEventFiltering, criteria.getSiemEventFiltering(), criteria.getSiemEventFilteringRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.filtersBuiltForReports, criteria.getFiltersBuiltForReports(), criteria.getFiltersBuiltForReportsRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.defaultAlarmRuleAdditions, criteria.getDefaultAlarmRuleAdditions(), criteria.getDefaultAlarmRuleAdditionsRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.customAlarmRuleAdditions, criteria.getCustomAlarmRuleAdditions(), criteria.getCustomAlarmRuleAdditionsRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.forwardAlarmsToUsmCentral, criteria.getForwardAlarmsToUsmCentral(), criteria.getForwardAlarmsToUsmCentralRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.forwardAlarmsToD3SocLive, criteria.getForwardAlarmsToD3SocLive(), criteria.getForwardAlarmsToD3SocLiveRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.followUpDate, criteria.getFollowUpDate(), criteria.getFollowUpDateRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.hostListProvidedByClient, criteria.getHostListProvidedByClient(), criteria.getHostListProvidedByClientRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.halcyonPackageGivenToClient, criteria.getHalcyonPackageGivenToClient(), criteria.getHalcyonPackageGivenToClientRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.halcyonDeployedToHosts, criteria.getHalcyonDeployedToHosts(), criteria.getHalcyonDeployedToHostsRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.devopsNotifiedOfHalcyonAddition, criteria.getDevopsNotifiedOfHalcyonAddition(), criteria.getDevopsNotifiedOfHalcyonAdditionRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.halcyonApiTokenAddedToD3, criteria.getHalcyonApiTokenAddedToD3(), criteria.getHalcyonApiTokenAddedToD3Range());
        expression = getDateComparisonExpression(expression, serviceCyberView.d3ConnectionVerified, criteria.getD3ConnectionVerified(), criteria.getD3ConnectionVerifiedRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.endLearningMode, criteria.getEndLearningMode(), criteria.getEndLearningModeRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.testEmailSentToClient, criteria.getTestEmailSentToClient(), criteria.getTestEmailSentToClientRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.deployConsultingTenant, criteria.getDeployConsultingTenant(), criteria.getDeployConsultingTenantRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.deployVulnerabilityScans, criteria.getDeployVulnerabilityScans(), criteria.getDeployVulnerabilityScansRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.providedCustomerWithReport, criteria.getProvidedCustomerWithReport(), criteria.getProvidedCustomerWithReportRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.discussFutureCyrismaManagement, criteria.getDiscussFutureCyrismaManagement(), criteria.getDiscussFutureCyrismaManagementRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.reviewExistingCaAndMfaPolicies, criteria.getReviewExistingCaAndMfaPolicies(), criteria.getReviewExistingCaAndMfaPoliciesRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.signInPoliciesEnabled, criteria.getSignInPoliciesEnabled(), criteria.getSignInPoliciesEnabledRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.conditionalAccessPolicyVerification, criteria.getConditionalAccessPolicyVerification(), criteria.getConditionalAccessPolicyVerificationRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.geographicRestrictionsEnabled, criteria.getGeographicRestrictionsEnabled(), criteria.getGeographicRestrictionsEnabledRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.deviceComplianceEnabled, criteria.getDeviceComplianceEnabled(), criteria.getDeviceComplianceEnabledRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.passwordResetEnabledForSelfService, criteria.getPasswordResetEnabledForSelfService(), criteria.getPasswordResetEnabledForSelfServiceRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.breakGlassAccountConfigured, criteria.getBreakGlassAccountConfigured(), criteria.getBreakGlassAccountConfiguredRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.pimEnablement, criteria.getPimEnablement(), criteria.getPimEnablementRange());
        expression = getDateComparisonExpression(expression, serviceCyberView.implementationVerified, criteria.getImplementationVerified(), criteria.getImplementationVerifiedRange());
        expression = getNumericComparisonExpression(expression, serviceCyberView.mrc, criteria.getMrc(), criteria.getMrcRange());
        expression = getNumericComparisonExpression(expression, serviceCyberView.nrc, criteria.getNrc(), criteria.getNrcRange());
     

        return expression;

    }
    
    private OrderSpecifier getOrderBy(final ServiceCyberViewSearchCriteria criteria) {
        OrderSpecifier orderBy = new OrderSpecifier(Order.DESC, serviceCyberView.id);
        if (!Strings.isNullOrEmpty(criteria.getSortField())) {
            PathBuilder<ServiceCyberView> pathBuilder = new PathBuilder<>(ServiceCyberView.class, serviceCyberView.getMetadata());
            orderBy = new OrderSpecifier(criteria.getSortDirection(), pathBuilder.get(criteria.getSortField()));
        }
        return orderBy;
    }
    
  public PaginatedResult<ServiceCyberView> getserviceCyberViewsForLink(ServiceCyberViewSearchCriteria criteria) {
        List<String> terminalStatuses = Arrays.asList("Service Cancelled", "Change In Assignment");
        return new PaginatedResult<>(new JPAQuery<ServiceCyberView>(entityManager)
                .from(serviceCyberView)
                .where(serviceCyberView.locationId.eq(criteria.getLocationId())
                        .and(serviceCyberView.linked.isFalse())
                        .and(serviceCyberView.bundled.isFalse())
                        .and(serviceCyberView.id.ne(criteria.getServiceId()))
                        .and(serviceCyberView.status.notIn(terminalStatuses)))
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());
    }

    public PaginatedResult<ServiceCyberView> getserviceCyberViewsForBundle(ServiceCyberViewSearchCriteria criteria) {

        ServiceCyberView existing = retrieve(criteria.getServiceId());
        List<String> terminalStatuses = Arrays.asList("Service Cancelled", "Change In Assignment");
        BooleanExpression expression = serviceCyberView.locationId.eq(criteria.getLocationId())
                .and(serviceCyberView.linked.isFalse())
                .and(serviceCyberView.bundled.isFalse())
                .and(serviceCyberView.id.ne(criteria.getServiceId()))
                .and(serviceCyberView.status.notIn(terminalStatuses));

        if (existing.getProvider() != null) {
            expression = expression.and(serviceCyberView.provider.eq(existing.getProvider())
                    .or(serviceCyberView.provider.isNull()));
        } else {
            expression = expression.and(serviceCyberView.provider.isNull());
        }


        return new PaginatedResult<>(new JPAQuery<ServiceCyberView>(entityManager)
                .from(serviceCyberView)
                .where(expression)
                .orderBy(getOrderBy(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults());


    }


    public ServiceCyberView findByServiceId(final Long id) {
        return new JPAQuery<ServiceCyberView>(entityManager)
                .from(serviceCyberView)
                .where(serviceCyberView.id.eq(id)).fetchOne();
    }
}
