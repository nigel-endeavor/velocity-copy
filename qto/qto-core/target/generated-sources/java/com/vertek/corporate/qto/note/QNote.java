package com.vertek.corporate.qto.note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNote is a Querydsl query type for Note
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNote extends EntityPathBase<Note> {

    private static final long serialVersionUID = 624359609L;

    public static final QNote note1 = new QNote("note1");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath category = createString("category");

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdById = createNumber("createdById", Long.class);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath editedBy = createString("editedBy");

    public final DateTimePath<java.util.Date> editedDate = createDateTime("editedDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath internalOnly = createBoolean("internalOnly");

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final NumberPath<Long> parentNoteId = createNumber("parentNoteId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final BooleanPath updateClient = createBoolean("updateClient");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QNote(String variable) {
        super(Note.class, forVariable(variable));
    }

    public QNote(Path<? extends Note> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNote(PathMetadata metadata) {
        super(Note.class, metadata);
    }

}

