package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMilestoneDisplaySet is a Querydsl query type for MilestoneDisplaySet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMilestoneDisplaySet extends EntityPathBase<MilestoneDisplaySet> {

    private static final long serialVersionUID = -1894648611L;

    public static final QMilestoneDisplaySet milestoneDisplaySet = new QMilestoneDisplaySet("milestoneDisplaySet");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final StringPath displayGroup = createString("displayGroup");

    public final ListPath<MilestoneDisplaySetInclude, QMilestoneDisplaySetInclude> displaySetIncludes = this.<MilestoneDisplaySetInclude, QMilestoneDisplaySetInclude>createList("displaySetIncludes", MilestoneDisplaySetInclude.class, QMilestoneDisplaySetInclude.class, PathInits.DIRECT2);

    public final StringPath displaySetLabel = createString("displaySetLabel");

    public final StringPath displayType = createString("displayType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMilestoneDisplaySet(String variable) {
        super(MilestoneDisplaySet.class, forVariable(variable));
    }

    public QMilestoneDisplaySet(Path<? extends MilestoneDisplaySet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMilestoneDisplaySet(PathMetadata metadata) {
        super(MilestoneDisplaySet.class, metadata);
    }

}

