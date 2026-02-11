package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAbstractCompany is a Querydsl query type for AbstractCompany
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAbstractCompany extends EntityPathBase<AbstractCompany> {

    private static final long serialVersionUID = -1768120947L;

    public static final QAbstractCompany abstractCompany = new QAbstractCompany("abstractCompany");

    public final QAbstractTenantOwnedEntity _super = new QAbstractTenantOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath businessSector = createString("businessSector");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    public final StringPath uuid = createString("uuid");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QAbstractCompany(String variable) {
        super(AbstractCompany.class, forVariable(variable));
    }

    public QAbstractCompany(Path<? extends AbstractCompany> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAbstractCompany(PathMetadata metadata) {
        super(AbstractCompany.class, metadata);
    }

}

