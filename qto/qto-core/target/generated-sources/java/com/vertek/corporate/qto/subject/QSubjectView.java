package com.vertek.corporate.qto.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubjectView is a Querydsl query type for SubjectView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubjectView extends EntityPathBase<SubjectView> {

    private static final long serialVersionUID = 5900922L;

    public static final QSubjectView subjectView = new QSubjectView("subjectView");

    public final BooleanPath active = createBoolean("active");

    public final StringPath displayName = createString("displayName");

    public final StringPath emailAddress = createString("emailAddress");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath username = createString("username");

    public QSubjectView(String variable) {
        super(SubjectView.class, forVariable(variable));
    }

    public QSubjectView(Path<? extends SubjectView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubjectView(PathMetadata metadata) {
        super(SubjectView.class, metadata);
    }

}

