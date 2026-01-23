package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiNote is a Querydsl query type for FtdiNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiNote extends EntityPathBase<FtdiNote> {

    private static final long serialVersionUID = 787107051L;

    public static final QFtdiNote ftdiNote = new QFtdiNote("ftdiNote");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> dispatchVendorId = createNumber("dispatchVendorId", Long.class);

    public final NumberPath<Long> endId = createNumber("endId", Long.class);

    public final NumberPath<Long> ftdiDispatchId = createNumber("ftdiDispatchId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    public final StringPath level = createString("level");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final NumberPath<Long> noteParsed = createNumber("noteParsed", Long.class);

    public final DateTimePath<java.util.Date> postDate = createDateTime("postDate", java.util.Date.class);

    public final StringPath postedBy = createString("postedBy");

    public final NumberPath<Long> sr = createNumber("sr", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiNote(String variable) {
        super(FtdiNote.class, forVariable(variable));
    }

    public QFtdiNote(Path<? extends FtdiNote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiNote(PathMetadata metadata) {
        super(FtdiNote.class, metadata);
    }

}

