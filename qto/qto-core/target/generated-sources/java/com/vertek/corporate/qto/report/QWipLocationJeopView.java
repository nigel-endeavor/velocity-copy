package com.vertek.corporate.qto.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWipLocationJeopView is a Querydsl query type for WipLocationJeopView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWipLocationJeopView extends EntityPathBase<WipLocationJeopView> {

    private static final long serialVersionUID = -1158724785L;

    public static final QWipLocationJeopView wipLocationJeopView = new QWipLocationJeopView("wipLocationJeopView");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath assignedTo = createString("assignedTo");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientProjectManager = createString("clientProjectManager");

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final StringPath jeopDescription = createString("jeopDescription");

    public final NumberPath<Long> jeopInstanceId = createNumber("jeopInstanceId", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath locationName = createString("locationName");

    public final StringPath locationStatus = createString("locationStatus");

    public final StringPath masterCompanyName = createString("masterCompanyName");

    public final NumberPath<Long> masterCustomerId = createNumber("masterCustomerId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> provisionerId = createNumber("provisionerId", Long.class);

    public final StringPath responsibility = createString("responsibility");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final StringPath stateProvince = createString("stateProvince");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final NumberPath<Long> vertekProjectManagerId = createNumber("vertekProjectManagerId", Long.class);

    public QWipLocationJeopView(String variable) {
        super(WipLocationJeopView.class, forVariable(variable));
    }

    public QWipLocationJeopView(Path<? extends WipLocationJeopView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWipLocationJeopView(PathMetadata metadata) {
        super(WipLocationJeopView.class, metadata);
    }

}

