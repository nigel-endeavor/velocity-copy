package com.vertek.corporate.qto.note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNoteUnionView is a Querydsl query type for NoteUnionView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNoteUnionView extends EntityPathBase<NoteUnionView> {

    private static final long serialVersionUID = 1505488219L;

    public static final QNoteUnionView noteUnionView = new QNoteUnionView("noteUnionView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath category = createString("category");

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdById = createNumber("createdById", Long.class);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath editedBy = createString("editedBy");

    public final DateTimePath<java.util.Date> editedDate = createDateTime("editedDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath internalOnly = createBoolean("internalOnly");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final NumberPath<Long> parentNoteId = createNumber("parentNoteId", Long.class);

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QNoteUnionView(String variable) {
        super(NoteUnionView.class, forVariable(variable));
    }

    public QNoteUnionView(Path<? extends NoteUnionView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNoteUnionView(PathMetadata metadata) {
        super(NoteUnionView.class, metadata);
    }

}

