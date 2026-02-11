package com.vertek.corporate.qto.dispute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDisputeView is a Querydsl query type for DisputeView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisputeView extends EntityPathBase<DisputeView> {

    private static final long serialVersionUID = -1970558550L;

    public static final QDisputeView disputeView = new QDisputeView("disputeView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final NumberPath<java.math.BigDecimal> amountDisputedMrc = createNumber("amountDisputedMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> amountDisputedNrc = createNumber("amountDisputedNrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> annualizedMrcSave = createNumber("annualizedMrcSave", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> billingReviewCompleteDate = createDateTime("billingReviewCompleteDate", java.util.Date.class);

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final StringPath creditRecognized = createString("creditRecognized");

    public final StringPath disputeAssignment = createString("disputeAssignment");

    public final DateTimePath<java.util.Date> disputeClosedDate = createDateTime("disputeClosedDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> disputeFollowUpDate = createDateTime("disputeFollowUpDate", java.util.Date.class);

    public final StringPath disputeStatus = createString("disputeStatus");

    public final StringPath disputeType = createString("disputeType");

    public final StringPath endCustomerClientId = createString("endCustomerClientId");

    public final BooleanPath hasIcb = createBoolean("hasIcb");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath invoiceNum = createString("invoiceNum");

    public final StringPath latestNote = createString("latestNote");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> openDate = createDateTime("openDate", java.util.Date.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath parentCompanyClientId = createString("parentCompanyClientId");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath provider = createString("provider");

    public final StringPath providerCircuitId = createString("providerCircuitId");

    public final NumberPath<java.math.BigDecimal> realizedCredit = createNumber("realizedCredit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> realizedMrcAdjustment = createNumber("realizedMrcAdjustment", java.math.BigDecimal.class);

    public final BooleanPath serviceActive = createBoolean("serviceActive");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final NumberPath<java.math.BigDecimal> serviceMrc = createNumber("serviceMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> serviceNrc = createNumber("serviceNrc", java.math.BigDecimal.class);

    public final StringPath serviceType = createString("serviceType");

    public final BooleanPath showDisputeFollowUpIcon = createBoolean("showDisputeFollowUpIcon");

    public final StringPath speed = createString("speed");

    public final StringPath stateProvince = createString("stateProvince");

    public final StringPath summaryBill = createString("summaryBill");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath vendorTrackingNum = createString("vendorTrackingNum");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QDisputeView(String variable) {
        super(DisputeView.class, forVariable(variable));
    }

    public QDisputeView(Path<? extends DisputeView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDisputeView(PathMetadata metadata) {
        super(DisputeView.class, metadata);
    }

}

