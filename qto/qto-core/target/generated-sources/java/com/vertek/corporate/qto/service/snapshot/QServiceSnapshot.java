package com.vertek.corporate.qto.service.snapshot;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceSnapshot is a Querydsl query type for ServiceSnapshot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceSnapshot extends EntityPathBase<ServiceSnapshot> {

    private static final long serialVersionUID = -1111437347L;

    public static final QServiceSnapshot serviceSnapshot = new QServiceSnapshot("serviceSnapshot");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<java.math.BigDecimal> annualRecurringCost = createNumber("annualRecurringCost", java.math.BigDecimal.class);

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    public final BooleanPath currentInventory = createBoolean("currentInventory");

    public final DateTimePath<java.util.Date> dataProvisioningCompleteDate = createDateTime("dataProvisioningCompleteDate", java.util.Date.class);

    public final NumberPath<Long> endCustomerId = createNumber("endCustomerId", Long.class);

    public final NumberPath<java.math.BigDecimal> icb = createNumber("icb", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<java.math.BigDecimal> osp = createNumber("osp", java.math.BigDecimal.class);

    public final StringPath provider = createString("provider");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath serviceType = createString("serviceType");

    public final DateTimePath<java.util.Date> snapshotDate = createDateTime("snapshotDate", java.util.Date.class);

    public final StringPath status = createString("status");

    public final StringPath subProductType = createString("subProductType");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceSnapshot(String variable) {
        super(ServiceSnapshot.class, forVariable(variable));
    }

    public QServiceSnapshot(Path<? extends ServiceSnapshot> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceSnapshot(PathMetadata metadata) {
        super(ServiceSnapshot.class, metadata);
    }

}

