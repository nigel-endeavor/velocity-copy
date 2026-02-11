package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStandardBaseEntity is a Querydsl query type for StandardBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QStandardBaseEntity extends EntityPathBase<StandardBaseEntity> {

    private static final long serialVersionUID = 383706079L;

    public static final QStandardBaseEntity standardBaseEntity = new QStandardBaseEntity("standardBaseEntity");

    public final QAbstractBaseEntity _super = new QAbstractBaseEntity(this);

    public final BooleanPath new$ = createBoolean("new");

    public QStandardBaseEntity(String variable) {
        super(StandardBaseEntity.class, forVariable(variable));
    }

    public QStandardBaseEntity(Path<? extends StandardBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStandardBaseEntity(PathMetadata metadata) {
        super(StandardBaseEntity.class, metadata);
    }

}

