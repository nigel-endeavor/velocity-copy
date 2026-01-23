package com.vertek.corporate.qto.location.inventoryview;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationInventoryView is a Querydsl query type for LocationInventoryView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationInventoryView extends EntityPathBase<LocationInventoryView> {

    private static final long serialVersionUID = 951914075L;

    public static final QLocationInventoryView locationInventoryView = new QLocationInventoryView("locationInventoryView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<java.math.BigDecimal> activeCompleteMrc = createNumber("activeCompleteMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> activeCompleteMrr = createNumber("activeCompleteMrr", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> activeCompleteNrc = createNumber("activeCompleteNrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> activeCompleteNrr = createNumber("activeCompleteNrr", java.math.BigDecimal.class);

    public final StringPath activeInactive = createString("activeInactive");

    public final NumberPath<Long> activeServiceCount = createNumber("activeServiceCount", Long.class);

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final NumberPath<java.math.BigDecimal> annualRecurringCost = createNumber("annualRecurringCost", java.math.BigDecimal.class);

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientProjectManager = createString("clientProjectManager");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final NumberPath<Long> countOpenDisputes = createNumber("countOpenDisputes", Long.class);

    public final NumberPath<Long> countServices = createNumber("countServices", Long.class);

    public final NumberPath<Long> countServicesCancelled = createNumber("countServicesCancelled", Long.class);

    public final NumberPath<Long> countServicesChangeInAssignment = createNumber("countServicesChangeInAssignment", Long.class);

    public final NumberPath<Long> countServicesComplete = createNumber("countServicesComplete", Long.class);

    public final StringPath endCustomerClientId = createString("endCustomerClientId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inactiveServiceCount = createNumber("inactiveServiceCount", Long.class);

    public final DateTimePath<java.util.Date> inventoryAddedDate = createDateTime("inventoryAddedDate", java.util.Date.class);

    public final StringPath locationName = createString("locationName");

    public final StringPath locationStatus = createString("locationStatus");

    public final NumberPath<Long> macdCount = createNumber("macdCount", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> openDisputeMrc = createNumber("openDisputeMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> openDisputeNrc = createNumber("openDisputeNrc", java.math.BigDecimal.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath parentCompanyClientId = createString("parentCompanyClientId");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Long> progressPercentage = createNumber("progressPercentage", Long.class);

    public final StringPath provisioner = createString("provisioner");

    public final StringPath services = createString("services");

    public final BooleanPath showBundledIcon = createBoolean("showBundledIcon");

    public final BooleanPath showLinkedIcon = createBoolean("showLinkedIcon");

    public final BooleanPath showOpenDisconnectIcon = createBoolean("showOpenDisconnectIcon");

    public final BooleanPath showOpenDisputeIcon = createBoolean("showOpenDisputeIcon");

    public final BooleanPath showOpenMacIcon = createBoolean("showOpenMacIcon");

    public final StringPath stateProvince = createString("stateProvince");

    public final StringPath subOrderTypes = createString("subOrderTypes");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath vertekProjectManager = createString("vertekProjectManager");

    public QLocationInventoryView(String variable) {
        super(LocationInventoryView.class, forVariable(variable));
    }

    public QLocationInventoryView(Path<? extends LocationInventoryView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationInventoryView(PathMetadata metadata) {
        super(LocationInventoryView.class, metadata);
    }

}

