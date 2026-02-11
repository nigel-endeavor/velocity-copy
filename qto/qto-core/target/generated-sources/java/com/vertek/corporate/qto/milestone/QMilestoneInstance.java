package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMilestoneInstance is a Querydsl query type for MilestoneInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMilestoneInstance extends EntityPathBase<MilestoneInstance> {

    private static final long serialVersionUID = 1094889586L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMilestoneInstance milestoneInstance = new QMilestoneInstance("milestoneInstance");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final BooleanPath historic = createBoolean("historic");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> invoiceId = createNumber("invoiceId", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final QMilestone milestone;

    public final DateTimePath<java.util.Date> milestoneDate = createDateTime("milestoneDate", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath param = createString("param");

    public final NumberPath<Long> parentMilestoneInstanceId = createNumber("parentMilestoneInstanceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMilestoneInstance(String variable) {
        this(MilestoneInstance.class, forVariable(variable), INITS);
    }

    public QMilestoneInstance(Path<? extends MilestoneInstance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMilestoneInstance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMilestoneInstance(PathMetadata metadata, PathInits inits) {
        this(MilestoneInstance.class, metadata, inits);
    }

    public QMilestoneInstance(Class<? extends MilestoneInstance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.milestone = inits.isInitialized("milestone") ? new QMilestone(forProperty("milestone")) : null;
    }

}

