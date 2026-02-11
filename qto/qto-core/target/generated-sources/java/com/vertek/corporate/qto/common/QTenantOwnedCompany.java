package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTenantOwnedCompany is a Querydsl query type for TenantOwnedCompany
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTenantOwnedCompany extends EntityPathBase<TenantOwnedCompany> {

    private static final long serialVersionUID = 73062160L;

    public static final QTenantOwnedCompany tenantOwnedCompany = new QTenantOwnedCompany("tenantOwnedCompany");

    public final QAbstractCompany _super = new QAbstractCompany(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final StringPath businessSector = _super.businessSector;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    //inherited
    public final StringPath type = _super.type;

    //inherited
    public final StringPath uuid = _super.uuid;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTenantOwnedCompany(String variable) {
        super(TenantOwnedCompany.class, forVariable(variable));
    }

    public QTenantOwnedCompany(Path<? extends TenantOwnedCompany> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTenantOwnedCompany(PathMetadata metadata) {
        super(TenantOwnedCompany.class, metadata);
    }

}

