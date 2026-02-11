package com.vertek.corporate.qto.activation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivationView is a Querydsl query type for ActivationView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationView extends EntityPathBase<ActivationView> {

    private static final long serialVersionUID = 1637119742L;

    public static final QActivationView activationView = new QActivationView("activationView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final DateTimePath<java.util.Date> fieldTechCheckIn = createDateTime("fieldTechCheckIn", java.util.Date.class);

    public final StringPath fieldTechCheckInFormatted = createString("fieldTechCheckInFormatted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath internalTechAssigned = createString("internalTechAssigned");

    public final StringPath lastUpdateBy = createString("lastUpdateBy");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath scheduledAttemptStatus = createString("scheduledAttemptStatus");

    public final StringPath scheduledCheckInFormatted = createString("scheduledCheckInFormatted");

    public final DateTimePath<java.util.Date> scheduledCheckInTime = createDateTime("scheduledCheckInTime", java.util.Date.class);

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final NumberPath<java.math.BigDecimal> ttuEquivalent = createNumber("ttuEquivalent", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QActivationView(String variable) {
        super(ActivationView.class, forVariable(variable));
    }

    public QActivationView(Path<? extends ActivationView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationView(PathMetadata metadata) {
        super(ActivationView.class, metadata);
    }

}

