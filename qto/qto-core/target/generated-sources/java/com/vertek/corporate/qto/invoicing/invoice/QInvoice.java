package com.vertek.corporate.qto.invoicing.invoice;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInvoice is a Querydsl query type for Invoice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvoice extends EntityPathBase<Invoice> {

    private static final long serialVersionUID = 1976087091L;

    public static final QInvoice invoice = new QInvoice("invoice");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final StringPath clientName = createString("clientName");

    public final StringPath generatedBy = createString("generatedBy");

    public final DateTimePath<java.util.Date> generatedDate = createDateTime("generatedDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> invoiceEnd = createDateTime("invoiceEnd", java.util.Date.class);

    public final StringPath invoiceNumber = createString("invoiceNumber");

    public final DateTimePath<java.util.Date> invoiceStart = createDateTime("invoiceStart", java.util.Date.class);

    public final StringPath invoiceStatus = createString("invoiceStatus");

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final NumberPath<java.math.BigDecimal> totalCharges = createNumber("totalCharges", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QInvoice(String variable) {
        super(Invoice.class, forVariable(variable));
    }

    public QInvoice(Path<? extends Invoice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvoice(PathMetadata metadata) {
        super(Invoice.class, metadata);
    }

}

