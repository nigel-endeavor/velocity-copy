package com.vertek.corporate.qto.activation.attempt.emailView;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivationAttemptEmailView is a Querydsl query type for ActivationAttemptEmailView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationAttemptEmailView extends EntityPathBase<ActivationAttemptEmailView> {

    private static final long serialVersionUID = 1080856667L;

    public static final QActivationAttemptEmailView activationAttemptEmailView = new QActivationAttemptEmailView("activationAttemptEmailView");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final StringPath address1 = createString("address1");

    public final StringPath backupDownloadSpeed = createString("backupDownloadSpeed");

    public final StringPath backupUploadSpeed = createString("backupUploadSpeed");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath closeNotes = createString("closeNotes");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath country = createString("country");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath locationDowntimeForCutover = createString("locationDowntimeForCutover");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath locationName = createString("locationName");

    public final DateTimePath<java.util.Date> networkCompleteDate = createDateTime("networkCompleteDate", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath postalCode = createString("postalCode");

    public final StringPath provider = createString("provider");

    public final StringPath replace4g5gWithBroadbandDia = createString("replace4g5gWithBroadbandDia");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath signalRsrp = createString("signalRsrp");

    public final StringPath sinrRsrq = createString("sinrRsrq");

    public final StringPath stateProvince = createString("stateProvince");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath testedDownloadSpeed = createString("testedDownloadSpeed");

    public final StringPath testedUploadSpeed = createString("testedUploadSpeed");

    public final StringPath totalAppointmentTime = createString("totalAppointmentTime");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final DateTimePath<java.util.Date> voipCompleteDate = createDateTime("voipCompleteDate", java.util.Date.class);

    public QActivationAttemptEmailView(String variable) {
        super(ActivationAttemptEmailView.class, forVariable(variable));
    }

    public QActivationAttemptEmailView(Path<? extends ActivationAttemptEmailView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationAttemptEmailView(PathMetadata metadata) {
        super(ActivationAttemptEmailView.class, metadata);
    }

}

