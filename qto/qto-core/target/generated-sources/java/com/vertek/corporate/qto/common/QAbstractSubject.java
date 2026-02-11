package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractSubject is a Querydsl query type for AbstractSubject
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractSubject extends EntityPathBase<AbstractSubject> {

    private static final long serialVersionUID = -291523012L;

    public static final QAbstractSubject abstractSubject = new QAbstractSubject("abstractSubject");

    public final QStandardVersionedBaseEntity _super = new QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Integer> badPasswordCount = createNumber("badPasswordCount", Integer.class);

    public final DateTimePath<java.util.Date> badPasswordTime = createDateTime("badPasswordTime", java.util.Date.class);

    public final StringPath displayName = createString("displayName");

    public final StringPath emailAddress = createString("emailAddress");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath username = createString("username");

    public final StringPath uuid = createString("uuid");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QAbstractSubject(String variable) {
        super(AbstractSubject.class, forVariable(variable));
    }

    public QAbstractSubject(Path<? extends AbstractSubject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractSubject(PathMetadata metadata) {
        super(AbstractSubject.class, metadata);
    }

}

