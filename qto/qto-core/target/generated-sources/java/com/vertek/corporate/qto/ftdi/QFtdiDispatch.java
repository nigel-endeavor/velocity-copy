package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFtdiDispatch is a Querydsl query type for FtdiDispatch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiDispatch extends EntityPathBase<FtdiDispatch> {

    private static final long serialVersionUID = -1732854541L;

    public static final QFtdiDispatch ftdiDispatch = new QFtdiDispatch("ftdiDispatch");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final ListPath<FtdiAppointment, QFtdiAppointment> appointments = this.<FtdiAppointment, QFtdiAppointment>createList("appointments", FtdiAppointment.class, QFtdiAppointment.class, PathInits.DIRECT2);

    public final StringPath errorString = createString("errorString");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final ListPath<FtdiNote, QFtdiNote> notes = this.<FtdiNote, QFtdiNote>createList("notes", FtdiNote.class, QFtdiNote.class, PathInits.DIRECT2);

    public final BooleanPath onHold = createBoolean("onHold");

    public final DateTimePath<java.util.Date> orderCreateDate = createDateTime("orderCreateDate", java.util.Date.class);

    public final StringPath orderType = createString("orderType");

    public final NumberPath<Long> orderTypeId = createNumber("orderTypeId", Long.class);

    public final StringPath parentVendorDispatchId = createString("parentVendorDispatchId");

    public final StringPath po = createString("po");

    public final NumberPath<Integer> resultId = createNumber("resultId", Integer.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final StringPath sr = createString("sr");

    public final StringPath status = createString("status");

    public final StringPath subject = createString("subject");

    public final StringPath vendor = createString("vendor");

    public final StringPath vendorDispatchId = createString("vendorDispatchId");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiDispatch(String variable) {
        super(FtdiDispatch.class, forVariable(variable));
    }

    public QFtdiDispatch(Path<? extends FtdiDispatch> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiDispatch(PathMetadata metadata) {
        super(FtdiDispatch.class, metadata);
    }

}

