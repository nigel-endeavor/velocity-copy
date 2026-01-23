package com.vertek.corporate.qto.invoicing.levelOfEffort;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLevelOfEffort is a Querydsl query type for LevelOfEffort
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLevelOfEffort extends EntityPathBase<LevelOfEffort> {

    private static final long serialVersionUID = 1083192103L;

    public static final QLevelOfEffort levelOfEffort1 = new QLevelOfEffort("levelOfEffort1");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath levelOfEffort = createString("levelOfEffort");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> sortOrder = createNumber("sortOrder", Long.class);

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLevelOfEffort(String variable) {
        super(LevelOfEffort.class, forVariable(variable));
    }

    public QLevelOfEffort(Path<? extends LevelOfEffort> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLevelOfEffort(PathMetadata metadata) {
        super(LevelOfEffort.class, metadata);
    }

}

