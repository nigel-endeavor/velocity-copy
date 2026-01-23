package com.vertek.corporate.qto.service.inventoryview;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceInventoryView is a Querydsl query type for ServiceInventoryView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceInventoryView extends EntityPathBase<ServiceInventoryView> {

    private static final long serialVersionUID = -449835417L;

    public static final QServiceInventoryView serviceInventoryView = new QServiceInventoryView("serviceInventoryView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final BooleanPath active = createBoolean("active");

    public final StringPath activeInactive = createString("activeInactive");

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final NumberPath<java.math.BigDecimal> annualRecurringCost = createNumber("annualRecurringCost", java.math.BigDecimal.class);

    public final BooleanPath billable = createBoolean("billable");

    public final BooleanPath bundled = createBoolean("bundled");

    public final StringPath childIds = createString("childIds");

    public final StringPath childOrderTypes = createString("childOrderTypes");

    public final StringPath childSubOrderTypes = createString("childSubOrderTypes");

    public final DateTimePath<java.util.Date> circuitTermEndDate = createDateTime("circuitTermEndDate", java.util.Date.class);

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> contractSignedDate = createDateTime("contractSignedDate", java.util.Date.class);

    public final StringPath contractTerm = createString("contractTerm");

    public final NumberPath<Long> countOpenDisputes = createNumber("countOpenDisputes", Long.class);

    public final StringPath disputeTypes = createString("disputeTypes");

    public final StringPath endCustomerClientId = createString("endCustomerClientId");

    public final BooleanPath hasIcb = createBoolean("hasIcb");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> inventoryAddedDate = createDateTime("inventoryAddedDate", java.util.Date.class);

    public final NumberPath<Long> isMacd = createNumber("isMacd", Long.class);

    public final BooleanPath linked = createBoolean("linked");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrr = createNumber("mrr", java.math.BigDecimal.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrr = createNumber("nrr", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> openDisputeMrc = createNumber("openDisputeMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> openDisputeNrc = createNumber("openDisputeNrc", java.math.BigDecimal.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderType = createString("orderType");

    public final StringPath parentCompanyClientId = createString("parentCompanyClientId");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath projectManager = createString("projectManager");

    public final StringPath provider = createString("provider");

    public final StringPath providerCircuitId = createString("providerCircuitId");

    public final StringPath provisioner = createString("provisioner");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final BooleanPath showOpenDisconnectIcon = createBoolean("showOpenDisconnectIcon");

    public final BooleanPath showOpenDisputeIcon = createBoolean("showOpenDisputeIcon");

    public final BooleanPath showOpenMacIcon = createBoolean("showOpenMacIcon");

    public final StringPath speed = createString("speed");

    public final StringPath stateProvince = createString("stateProvince");

    public final StringPath status = createString("status");

    public final StringPath subOrderType = createString("subOrderType");

    public final StringPath summaryBill = createString("summaryBill");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceInventoryView(String variable) {
        super(ServiceInventoryView.class, forVariable(variable));
    }

    public QServiceInventoryView(Path<? extends ServiceInventoryView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceInventoryView(PathMetadata metadata) {
        super(ServiceInventoryView.class, metadata);
    }

}

