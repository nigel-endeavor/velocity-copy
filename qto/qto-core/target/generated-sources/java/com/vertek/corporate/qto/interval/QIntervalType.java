package com.vertek.corporate.qto.interval;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIntervalType is a Querydsl query type for IntervalType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntervalType extends EntityPathBase<IntervalType> {

    private static final long serialVersionUID = 1235385331L;

    public static final QIntervalType intervalType = new QIntervalType("intervalType");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> closeMilestoneId = createNumber("closeMilestoneId", Long.class);

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> openMilestoneId = createNumber("openMilestoneId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QIntervalType(String variable) {
        super(IntervalType.class, forVariable(variable));
    }

    public QIntervalType(Path<? extends IntervalType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIntervalType(PathMetadata metadata) {
        super(IntervalType.class, metadata);
    }

}

