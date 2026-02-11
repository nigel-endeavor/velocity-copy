package com.vertek.corporate.qto.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTenantView is a Querydsl query type for TenantView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTenantView extends EntityPathBase<TenantView> {

    private static final long serialVersionUID = -1051919459L;

    public static final QTenantView tenantView = new QTenantView("tenantView");

    public final QStandardVersionedBaseEntity _super = new QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTenantView(String variable) {
        super(TenantView.class, forVariable(variable));
    }

    public QTenantView(Path<? extends TenantView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTenantView(PathMetadata metadata) {
        super(TenantView.class, metadata);
    }

}

