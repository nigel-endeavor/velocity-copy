package com.vertek.corporate.qto.service.historyview;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceHistoryView is a Querydsl query type for ServiceHistoryView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceHistoryView extends EntityPathBase<ServiceHistoryView> {

    private static final long serialVersionUID = 140130407L;

    public static final QServiceHistoryView serviceHistoryView = new QServiceHistoryView("serviceHistoryView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final DateTimePath<java.util.Date> cancelled = createDateTime("cancelled", java.util.Date.class);

    public final StringPath clientServiceId = createString("clientServiceId");

    public final DateTimePath<java.util.Date> complete = createDateTime("complete", java.util.Date.class);

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderType = createString("orderType");

    public final NumberPath<Long> parentServiceId = createNumber("parentServiceId", Long.class);

    public final StringPath parentServiceLink = createString("parentServiceLink");

    public final StringPath provider = createString("provider");

    public final StringPath providerOrderNum = createString("providerOrderNum");

    public final StringPath serviceStatus = createString("serviceStatus");

    public final StringPath subOrderType = createString("subOrderType");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceHistoryView(String variable) {
        super(ServiceHistoryView.class, forVariable(variable));
    }

    public QServiceHistoryView(Path<? extends ServiceHistoryView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceHistoryView(PathMetadata metadata) {
        super(ServiceHistoryView.class, metadata);
    }

}

