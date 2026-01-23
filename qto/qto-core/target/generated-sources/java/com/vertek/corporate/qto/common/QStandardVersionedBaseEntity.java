package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStandardVersionedBaseEntity is a Querydsl query type for StandardVersionedBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QStandardVersionedBaseEntity extends EntityPathBase<StandardVersionedBaseEntity> {

    private static final long serialVersionUID = 248275840L;

    public static final QStandardVersionedBaseEntity standardVersionedBaseEntity = new QStandardVersionedBaseEntity("standardVersionedBaseEntity");

    public final QAbstractVersionedEntity _super = new QAbstractVersionedEntity(this);

    public final BooleanPath new$ = createBoolean("new");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QStandardVersionedBaseEntity(String variable) {
        super(StandardVersionedBaseEntity.class, forVariable(variable));
    }

    public QStandardVersionedBaseEntity(Path<? extends StandardVersionedBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStandardVersionedBaseEntity(PathMetadata metadata) {
        super(StandardVersionedBaseEntity.class, metadata);
    }

}

