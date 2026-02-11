package com.vertek.corporate.qto.equipment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEquipment is a Querydsl query type for Equipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEquipment extends EntityPathBase<Equipment> {

    private static final long serialVersionUID = 1083527033L;

    public static final QEquipment equipment = new QEquipment("equipment");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath decommission = createBoolean("decommission");

    public final DateTimePath<java.util.Date> decommissionedDate = createDateTime("decommissionedDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> deliveredDate = createDateTime("deliveredDate", java.util.Date.class);

    public final StringPath description = createString("description");

    public final StringPath dns1 = createString("dns1");

    public final StringPath dns2 = createString("dns2");

    public final StringPath equipmentSubtype = createString("equipmentSubtype");

    public final StringPath equipmentType = createString("equipmentType");

    public final StringPath gateway = createString("gateway");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final StringPath macAddress = createString("macAddress");

    public final StringPath make = createString("make");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath model = createString("model");

    public final StringPath networkIpRange = createString("networkIpRange");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath ownership = createString("ownership");

    public final StringPath serialNumber = createString("serialNumber");

    public final DateTimePath<java.util.Date> shipDate = createDateTime("shipDate", java.util.Date.class);

    public final StringPath shippingMethod = createString("shippingMethod");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath trackingInfo = createString("trackingInfo");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QEquipment(String variable) {
        super(Equipment.class, forVariable(variable));
    }

    public QEquipment(Path<? extends Equipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEquipment(PathMetadata metadata) {
        super(Equipment.class, metadata);
    }

}

