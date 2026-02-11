package com.vertek.corporate.qto.equipment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceEquipment is a Querydsl query type for ServiceEquipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceEquipment extends EntityPathBase<ServiceEquipment> {

    private static final long serialVersionUID = -198139378L;

    public static final QServiceEquipment serviceEquipment = new QServiceEquipment("serviceEquipment");

    public final QEquipment _super = new QEquipment(this);

    //inherited
    public final BooleanPath decommission = _super.decommission;

    //inherited
    public final DateTimePath<java.util.Date> decommissionedDate = _super.decommissionedDate;

    //inherited
    public final DateTimePath<java.util.Date> deliveredDate = _super.deliveredDate;

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final StringPath dns1 = _super.dns1;

    //inherited
    public final StringPath dns2 = _super.dns2;

    //inherited
    public final StringPath equipmentSubtype = _super.equipmentSubtype;

    //inherited
    public final StringPath equipmentType = _super.equipmentType;

    //inherited
    public final StringPath gateway = _super.gateway;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath ipAddress = _super.ipAddress;

    //inherited
    public final StringPath macAddress = _super.macAddress;

    //inherited
    public final StringPath make = _super.make;

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final StringPath model = _super.model;

    //inherited
    public final StringPath networkIpRange = _super.networkIpRange;

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final StringPath ownership = _super.ownership;

    //inherited
    public final StringPath serialNumber = _super.serialNumber;

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> shipDate = _super.shipDate;

    //inherited
    public final StringPath shippingMethod = _super.shippingMethod;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final StringPath trackingInfo = _super.trackingInfo;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceEquipment(String variable) {
        super(ServiceEquipment.class, forVariable(variable));
    }

    public QServiceEquipment(Path<? extends ServiceEquipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceEquipment(PathMetadata metadata) {
        super(ServiceEquipment.class, metadata);
    }

}

