package com.vertek.corporate.qto.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivationAttemptView is a Querydsl query type for ActivationAttemptView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationAttemptView extends EntityPathBase<ActivationAttemptView> {

    private static final long serialVersionUID = -1017250633L;

    public static final QActivationAttemptView activationAttemptView = new QActivationAttemptView("activationAttemptView");

    public final NumberPath<Long> aaMonth = createNumber("aaMonth", Long.class);

    public final NumberPath<Long> aaYear = createNumber("aaYear", Long.class);

    public final NumberPath<Long> activationAttemptId = createNumber("activationAttemptId", Long.class);

    public final NumberPath<Long> attemptNumber = createNumber("attemptNumber", Long.class);

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> dataProvisioningComplete = createDateTime("dataProvisioningComplete", java.util.Date.class);

    public final DateTimePath<java.util.Date> fieldTechCheckIn = createDateTime("fieldTechCheckIn", java.util.Date.class);

    public final DateTimePath<java.util.Date> fieldTechCheckOut = createDateTime("fieldTechCheckOut", java.util.Date.class);

    public final NumberPath<Long> locationActivationInterval = createNumber("locationActivationInterval", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath locationName = createString("locationName");

    public final StringPath masterCompanyName = createString("masterCompanyName");

    public final NumberPath<Long> masterCustomerId = createNumber("masterCustomerId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath provider = createString("provider");

    public final StringPath scheduledAttemptStatus = createString("scheduledAttemptStatus");

    public final NumberPath<Integer> serviceActivationInterval = createNumber("serviceActivationInterval", Integer.class);

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final DateTimePath<java.util.Date> serviceComplete = createDateTime("serviceComplete", java.util.Date.class);

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath serviceType = createString("serviceType");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public QActivationAttemptView(String variable) {
        super(ActivationAttemptView.class, forVariable(variable));
    }

    public QActivationAttemptView(Path<? extends ActivationAttemptView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationAttemptView(PathMetadata metadata) {
        super(ActivationAttemptView.class, metadata);
    }

}

