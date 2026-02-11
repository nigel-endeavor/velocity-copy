package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiShipment is a Querydsl query type for FtdiShipment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiShipment extends EntityPathBase<FtdiShipment> {

    private static final long serialVersionUID = 1761012243L;

    public static final QFtdiShipment ftdiShipment = new QFtdiShipment("ftdiShipment");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final StringPath courier = createString("courier");

    public final BooleanPath delivered = createBoolean("delivered");

    public final DateTimePath<java.util.Date> deliveryDate = createDateTime("deliveryDate", java.util.Date.class);

    public final NumberPath<Long> ftdiDispatchId = createNumber("ftdiDispatchId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final BooleanPath returnShipment = createBoolean("returnShipment");

    public final DateTimePath<java.util.Date> shipmentDate = createDateTime("shipmentDate", java.util.Date.class);

    public final StringPath shipmentStatus = createString("shipmentStatus");

    public final StringPath signedBy = createString("signedBy");

    public final StringPath trackingNumber = createString("trackingNumber");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiShipment(String variable) {
        super(FtdiShipment.class, forVariable(variable));
    }

    public QFtdiShipment(Path<? extends FtdiShipment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiShipment(PathMetadata metadata) {
        super(FtdiShipment.class, metadata);
    }

}

