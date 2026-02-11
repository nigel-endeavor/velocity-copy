package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractTenantOwnedEntity is a Querydsl query type for AbstractTenantOwnedEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractTenantOwnedEntity extends EntityPathBase<AbstractTenantOwnedEntity> {

    private static final long serialVersionUID = 860115182L;

    public static final QAbstractTenantOwnedEntity abstractTenantOwnedEntity = new QAbstractTenantOwnedEntity("abstractTenantOwnedEntity");

    public final QStandardVersionedBaseEntity _super = new QStandardVersionedBaseEntity(this);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QAbstractTenantOwnedEntity(String variable) {
        super(AbstractTenantOwnedEntity.class, forVariable(variable));
    }

    public QAbstractTenantOwnedEntity(Path<? extends AbstractTenantOwnedEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractTenantOwnedEntity(PathMetadata metadata) {
        super(AbstractTenantOwnedEntity.class, metadata);
    }

}

