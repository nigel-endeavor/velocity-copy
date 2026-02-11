package com.vertek.corporate.qto.activation.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivationScheduleCustom is a Querydsl query type for ActivationScheduleCustom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationScheduleCustom extends EntityPathBase<ActivationScheduleCustom> {

    private static final long serialVersionUID = 834054226L;

    public static final QActivationScheduleCustom activationScheduleCustom = new QActivationScheduleCustom("activationScheduleCustom");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> activationScheduleId = createNumber("activationScheduleId", Long.class);

    public final StringPath fieldName = createString("fieldName");

    public final StringPath fieldValue = createString("fieldValue");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QActivationScheduleCustom(String variable) {
        super(ActivationScheduleCustom.class, forVariable(variable));
    }

    public QActivationScheduleCustom(Path<? extends ActivationScheduleCustom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationScheduleCustom(PathMetadata metadata) {
        super(ActivationScheduleCustom.class, metadata);
    }

}

