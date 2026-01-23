package com.vertek.corporate.qto.activation.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivationScheduleEquipment is a Querydsl query type for ActivationScheduleEquipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationScheduleEquipment extends EntityPathBase<ActivationScheduleEquipment> {

    private static final long serialVersionUID = 388534541L;

    public static final QActivationScheduleEquipment activationScheduleEquipment = new QActivationScheduleEquipment("activationScheduleEquipment");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> activationScheduleId = createNumber("activationScheduleId", Long.class);

    public final StringPath equipmentType = createString("equipmentType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemNumber = createString("itemNumber");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> quantity = createNumber("quantity", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QActivationScheduleEquipment(String variable) {
        super(ActivationScheduleEquipment.class, forVariable(variable));
    }

    public QActivationScheduleEquipment(Path<? extends ActivationScheduleEquipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationScheduleEquipment(PathMetadata metadata) {
        super(ActivationScheduleEquipment.class, metadata);
    }

}

