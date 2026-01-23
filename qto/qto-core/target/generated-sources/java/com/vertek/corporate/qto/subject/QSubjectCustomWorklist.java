package com.vertek.corporate.qto.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubjectCustomWorklist is a Querydsl query type for SubjectCustomWorklist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubjectCustomWorklist extends EntityPathBase<SubjectCustomWorklist> {

    private static final long serialVersionUID = -427933867L;

    public static final QSubjectCustomWorklist subjectCustomWorklist = new QSubjectCustomWorklist("subjectCustomWorklist");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> customWorklistId = createNumber("customWorklistId", Long.class);

    public final BooleanPath favorite = createBoolean("favorite");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastViewedDate = createDateTime("lastViewedDate", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> subjectId = createNumber("subjectId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QSubjectCustomWorklist(String variable) {
        super(SubjectCustomWorklist.class, forVariable(variable));
    }

    public QSubjectCustomWorklist(Path<? extends SubjectCustomWorklist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubjectCustomWorklist(PathMetadata metadata) {
        super(SubjectCustomWorklist.class, metadata);
    }

}

