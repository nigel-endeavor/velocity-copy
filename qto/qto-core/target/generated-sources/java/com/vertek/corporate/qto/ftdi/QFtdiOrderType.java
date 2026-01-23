package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFtdiOrderType is a Querydsl query type for FtdiOrderType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiOrderType extends EntityPathBase<FtdiOrderType> {

    private static final long serialVersionUID = 1486539343L;

    public static final QFtdiOrderType ftdiOrderType = new QFtdiOrderType("ftdiOrderType");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath category = createString("category");

    public final ListPath<FtdiCustomField, QFtdiCustomField> customFields = this.<FtdiCustomField, QFtdiCustomField>createList("customFields", FtdiCustomField.class, QFtdiCustomField.class, PathInits.DIRECT2);

    public final ListPath<FtdiEquipmentType, QFtdiEquipmentType> equipmentTypes = this.<FtdiEquipmentType, QFtdiEquipmentType>createList("equipmentTypes", FtdiEquipmentType.class, QFtdiEquipmentType.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath orderType = createString("orderType");

    public final StringPath orgId = createString("orgId");

    public final StringPath sort1 = createString("sort1");

    public final StringPath sort2 = createString("sort2");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath vendorName = createString("vendorName");

    public final StringPath vendorOrderTypeId = createString("vendorOrderTypeId");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiOrderType(String variable) {
        super(FtdiOrderType.class, forVariable(variable));
    }

    public QFtdiOrderType(Path<? extends FtdiOrderType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiOrderType(PathMetadata metadata) {
        super(FtdiOrderType.class, metadata);
    }

}

