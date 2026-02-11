package com.vertek.corporate.qto.invoicing.billableMilestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBillableMilestone is a Querydsl query type for BillableMilestone
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBillableMilestone extends EntityPathBase<BillableMilestone> {

    private static final long serialVersionUID = -1187392457L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBillableMilestone billableMilestone = new QBillableMilestone("billableMilestone");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath level = createString("level");

    public final com.vertek.corporate.qto.milestone.QMilestone milestone;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Integer> percentage = createNumber("percentage", Integer.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QBillableMilestone(String variable) {
        this(BillableMilestone.class, forVariable(variable), INITS);
    }

    public QBillableMilestone(Path<? extends BillableMilestone> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBillableMilestone(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBillableMilestone(PathMetadata metadata, PathInits inits) {
        this(BillableMilestone.class, metadata, inits);
    }

    public QBillableMilestone(Class<? extends BillableMilestone> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.milestone = inits.isInitialized("milestone") ? new com.vertek.corporate.qto.milestone.QMilestone(forProperty("milestone")) : null;
    }

}

