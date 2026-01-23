package com.vertek.corporate.qto.inventory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPendingDisconnect is a Querydsl query type for PendingDisconnect
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPendingDisconnect extends EntityPathBase<PendingDisconnect> {

    private static final long serialVersionUID = -569354036L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPendingDisconnect pendingDisconnect = new QPendingDisconnect("pendingDisconnect");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final com.vertek.corporate.qto.service.QService childService;

    public final StringPath disconnectReason = createString("disconnectReason");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final com.vertek.corporate.qto.service.QService newService;

    public final DateTimePath<java.util.Date> newServiceCompleteDate = createDateTime("newServiceCompleteDate", java.util.Date.class);

    public final com.vertek.corporate.qto.service.QService parentService;

    public QPendingDisconnect(String variable) {
        this(PendingDisconnect.class, forVariable(variable), INITS);
    }

    public QPendingDisconnect(Path<? extends PendingDisconnect> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPendingDisconnect(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPendingDisconnect(PathMetadata metadata, PathInits inits) {
        this(PendingDisconnect.class, metadata, inits);
    }

    public QPendingDisconnect(Class<? extends PendingDisconnect> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.childService = inits.isInitialized("childService") ? new com.vertek.corporate.qto.service.QService(forProperty("childService"), inits.get("childService")) : null;
        this.newService = inits.isInitialized("newService") ? new com.vertek.corporate.qto.service.QService(forProperty("newService"), inits.get("newService")) : null;
        this.parentService = inits.isInitialized("parentService") ? new com.vertek.corporate.qto.service.QService(forProperty("parentService"), inits.get("parentService")) : null;
    }

}

