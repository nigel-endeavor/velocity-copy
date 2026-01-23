package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMilestone is a Querydsl query type for Milestone
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMilestone extends EntityPathBase<Milestone> {

    private static final long serialVersionUID = 2144322749L;

    public static final QMilestone milestone = new QMilestone("milestone");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMilestone(String variable) {
        super(Milestone.class, forVariable(variable));
    }

    public QMilestone(Path<? extends Milestone> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMilestone(PathMetadata metadata) {
        super(Milestone.class, metadata);
    }

}

