package com.vertek.corporate.qto.location;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocation is a Querydsl query type for Location
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocation extends EntityPathBase<Location> {

    private static final long serialVersionUID = -937166055L;

    public static final QLocation location = new QLocation("location");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath buildingType = createString("buildingType");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath country = createString("country");

    public final DateTimePath<java.util.Date> deletionDate = createDateTime("deletionDate", java.util.Date.class);

    public final StringPath description = createString("description");

    public final BooleanPath eligibleForInventory = createBoolean("eligibleForInventory");

    public final BooleanPath finalUpdate = createBoolean("finalUpdate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inventoryLocationId = createNumber("inventoryLocationId", Long.class);

    public final ListPath<com.vertek.corporate.qto.service.Service, com.vertek.corporate.qto.service.QService> inventoryServices = this.<com.vertek.corporate.qto.service.Service, com.vertek.corporate.qto.service.QService>createList("inventoryServices", com.vertek.corporate.qto.service.Service.class, com.vertek.corporate.qto.service.QService.class, PathInits.DIRECT2);

    public final BooleanPath isCurrentInventory = createBoolean("isCurrentInventory");

    public final StringPath lastUpdateBy = createString("lastUpdateBy");

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final StringPath legacyId = createString("legacyId");

    public final NumberPath<java.math.BigDecimal> legacyTransactionAmount = createNumber("legacyTransactionAmount", java.math.BigDecimal.class);

    public final StringPath legacyTransactionType = createString("legacyTransactionType");

    public final StringPath levelOfEffort = createString("levelOfEffort");

    public final BooleanPath markedForDeletion = createBoolean("markedForDeletion");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> parentLocationId = createNumber("parentLocationId", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Integer> progressPercentage = createNumber("progressPercentage", Integer.class);

    public final NumberPath<Long> provisioningLocationId = createNumber("provisioningLocationId", Long.class);

    public final StringPath quoteLocationId = createString("quoteLocationId");

    public final StringPath recordSource = createString("recordSource");

    public final NumberPath<Long> requirementTemplateId = createNumber("requirementTemplateId", Long.class);

    public final ListPath<com.vertek.corporate.qto.service.Service, com.vertek.corporate.qto.service.QService> services = this.<com.vertek.corporate.qto.service.Service, com.vertek.corporate.qto.service.QService>createList("services", com.vertek.corporate.qto.service.Service.class, com.vertek.corporate.qto.service.QService.class, PathInits.DIRECT2);

    public final NumberPath<Long> sortOrder = createNumber("sortOrder", Long.class);

    public final StringPath state = createString("state");

    public final StringPath status = createString("status");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath timezone = createString("timezone");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLocation(String variable) {
        super(Location.class, forVariable(variable));
    }

    public QLocation(Path<? extends Location> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocation(PathMetadata metadata) {
        super(Location.class, metadata);
    }

}

