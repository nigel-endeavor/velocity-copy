package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMilestoneInstanceHistory is a Querydsl query type for MilestoneInstanceHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMilestoneInstanceHistory extends EntityPathBase<MilestoneInstanceHistory> {

    private static final long serialVersionUID = -602552382L;

    public static final QMilestoneInstanceHistory milestoneInstanceHistory = new QMilestoneInstanceHistory("milestoneInstanceHistory");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> milestoneInstanceId = createNumber("milestoneInstanceId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> newDate = createDateTime("newDate", java.util.Date.class);

    public final StringPath note = createString("note");

    public final DateTimePath<java.util.Date> oldDate = createDateTime("oldDate", java.util.Date.class);

    public final StringPath updateBy = createString("updateBy");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMilestoneInstanceHistory(String variable) {
        super(MilestoneInstanceHistory.class, forVariable(variable));
    }

    public QMilestoneInstanceHistory(Path<? extends MilestoneInstanceHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMilestoneInstanceHistory(PathMetadata metadata) {
        super(MilestoneInstanceHistory.class, metadata);
    }

}

