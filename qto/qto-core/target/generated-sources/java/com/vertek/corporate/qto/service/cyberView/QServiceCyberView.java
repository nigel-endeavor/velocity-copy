package com.vertek.corporate.qto.service.cyberView;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceCyberView is a Querydsl query type for ServiceCyberView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceCyberView extends EntityPathBase<ServiceCyberView> {

    private static final long serialVersionUID = 1646367207L;

    public static final QServiceCyberView serviceCyberView = new QServiceCyberView("serviceCyberView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final DateTimePath<java.util.Date> breakGlassAccountConfigured = createDateTime("breakGlassAccountConfigured", java.util.Date.class);

    public final DateTimePath<java.util.Date> bulkAlarmTuningPhase = createDateTime("bulkAlarmTuningPhase", java.util.Date.class);

    public final BooleanPath bundled = createBoolean("bundled");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> conditionalAccessPolicyVerification = createDateTime("conditionalAccessPolicyVerification", java.util.Date.class);

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final DateTimePath<java.util.Date> customAlarmRuleAdditions = createDateTime("customAlarmRuleAdditions", java.util.Date.class);

    public final DateTimePath<java.util.Date> customerRequestedInstall = createDateTime("customerRequestedInstall", java.util.Date.class);

    public final DateTimePath<java.util.Date> d3ConnectionVerified = createDateTime("d3ConnectionVerified", java.util.Date.class);

    public final DateTimePath<java.util.Date> defaultAlarmRuleAdditions = createDateTime("defaultAlarmRuleAdditions", java.util.Date.class);

    public final DateTimePath<java.util.Date> deployConsultingTenant = createDateTime("deployConsultingTenant", java.util.Date.class);

    public final DateTimePath<java.util.Date> deployVulnerabilityScans = createDateTime("deployVulnerabilityScans", java.util.Date.class);

    public final DateTimePath<java.util.Date> deviceComplianceEnabled = createDateTime("deviceComplianceEnabled", java.util.Date.class);

    public final DateTimePath<java.util.Date> devopsNotifiedOfHalcyonAddition = createDateTime("devopsNotifiedOfHalcyonAddition", java.util.Date.class);

    public final DateTimePath<java.util.Date> discussFutureCyrismaManagement = createDateTime("discussFutureCyrismaManagement", java.util.Date.class);

    public final DateTimePath<java.util.Date> emailUsmAnywhereTemplateRequirements = createDateTime("emailUsmAnywhereTemplateRequirements", java.util.Date.class);

    public final StringPath endCustomerClientId = createString("endCustomerClientId");

    public final DateTimePath<java.util.Date> endLearningMode = createDateTime("endLearningMode", java.util.Date.class);

    public final NumberPath<Long> equipmentCount = createNumber("equipmentCount", Long.class);

    public final StringPath equipmentTypes = createString("equipmentTypes");

    public final DateTimePath<java.util.Date> filtersBuiltForReports = createDateTime("filtersBuiltForReports", java.util.Date.class);

    public final DateTimePath<java.util.Date> followUpDate = createDateTime("followUpDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> forwardAlarmsToD3SocLive = createDateTime("forwardAlarmsToD3SocLive", java.util.Date.class);

    public final DateTimePath<java.util.Date> forwardAlarmsToUsmCentral = createDateTime("forwardAlarmsToUsmCentral", java.util.Date.class);

    public final DateTimePath<java.util.Date> geographicRestrictionsEnabled = createDateTime("geographicRestrictionsEnabled", java.util.Date.class);

    public final DateTimePath<java.util.Date> greatestMilestoneDate = createDateTime("greatestMilestoneDate", java.util.Date.class);

    public final StringPath greatestMilestoneName = createString("greatestMilestoneName");

    public final DateTimePath<java.util.Date> halcyonApiTokenAddedToD3 = createDateTime("halcyonApiTokenAddedToD3", java.util.Date.class);

    public final DateTimePath<java.util.Date> halcyonDeployedToHosts = createDateTime("halcyonDeployedToHosts", java.util.Date.class);

    public final DateTimePath<java.util.Date> halcyonPackageGivenToClient = createDateTime("halcyonPackageGivenToClient", java.util.Date.class);

    public final DateTimePath<java.util.Date> hostListProvidedByClient = createDateTime("hostListProvidedByClient", java.util.Date.class);

    public final StringPath i90ProjectManager = createString("i90ProjectManager");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> implementationQa = createDateTime("implementationQa", java.util.Date.class);

    public final DateTimePath<java.util.Date> implementationVerified = createDateTime("implementationVerified", java.util.Date.class);

    public final DateTimePath<java.util.Date> inventoryAssignmentVerified = createDateTime("inventoryAssignmentVerified", java.util.Date.class);

    public final StringPath latestNote = createString("latestNote");

    public final BooleanPath linked = createBoolean("linked");

    public final BooleanPath linkedBundledParent = createBoolean("linkedBundledParent");

    public final NumberPath<Long> linkedBundledParentId = createNumber("linkedBundledParentId", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrr = createNumber("mrr", java.math.BigDecimal.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> newUsmAnywhereServerBuild = createDateTime("newUsmAnywhereServerBuild", java.util.Date.class);

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrr = createNumber("nrr", java.math.BigDecimal.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderType = createString("orderType");

    public final StringPath parentCompanyClientId = createString("parentCompanyClientId");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final DateTimePath<java.util.Date> passwordResetEnabledForSelfService = createDateTime("passwordResetEnabledForSelfService", java.util.Date.class);

    public final DateTimePath<java.util.Date> pimEnablement = createDateTime("pimEnablement", java.util.Date.class);

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Long> progressPercentage = createNumber("progressPercentage", Long.class);

    public final DateTimePath<java.util.Date> providedCustomerWithReport = createDateTime("providedCustomerWithReport", java.util.Date.class);

    public final StringPath provider = createString("provider");

    public final StringPath provisioner = createString("provisioner");

    public final DateTimePath<java.util.Date> reviewExistingCaAndMfaPolicies = createDateTime("reviewExistingCaAndMfaPolicies", java.util.Date.class);

    public final DateTimePath<java.util.Date> scheduleVulnerabilityScans = createDateTime("scheduleVulnerabilityScans", java.util.Date.class);

    public final BooleanPath showJeopIcon = createBoolean("showJeopIcon");

    public final BooleanPath showNoteIcon = createBoolean("showNoteIcon");

    public final BooleanPath showOpenDisconnectIcon = createBoolean("showOpenDisconnectIcon");

    public final BooleanPath showOpenMacIcon = createBoolean("showOpenMacIcon");

    public final DateTimePath<java.util.Date> siemEventFiltering = createDateTime("siemEventFiltering", java.util.Date.class);

    public final DateTimePath<java.util.Date> signInPoliciesEnabled = createDateTime("signInPoliciesEnabled", java.util.Date.class);

    public final StringPath stateProvince = createString("stateProvince");

    public final StringPath status = createString("status");

    public final NumberPath<Long> statusAge = createNumber("statusAge", Long.class);

    public final DateTimePath<java.util.Date> techDataGatheringFormSent = createDateTime("techDataGatheringFormSent", java.util.Date.class);

    public final DateTimePath<java.util.Date> techDataGatheringMeetingCompleted = createDateTime("techDataGatheringMeetingCompleted", java.util.Date.class);

    public final DateTimePath<java.util.Date> techDataGatheringMeetingScheduled = createDateTime("techDataGatheringMeetingScheduled", java.util.Date.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final DateTimePath<java.util.Date> testEmailSentToClient = createDateTime("testEmailSentToClient", java.util.Date.class);

    public final StringPath type = createString("type");

    public final DateTimePath<java.util.Date> verifyAssetsInSiemDb = createDateTime("verifyAssetsInSiemDb", java.util.Date.class);

    public final DateTimePath<java.util.Date> verifyLoggingDataSource = createDateTime("verifyLoggingDataSource", java.util.Date.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceCyberView(String variable) {
        super(ServiceCyberView.class, forVariable(variable));
    }

    public QServiceCyberView(Path<? extends ServiceCyberView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceCyberView(PathMetadata metadata) {
        super(ServiceCyberView.class, metadata);
    }

}

