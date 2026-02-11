package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiOrderTypeEquipment is a Querydsl query type for FtdiOrderTypeEquipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiOrderTypeEquipment extends EntityPathBase<FtdiOrderTypeEquipment> {

    private static final long serialVersionUID = -1037278209L;

    public static final QFtdiOrderTypeEquipment ftdiOrderTypeEquipment = new QFtdiOrderTypeEquipment("ftdiOrderTypeEquipment");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Long> equipmentTypeId = createNumber("equipmentTypeId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderTypeId = createNumber("orderTypeId", Long.class);

    public QFtdiOrderTypeEquipment(String variable) {
        super(FtdiOrderTypeEquipment.class, forVariable(variable));
    }

    public QFtdiOrderTypeEquipment(Path<? extends FtdiOrderTypeEquipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiOrderTypeEquipment(PathMetadata metadata) {
        super(FtdiOrderTypeEquipment.class, metadata);
    }

}

