package com.vertek.corporate.qto.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -1247896071L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<Long> activationEngineer = createNumber("activationEngineer", Long.class);

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientProjectManager = createString("clientProjectManager");

    public final com.vertek.corporate.qto.company.QCompany company;

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final BooleanPath eligibleForInventory = createBoolean("eligibleForInventory");

    public final BooleanPath finalUpdate = createBoolean("finalUpdate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inventoryOrderId = createNumber("inventoryOrderId", Long.class);

    public final BooleanPath isCurrentInventory = createBoolean("isCurrentInventory");

    public final StringPath lastUpdateBy = createString("lastUpdateBy");

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    public final ListPath<com.vertek.corporate.qto.location.Location, com.vertek.corporate.qto.location.QLocation> locations = this.<com.vertek.corporate.qto.location.Location, com.vertek.corporate.qto.location.QLocation>createList("locations", com.vertek.corporate.qto.location.Location.class, com.vertek.corporate.qto.location.QLocation.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> provisioner = createNumber("provisioner", Long.class);

    public final NumberPath<Long> provisioningOrderId = createNumber("provisioningOrderId", Long.class);

    public final NumberPath<Long> qaManager = createNumber("qaManager", Long.class);

    public final StringPath quoteId = createString("quoteId");

    public final StringPath quoteNumber = createString("quoteNumber");

    public final StringPath status = createString("status");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath vertekClient = createString("vertekClient");

    public final NumberPath<Long> vertekProjectManager = createNumber("vertekProjectManager", Long.class);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.vertek.corporate.qto.company.QCompany(forProperty("company"), inits.get("company")) : null;
    }

}

