package com.vertek.corporate.qto.inventory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInventoryLocationService is a Querydsl query type for InventoryLocationService
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryLocationService extends EntityPathBase<InventoryLocationService> {

    private static final long serialVersionUID = 1801193195L;

    public static final QInventoryLocationService inventoryLocationService = new QInventoryLocationService("inventoryLocationService");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inventoryLocationId = createNumber("inventoryLocationId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public QInventoryLocationService(String variable) {
        super(InventoryLocationService.class, forVariable(variable));
    }

    public QInventoryLocationService(Path<? extends InventoryLocationService> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInventoryLocationService(PathMetadata metadata) {
        super(InventoryLocationService.class, metadata);
    }

}

