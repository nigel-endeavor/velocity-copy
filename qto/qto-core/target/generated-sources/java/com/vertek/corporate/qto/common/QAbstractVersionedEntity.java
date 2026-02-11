package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractVersionedEntity is a Querydsl query type for AbstractVersionedEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractVersionedEntity extends EntityPathBase<AbstractVersionedEntity<? extends java.io.Serializable>> {

    private static final long serialVersionUID = -756367542L;

    public static final QAbstractVersionedEntity abstractVersionedEntity = new QAbstractVersionedEntity("abstractVersionedEntity");

    public final QAbstractBaseEntity _super = new QAbstractBaseEntity(this);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractVersionedEntity(String variable) {
        super((Class) AbstractVersionedEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractVersionedEntity(Path<? extends AbstractVersionedEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QAbstractVersionedEntity(PathMetadata metadata) {
        super((Class) AbstractVersionedEntity.class, metadata);
    }

}

