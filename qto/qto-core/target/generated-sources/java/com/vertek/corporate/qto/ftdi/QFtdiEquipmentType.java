package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiEquipmentType is a Querydsl query type for FtdiEquipmentType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiEquipmentType extends EntityPathBase<FtdiEquipmentType> {

    private static final long serialVersionUID = -770810609L;

    public static final QFtdiEquipmentType ftdiEquipmentType = new QFtdiEquipmentType("ftdiEquipmentType");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath clientPartNumber = createString("clientPartNumber");

    public final StringPath equipmentType = createString("equipmentType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemNumber = createString("itemNumber");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath partNumber = createString("partNumber");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiEquipmentType(String variable) {
        super(FtdiEquipmentType.class, forVariable(variable));
    }

    public QFtdiEquipmentType(Path<? extends FtdiEquipmentType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiEquipmentType(PathMetadata metadata) {
        super(FtdiEquipmentType.class, metadata);
    }

}

