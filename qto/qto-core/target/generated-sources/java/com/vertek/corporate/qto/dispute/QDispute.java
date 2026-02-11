package com.vertek.corporate.qto.dispute;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDispute is a Querydsl query type for Dispute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDispute extends EntityPathBase<Dispute> {

    private static final long serialVersionUID = 83244389L;

    public static final QDispute dispute = new QDispute("dispute");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<java.math.BigDecimal> amountDisputedMrc = createNumber("amountDisputedMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> amountDisputedNrc = createNumber("amountDisputedNrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> annualizedMrcSave = createNumber("annualizedMrcSave", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> billingReviewComplete = createDateTime("billingReviewComplete", java.util.Date.class);

    public final DateTimePath<java.util.Date> creditRecognized = createDateTime("creditRecognized", java.util.Date.class);

    public final StringPath disputeAssignment = createString("disputeAssignment");

    public final DateTimePath<java.util.Date> disputeClosedDate = createDateTime("disputeClosedDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> disputeFollowUpDate = createDateTime("disputeFollowUpDate", java.util.Date.class);

    public final StringPath disputeStatus = createString("disputeStatus");

    public final StringPath disputeType = createString("disputeType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath invoiceNum = createString("invoiceNum");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> openDate = createDateTime("openDate", java.util.Date.class);

    public final NumberPath<java.math.BigDecimal> realizedCredit = createNumber("realizedCredit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> realizedMrcAdjustment = createNumber("realizedMrcAdjustment", java.math.BigDecimal.class);

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath vendorTrackingNum = createString("vendorTrackingNum");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QDispute(String variable) {
        super(Dispute.class, forVariable(variable));
    }

    public QDispute(Path<? extends Dispute> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDispute(PathMetadata metadata) {
        super(Dispute.class, metadata);
    }

}

