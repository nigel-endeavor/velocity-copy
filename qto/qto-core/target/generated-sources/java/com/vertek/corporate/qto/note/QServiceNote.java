package com.vertek.corporate.qto.note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceNote is a Querydsl query type for ServiceNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceNote extends EntityPathBase<ServiceNote> {

    private static final long serialVersionUID = 1666326944L;

    public static final QServiceNote serviceNote = new QServiceNote("serviceNote");

    public final QNote _super = new QNote(this);

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final NumberPath<Long> createdById = _super.createdById;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    //inherited
    public final StringPath editedBy = _super.editedBy;

    //inherited
    public final DateTimePath<java.util.Date> editedDate = _super.editedDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath internalOnly = _super.internalOnly;

    //inherited
    public final NumberPath<Long> legacyId = _super.legacyId;

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final StringPath note = _super.note;

    //inherited
    public final NumberPath<Long> parentNoteId = _super.parentNoteId;

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final BooleanPath updateClient = _super.updateClient;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceNote(String variable) {
        super(ServiceNote.class, forVariable(variable));
    }

    public QServiceNote(Path<? extends ServiceNote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceNote(PathMetadata metadata) {
        super(ServiceNote.class, metadata);
    }

}

