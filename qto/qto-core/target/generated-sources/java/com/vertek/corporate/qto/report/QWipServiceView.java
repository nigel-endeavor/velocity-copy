package com.vertek.corporate.qto.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWipServiceView is a Querydsl query type for WipServiceView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWipServiceView extends EntityPathBase<WipServiceView> {

    private static final long serialVersionUID = 1987818497L;

    public static final QWipServiceView wipServiceView = new QWipServiceView("wipServiceView");

    public final BooleanPath active = createBoolean("active");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientProjectManager = createString("clientProjectManager");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    public final BooleanPath currentInventory = createBoolean("currentInventory");

    public final DateTimePath<java.util.Date> dataProvisioningCompleteDate = createDateTime("dataProvisioningCompleteDate", java.util.Date.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath locationName = createString("locationName");

    public final StringPath masterCompanyName = createString("masterCompanyName");

    public final NumberPath<Long> masterCustomerId = createNumber("masterCustomerId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath provider = createString("provider");

    public final NumberPath<Long> provisionerId = createNumber("provisionerId", Long.class);

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final NumberPath<java.math.BigDecimal> serviceMrc = createNumber("serviceMrc", java.math.BigDecimal.class);

    public final StringPath serviceStatus = createString("serviceStatus");

    public final StringPath serviceType = createString("serviceType");

    public final StringPath stateProvince = createString("stateProvince");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final NumberPath<Long> vertekProjectManagerId = createNumber("vertekProjectManagerId", Long.class);

    public QWipServiceView(String variable) {
        super(WipServiceView.class, forVariable(variable));
    }

    public QWipServiceView(Path<? extends WipServiceView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWipServiceView(PathMetadata metadata) {
        super(WipServiceView.class, metadata);
    }

}

