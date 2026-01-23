package com.vertek.corporate.qto.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTenantSubject is a Querydsl query type for TenantSubject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTenantSubject extends EntityPathBase<TenantSubject> {

    private static final long serialVersionUID = -1463726229L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTenantSubject tenantSubject = new QTenantSubject("tenantSubject");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSelected = createBoolean("isSelected");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final QSubject subject;

    public final com.vertek.corporate.qto.common.QTenant tenant;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTenantSubject(String variable) {
        this(TenantSubject.class, forVariable(variable), INITS);
    }

    public QTenantSubject(Path<? extends TenantSubject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTenantSubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTenantSubject(PathMetadata metadata, PathInits inits) {
        this(TenantSubject.class, metadata, inits);
    }

    public QTenantSubject(Class<? extends TenantSubject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subject = inits.isInitialized("subject") ? new QSubject(forProperty("subject")) : null;
        this.tenant = inits.isInitialized("tenant") ? new com.vertek.corporate.qto.common.QTenant(forProperty("tenant")) : null;
    }

}

