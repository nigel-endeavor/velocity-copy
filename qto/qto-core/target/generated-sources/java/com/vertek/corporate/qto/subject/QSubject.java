package com.vertek.corporate.qto.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubject is a Querydsl query type for Subject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubject extends EntityPathBase<Subject> {

    private static final long serialVersionUID = 808812085L;

    public static final QSubject subject = new QSubject("subject");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> badPasswordCount = createNumber("badPasswordCount", Long.class);

    public final DateTimePath<java.util.Date> badPasswordTime = createDateTime("badPasswordTime", java.util.Date.class);

    public final StringPath displayName = createString("displayName");

    public final StringPath emailAddress = createString("emailAddress");

    public final StringPath encKey = createString("encKey");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastLoginTime = createDateTime("lastLoginTime", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QSubject(String variable) {
        super(Subject.class, forVariable(variable));
    }

    public QSubject(Path<? extends Subject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubject(PathMetadata metadata) {
        super(Subject.class, metadata);
    }

}

