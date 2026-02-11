package com.vertek.corporate.qto.invoicing.surchargeType;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSurchargeType is a Querydsl query type for SurchargeType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSurchargeType extends EntityPathBase<SurchargeType> {

    private static final long serialVersionUID = -2086577899L;

    public static final QSurchargeType surchargeType = new QSurchargeType("surchargeType");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath level = createString("level");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QSurchargeType(String variable) {
        super(SurchargeType.class, forVariable(variable));
    }

    public QSurchargeType(Path<? extends SurchargeType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSurchargeType(PathMetadata metadata) {
        super(SurchargeType.class, metadata);
    }

}

