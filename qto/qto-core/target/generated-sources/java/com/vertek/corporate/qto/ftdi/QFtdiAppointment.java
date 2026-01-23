package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiAppointment is a Querydsl query type for FtdiAppointment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiAppointment extends EntityPathBase<FtdiAppointment> {

    private static final long serialVersionUID = -503585658L;

    public static final QFtdiAppointment ftdiAppointment = new QFtdiAppointment("ftdiAppointment");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final DateTimePath<java.util.Date> appointmentDate = createDateTime("appointmentDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> eta = createDateTime("eta", java.util.Date.class);

    public final NumberPath<Long> ftdiDispatchId = createNumber("ftdiDispatchId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    public final DateTimePath<java.util.Date> modTimeOffSite = createDateTime("modTimeOffSite", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> sr = createNumber("sr", Long.class);

    public final StringPath status = createString("status");

    public final StringPath techCell = createString("techCell");

    public final StringPath techName = createString("techName");

    public final DateTimePath<java.util.Date> timeOffSite = createDateTime("timeOffSite", java.util.Date.class);

    public final DateTimePath<java.util.Date> timeOnSite = createDateTime("timeOnSite", java.util.Date.class);

    public final StringPath vendor_name = createString("vendor_name");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final DateTimePath<java.util.Date> windowEnd = createDateTime("windowEnd", java.util.Date.class);

    public final DateTimePath<java.util.Date> windowStart = createDateTime("windowStart", java.util.Date.class);

    public QFtdiAppointment(String variable) {
        super(FtdiAppointment.class, forVariable(variable));
    }

    public QFtdiAppointment(Path<? extends FtdiAppointment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiAppointment(PathMetadata metadata) {
        super(FtdiAppointment.class, metadata);
    }

}

