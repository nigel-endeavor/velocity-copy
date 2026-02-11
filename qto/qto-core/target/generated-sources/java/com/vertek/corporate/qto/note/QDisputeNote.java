package com.vertek.corporate.qto.note;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDisputeNote is a Querydsl query type for DisputeNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDisputeNote extends EntityPathBase<DisputeNote> {

    private static final long serialVersionUID = 937162799L;

    public static final QDisputeNote disputeNote = new QDisputeNote("disputeNote");

    public final QNote _super = new QNote(this);

    //inherited
    public final StringPath category = _super.category;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final NumberPath<Long> createdById = _super.createdById;

    //inherited
    public final DateTimePath<java.util.Date> createdDate = _super.createdDate;

    public final NumberPath<Long> disputeId = createNumber("disputeId", Long.class);

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

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final BooleanPath updateClient = _super.updateClient;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QDisputeNote(String variable) {
        super(DisputeNote.class, forVariable(variable));
    }

    public QDisputeNote(Path<? extends DisputeNote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDisputeNote(PathMetadata metadata) {
        super(DisputeNote.class, metadata);
    }

}

