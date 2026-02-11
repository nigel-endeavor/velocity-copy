package com.vertek.corporate.qto.activation.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivationSchedule is a Querydsl query type for ActivationSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationSchedule extends EntityPathBase<ActivationSchedule> {

    private static final long serialVersionUID = 1847268737L;

    public static final QActivationSchedule activationSchedule = new QActivationSchedule("activationSchedule");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final ListPath<ActivationScheduleCustom, QActivationScheduleCustom> customFields = this.<ActivationScheduleCustom, QActivationScheduleCustom>createList("customFields", ActivationScheduleCustom.class, QActivationScheduleCustom.class, PathInits.DIRECT2);

    public final StringPath description = createString("description");

    public final ListPath<com.vertek.corporate.qto.ftdi.FtdiDispatch, com.vertek.corporate.qto.ftdi.QFtdiDispatch> dispatches = this.<com.vertek.corporate.qto.ftdi.FtdiDispatch, com.vertek.corporate.qto.ftdi.QFtdiDispatch>createList("dispatches", com.vertek.corporate.qto.ftdi.FtdiDispatch.class, com.vertek.corporate.qto.ftdi.QFtdiDispatch.class, PathInits.DIRECT2);

    public final ListPath<ActivationScheduleEquipment, QActivationScheduleEquipment> equipment = this.<ActivationScheduleEquipment, QActivationScheduleEquipment>createList("equipment", ActivationScheduleEquipment.class, QActivationScheduleEquipment.class, PathInits.DIRECT2);

    public final NumberPath<Long> ftdiOrderTypeId = createNumber("ftdiOrderTypeId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> latestRequestedDate = createDateTime("latestRequestedDate", java.util.Date.class);

    public final NumberPath<Long> legacyCtnId = createNumber("legacyCtnId", Long.class);

    public final NumberPath<Long> legacyDispatchId = createNumber("legacyDispatchId", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> requestedDate = createDateTime("requestedDate", java.util.Date.class);

    public final StringPath requestedDays = createString("requestedDays");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath technicalNote = createString("technicalNote");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath vendor = createString("vendor");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QActivationSchedule(String variable) {
        super(ActivationSchedule.class, forVariable(variable));
    }

    public QActivationSchedule(Path<? extends ActivationSchedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationSchedule(PathMetadata metadata) {
        super(ActivationSchedule.class, metadata);
    }

}

