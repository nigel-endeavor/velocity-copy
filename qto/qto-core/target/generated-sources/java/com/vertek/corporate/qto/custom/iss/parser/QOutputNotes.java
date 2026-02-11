package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOutputNotes is a Querydsl query type for OutputNotes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOutputNotes extends EntityPathBase<OutputNotes> {

    private static final long serialVersionUID = -418323426L;

    public static final QOutputNotes outputNotes = new QOutputNotes("outputNotes");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath createdBy = createString("createdBy");

    public final StringPath createdDate = createString("createdDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final StringPath ticketNumber = createString("ticketNumber");

    public QOutputNotes(String variable) {
        super(OutputNotes.class, forVariable(variable));
    }

    public QOutputNotes(Path<? extends OutputNotes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOutputNotes(PathMetadata metadata) {
        super(OutputNotes.class, metadata);
    }

}

