package com.vertek.corporate.qto.service.view;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceView is a Querydsl query type for ServiceView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceView extends EntityPathBase<ServiceView> {

    private static final long serialVersionUID = -785040481L;

    public static final QServiceView serviceView = new QServiceView("serviceView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final DateTimePath<java.util.Date> accessCircuitFoc = createDateTime("accessCircuitFoc", java.util.Date.class);

    public final BooleanPath active = createBoolean("active");

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final DateTimePath<java.util.Date> billingReviewComplete = createDateTime("billingReviewComplete", java.util.Date.class);

    public final BooleanPath bundled = createBoolean("bundled");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final DateTimePath<java.util.Date> customerRequestedInstall = createDateTime("customerRequestedInstall", java.util.Date.class);

    public final DateTimePath<java.util.Date> dataProvisioningComplete = createDateTime("dataProvisioningComplete", java.util.Date.class);

    public final DateTimePath<java.util.Date> firstVendorInvoice = createDateTime("firstVendorInvoice", java.util.Date.class);

    public final DateTimePath<java.util.Date> followUpDate = createDateTime("followUpDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> greatestMilestoneDate = createDateTime("greatestMilestoneDate", java.util.Date.class);

    public final StringPath greatestMilestoneName = createString("greatestMilestoneName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath latestNote = createString("latestNote");

    public final StringPath lconPhone = createString("lconPhone");

    public final StringPath levelOfEffort = createString("levelOfEffort");

    public final BooleanPath linked = createBoolean("linked");

    public final BooleanPath linkedBundledParent = createBoolean("linkedBundledParent");

    public final NumberPath<Long> linkedBundledParentId = createNumber("linkedBundledParentId", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrr = createNumber("mrr", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> networkProviderFoc = createDateTime("networkProviderFoc", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrr = createNumber("nrr", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> onHold = createDateTime("onHold", java.util.Date.class);

    public final StringPath openJeop = createString("openJeop");

    public final StringPath openJeopResponsibilities = createString("openJeopResponsibilities");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderType = createString("orderType");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Long> progressPercentage = createNumber("progressPercentage", Long.class);

    public final StringPath projectManager = createString("projectManager");

    public final StringPath projectName = createString("projectName");

    public final StringPath provider = createString("provider");

    public final DateTimePath<java.util.Date> providerOrderSubmitted = createDateTime("providerOrderSubmitted", java.util.Date.class);

    public final StringPath provisioner = createString("provisioner");

    public final DateTimePath<java.util.Date> qaCheckOpen = createDateTime("qaCheckOpen", java.util.Date.class);

    public final StringPath qaManager = createString("qaManager");

    public final StringPath recordSource = createString("recordSource");

    public final DateTimePath<java.util.Date> returnedToOrderGroup = createDateTime("returnedToOrderGroup", java.util.Date.class);

    public final DateTimePath<java.util.Date> returnedToSales = createDateTime("returnedToSales", java.util.Date.class);

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final BooleanPath showJeopIcon = createBoolean("showJeopIcon");

    public final BooleanPath showNoteIcon = createBoolean("showNoteIcon");

    public final BooleanPath showOpenDisconnectIcon = createBoolean("showOpenDisconnectIcon");

    public final BooleanPath showOpenMacIcon = createBoolean("showOpenMacIcon");

    public final DateTimePath<java.util.Date> siteSurveyDue = createDateTime("siteSurveyDue", java.util.Date.class);

    public final DateTimePath<java.util.Date> siteSurveySubmit = createDateTime("siteSurveySubmit", java.util.Date.class);

    public final StringPath speed = createString("speed");

    public final StringPath stateProvince = createString("stateProvince");

    public final StringPath status = createString("status");

    public final NumberPath<Long> statusAge = createNumber("statusAge", Long.class);

    public final StringPath subStatus = createString("subStatus");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath vertekProjectManager = createString("vertekProjectManager");

    public QServiceView(String variable) {
        super(ServiceView.class, forVariable(variable));
    }

    public QServiceView(Path<? extends ServiceView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceView(PathMetadata metadata) {
        super(ServiceView.class, metadata);
    }

}

