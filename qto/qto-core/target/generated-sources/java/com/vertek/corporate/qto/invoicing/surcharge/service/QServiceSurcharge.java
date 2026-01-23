package com.vertek.corporate.qto.invoicing.surcharge.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QServiceSurcharge is a Querydsl query type for ServiceSurcharge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceSurcharge extends EntityPathBase<ServiceSurcharge> {

    private static final long serialVersionUID = 560482667L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QServiceSurcharge serviceSurcharge = new QServiceSurcharge("serviceSurcharge");

    public final com.vertek.corporate.qto.invoicing.surcharge.QSurcharge _super;

    //inherited
    public final StringPath addedBy;

    //inherited
    public final BooleanPath finalized;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Long> invoiceChargeId;

    //inherited
    public final NumberPath<Long> masterCustomerId;

    //inherited
    public final BooleanPath new$;

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> surchargeDate;

    // inherited
    public final com.vertek.corporate.qto.invoicing.surchargeType.QSurchargeType surchargeType;

    //inherited
    public final NumberPath<Long> tenantId;

    //inherited
    public final NumberPath<Integer> version;

    public QServiceSurcharge(String variable) {
        this(ServiceSurcharge.class, forVariable(variable), INITS);
    }

    public QServiceSurcharge(Path<? extends ServiceSurcharge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QServiceSurcharge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QServiceSurcharge(PathMetadata metadata, PathInits inits) {
        this(ServiceSurcharge.class, metadata, inits);
    }

    public QServiceSurcharge(Class<? extends ServiceSurcharge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.vertek.corporate.qto.invoicing.surcharge.QSurcharge(type, metadata, inits);
        this.addedBy = _super.addedBy;
        this.finalized = _super.finalized;
        this.id = _super.id;
        this.invoiceChargeId = _super.invoiceChargeId;
        this.masterCustomerId = _super.masterCustomerId;
        this.new$ = _super.new$;
        this.surchargeDate = _super.surchargeDate;
        this.surchargeType = _super.surchargeType;
        this.tenantId = _super.tenantId;
        this.version = _super.version;
    }

}

