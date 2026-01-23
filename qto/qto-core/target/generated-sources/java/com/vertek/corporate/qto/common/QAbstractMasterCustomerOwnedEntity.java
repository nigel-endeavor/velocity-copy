package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractMasterCustomerOwnedEntity is a Querydsl query type for AbstractMasterCustomerOwnedEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractMasterCustomerOwnedEntity extends EntityPathBase<AbstractMasterCustomerOwnedEntity> {

    private static final long serialVersionUID = 1573474168L;

    public static final QAbstractMasterCustomerOwnedEntity abstractMasterCustomerOwnedEntity = new QAbstractMasterCustomerOwnedEntity("abstractMasterCustomerOwnedEntity");

    public final QAbstractTenantOwnedEntity _super = new QAbstractTenantOwnedEntity(this);

    public final NumberPath<Long> masterCustomerId = createNumber("masterCustomerId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QAbstractMasterCustomerOwnedEntity(String variable) {
        super(AbstractMasterCustomerOwnedEntity.class, forVariable(variable));
    }

    public QAbstractMasterCustomerOwnedEntity(Path<? extends AbstractMasterCustomerOwnedEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractMasterCustomerOwnedEntity(PathMetadata metadata) {
        super(AbstractMasterCustomerOwnedEntity.class, metadata);
    }

}

