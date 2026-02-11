package com.vertek.corporate.qto.invoicing.surcharge;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSurcharge is a Querydsl query type for Surcharge
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSurcharge extends EntityPathBase<Surcharge> {

    private static final long serialVersionUID = -1497956895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSurcharge surcharge = new QSurcharge("surcharge");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath addedBy = createString("addedBy");

    public final BooleanPath finalized = createBoolean("finalized");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> invoiceChargeId = createNumber("invoiceChargeId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> surchargeDate = createDateTime("surchargeDate", java.util.Date.class);

    public final com.vertek.corporate.qto.invoicing.surchargeType.QSurchargeType surchargeType;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QSurcharge(String variable) {
        this(Surcharge.class, forVariable(variable), INITS);
    }

    public QSurcharge(Path<? extends Surcharge> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSurcharge(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSurcharge(PathMetadata metadata, PathInits inits) {
        this(Surcharge.class, metadata, inits);
    }

    public QSurcharge(Class<? extends Surcharge> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.surchargeType = inits.isInitialized("surchargeType") ? new com.vertek.corporate.qto.invoicing.surchargeType.QSurchargeType(forProperty("surchargeType")) : null;
    }

}

