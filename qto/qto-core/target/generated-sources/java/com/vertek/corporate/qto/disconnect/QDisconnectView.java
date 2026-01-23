package com.vertek.corporate.qto.disconnect;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDisconnectView is a Querydsl query type for DisconnectView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisconnectView extends EntityPathBase<DisconnectView> {

    private static final long serialVersionUID = -1490909378L;

    public static final QDisconnectView disconnectView = new QDisconnectView("disconnectView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final DateTimePath<java.util.Date> billingReviewComplete = createDateTime("billingReviewComplete", java.util.Date.class);

    public final BooleanPath bundled = createBoolean("bundled");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> complete = createDateTime("complete", java.util.Date.class);

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final DateTimePath<java.util.Date> customerRequestedDisconnect = createDateTime("customerRequestedDisconnect", java.util.Date.class);

    public final StringPath disconnectReason = createString("disconnectReason");

    public final NumberPath<java.math.BigDecimal> earlyTerminationFee = createNumber("earlyTerminationFee", java.math.BigDecimal.class);

    public final StringPath endCustomerClientId = createString("endCustomerClientId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath latestNote = createString("latestNote");

    public final BooleanPath linked = createBoolean("linked");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrr = createNumber("mrr", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> networkProviderFoc = createDateTime("networkProviderFoc", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderType = createString("orderType");

    public final StringPath parentCompanyClientId = createString("parentCompanyClientId");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Long> progressPercentage = createNumber("progressPercentage", Long.class);

    public final StringPath projectName = createString("projectName");

    public final StringPath provider = createString("provider");

    public final StringPath providerOrderNumber = createString("providerOrderNumber");

    public final DateTimePath<java.util.Date> providerOrderSubmitted = createDateTime("providerOrderSubmitted", java.util.Date.class);

    public final StringPath provisioner = createString("provisioner");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final BooleanPath showJeopIcon = createBoolean("showJeopIcon");

    public final BooleanPath showNoteIcon = createBoolean("showNoteIcon");

    public final BooleanPath showOpenDisconnectIcon = createBoolean("showOpenDisconnectIcon");

    public final BooleanPath showOpenMacIcon = createBoolean("showOpenMacIcon");

    public final StringPath stateProvince = createString("stateProvince");

    public final StringPath status = createString("status");

    public final NumberPath<Long> statusAge = createNumber("statusAge", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QDisconnectView(String variable) {
        super(DisconnectView.class, forVariable(variable));
    }

    public QDisconnectView(Path<? extends DisconnectView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDisconnectView(PathMetadata metadata) {
        super(DisconnectView.class, metadata);
    }

}

