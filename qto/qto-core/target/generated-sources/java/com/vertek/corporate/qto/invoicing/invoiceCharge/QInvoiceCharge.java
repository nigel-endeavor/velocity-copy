package com.vertek.corporate.qto.invoicing.invoiceCharge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInvoiceCharge is a Querydsl query type for InvoiceCharge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvoiceCharge extends EntityPathBase<InvoiceCharge> {

    private static final long serialVersionUID = 869481371L;

    public static final QInvoiceCharge invoiceCharge = new QInvoiceCharge("invoiceCharge");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final DateTimePath<java.util.Date> billableEventDate = createDateTime("billableEventDate", java.util.Date.class);

    public final StringPath billableEventMilestoneDescription = createString("billableEventMilestoneDescription");

    public final StringPath chargeCredit = createString("chargeCredit");

    public final StringPath chargeDesc = createString("chargeDesc");

    public final StringPath chargeLevel = createString("chargeLevel");

    public final StringPath chargeType = createString("chargeType");

    public final StringPath endCustomerName = createString("endCustomerName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> invoicedAmount = createNumber("invoicedAmount", java.math.BigDecimal.class);

    public final NumberPath<Long> invoiceId = createNumber("invoiceId", Long.class);

    public final StringPath itemDesc = createString("itemDesc");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath masterCustomerName = createString("masterCustomerName");

    public final NumberPath<Long> milestoneInstanceId = createNumber("milestoneInstanceId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> previouslyBilled = createNumber("previouslyBilled", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final NumberPath<java.math.BigDecimal> unitCost = createNumber("unitCost", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QInvoiceCharge(String variable) {
        super(InvoiceCharge.class, forVariable(variable));
    }

    public QInvoiceCharge(Path<? extends InvoiceCharge> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvoiceCharge(PathMetadata metadata) {
        super(InvoiceCharge.class, metadata);
    }

}

