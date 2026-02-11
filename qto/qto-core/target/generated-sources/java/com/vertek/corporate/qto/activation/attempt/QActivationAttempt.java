package com.vertek.corporate.qto.activation.attempt;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivationAttempt is a Querydsl query type for ActivationAttempt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationAttempt extends EntityPathBase<ActivationAttempt> {

    private static final long serialVersionUID = 1886607859L;

    public static final QActivationAttempt activationAttempt = new QActivationAttempt("activationAttempt");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<Long> activationScheduleId = createNumber("activationScheduleId", Long.class);

    public final NumberPath<Long> attemptNumber = createNumber("attemptNumber", Long.class);

    public final StringPath backupDownloadSpeed = createString("backupDownloadSpeed");

    public final StringPath backupUploadSpeed = createString("backupUploadSpeed");

    public final StringPath cancelledBy = createString("cancelledBy");

    public final DateTimePath<java.util.Date> cancelledDate = createDateTime("cancelledDate", java.util.Date.class);

    public final StringPath closeNotes = createString("closeNotes");

    public final StringPath closeoutCode = createString("closeoutCode");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final BooleanPath duplicateToRelated = createBoolean("duplicateToRelated");

    public final StringPath fieldDispatchVendor = createString("fieldDispatchVendor");

    public final DateTimePath<java.util.Date> fieldTechCheckIn = createDateTime("fieldTechCheckIn", java.util.Date.class);

    public final DateTimePath<java.util.Date> fieldTechCheckOut = createDateTime("fieldTechCheckOut", java.util.Date.class);

    public final StringPath fieldTechName = createString("fieldTechName");

    public final StringPath fieldTechPhone = createString("fieldTechPhone");

    public final NumberPath<Long> ftdiAppointmentId = createNumber("ftdiAppointmentId", Long.class);

    public final NumberPath<Long> ftdiDispatchId = createNumber("ftdiDispatchId", Long.class);

    public final StringPath ftdiVendorId = createString("ftdiVendorId");

    public final StringPath ftdiVendorStatus = createString("ftdiVendorStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath internalTechAssigned = createString("internalTechAssigned");

    public final StringPath issueNotes = createString("issueNotes");

    public final StringPath latency = createString("latency");

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    public final StringPath locationDowntownForCutover = createString("locationDowntownForCutover");

    public final StringPath managedRouterSerialNumber = createString("managedRouterSerialNumber");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final DateTimePath<java.util.Date> networkCompleteDate = createDateTime("networkCompleteDate", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath poNumber = createString("poNumber");

    public final StringPath primaryUid = createString("primaryUid");

    public final StringPath replace4g5g = createString("replace4g5g");

    public final ListPath<com.vertek.corporate.qto.activation.requirement.ActivationAttemptRequirement, com.vertek.corporate.qto.activation.requirement.QActivationAttemptRequirement> requirements = this.<com.vertek.corporate.qto.activation.requirement.ActivationAttemptRequirement, com.vertek.corporate.qto.activation.requirement.QActivationAttemptRequirement>createList("requirements", com.vertek.corporate.qto.activation.requirement.ActivationAttemptRequirement.class, com.vertek.corporate.qto.activation.requirement.QActivationAttemptRequirement.class, PathInits.DIRECT2);

    public final BooleanPath sameDaySchedule = createBoolean("sameDaySchedule");

    public final StringPath scheduledAttemptStatus = createString("scheduledAttemptStatus");

    public final DateTimePath<java.util.Date> scheduledCheckInTime = createDateTime("scheduledCheckInTime", java.util.Date.class);

    public final StringPath secondaryUid = createString("secondaryUid");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath signalRsrp = createString("signalRsrp");

    public final StringPath sinrRsrq = createString("sinrRsrq");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath testedDownloadSpeed = createString("testedDownloadSpeed");

    public final StringPath testedUploadSpeed = createString("testedUploadSpeed");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final DateTimePath<java.util.Date> voipCompleteDate = createDateTime("voipCompleteDate", java.util.Date.class);

    public final StringPath warningMessage = createString("warningMessage");

    public QActivationAttempt(String variable) {
        super(ActivationAttempt.class, forVariable(variable));
    }

    public QActivationAttempt(Path<? extends ActivationAttempt> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationAttempt(PathMetadata metadata) {
        super(ActivationAttempt.class, metadata);
    }

}

