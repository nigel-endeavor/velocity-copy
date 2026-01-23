package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractBaseEntity is a Querydsl query type for AbstractBaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractBaseEntity extends EntityPathBase<AbstractBaseEntity<? extends java.io.Serializable>> {

    private static final long serialVersionUID = -988480764L;

    public static final QAbstractBaseEntity abstractBaseEntity = new QAbstractBaseEntity("abstractBaseEntity");

    public final BooleanPath new$ = createBoolean("new");

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractBaseEntity(String variable) {
        super((Class) AbstractBaseEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractBaseEntity(Path<? extends AbstractBaseEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractBaseEntity(PathMetadata metadata) {
        super((Class) AbstractBaseEntity.class, metadata);
    }

}

