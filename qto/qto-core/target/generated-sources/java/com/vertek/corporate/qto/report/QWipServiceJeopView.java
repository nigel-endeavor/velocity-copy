package com.vertek.corporate.qto.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWipServiceJeopView is a Querydsl query type for WipServiceJeopView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWipServiceJeopView extends EntityPathBase<WipServiceJeopView> {

    private static final long serialVersionUID = 138364029L;

    public static final QWipServiceJeopView wipServiceJeopView = new QWipServiceJeopView("wipServiceJeopView");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath assignedTo = createString("assignedTo");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientProjectManager = createString("clientProjectManager");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final StringPath jeopDescription = createString("jeopDescription");

    public final NumberPath<Long> jeopInstanceId = createNumber("jeopInstanceId", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath locationName = createString("locationName");

    public final StringPath masterCompanyName = createString("masterCompanyName");

    public final NumberPath<Long> masterCustomerId = createNumber("masterCustomerId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath provider = createString("provider");

    public final NumberPath<Long> provisionerId = createNumber("provisionerId", Long.class);

    public final StringPath responsibility = createString("responsibility");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath serviceStatus = createString("serviceStatus");

    public final StringPath serviceType = createString("serviceType");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final StringPath stateProvince = createString("stateProvince");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final NumberPath<Long> vertekProjectManagerId = createNumber("vertekProjectManagerId", Long.class);

    public QWipServiceJeopView(String variable) {
        super(WipServiceJeopView.class, forVariable(variable));
    }

    public QWipServiceJeopView(Path<? extends WipServiceJeopView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWipServiceJeopView(PathMetadata metadata) {
        super(WipServiceJeopView.class, metadata);
    }

}

