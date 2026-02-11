package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMilestoneSet is a Querydsl query type for MilestoneSet
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMilestoneSet extends EntityPathBase<MilestoneSet> {

    private static final long serialVersionUID = -1824462235L;

    public static final QMilestoneSet milestoneSet = new QMilestoneSet("milestoneSet");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final SetPath<MilestoneDisplaySet, QMilestoneDisplaySet> displaySets = this.<MilestoneDisplaySet, QMilestoneDisplaySet>createSet("displaySets", MilestoneDisplaySet.class, QMilestoneDisplaySet.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMilestoneSet(String variable) {
        super(MilestoneSet.class, forVariable(variable));
    }

    public QMilestoneSet(Path<? extends MilestoneSet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMilestoneSet(PathMetadata metadata) {
        super(MilestoneSet.class, metadata);
    }

}

