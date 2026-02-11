package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMilestoneDisplaySetInclude is a Querydsl query type for MilestoneDisplaySetInclude
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMilestoneDisplaySetInclude extends EntityPathBase<MilestoneDisplaySetInclude> {

    private static final long serialVersionUID = -1359399669L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMilestoneDisplaySetInclude milestoneDisplaySetInclude = new QMilestoneDisplaySetInclude("milestoneDisplaySetInclude");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final BooleanPath adjustable = createBoolean("adjustable");

    public final StringPath description = createString("description");

    public final BooleanPath disallowFuture = createBoolean("disallowFuture");

    public final BooleanPath hasTime = createBoolean("hasTime");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath inventoryFlag = createBoolean("inventoryFlag");

    public final QMilestone milestone;

    public final QMilestoneDisplaySet milestoneDisplaySet;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> progressPercentage = createNumber("progressPercentage", Long.class);

    public final BooleanPath required = createBoolean("required");

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final StringPath status = createString("status");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final BooleanPath workflowDriven = createBoolean("workflowDriven");

    public QMilestoneDisplaySetInclude(String variable) {
        this(MilestoneDisplaySetInclude.class, forVariable(variable), INITS);
    }

    public QMilestoneDisplaySetInclude(Path<? extends MilestoneDisplaySetInclude> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMilestoneDisplaySetInclude(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMilestoneDisplaySetInclude(PathMetadata metadata, PathInits inits) {
        this(MilestoneDisplaySetInclude.class, metadata, inits);
    }

    public QMilestoneDisplaySetInclude(Class<? extends MilestoneDisplaySetInclude> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.milestone = inits.isInitialized("milestone") ? new QMilestone(forProperty("milestone")) : null;
        this.milestoneDisplaySet = inits.isInitialized("milestoneDisplaySet") ? new QMilestoneDisplaySet(forProperty("milestoneDisplaySet")) : null;
    }

}

