package com.vertek.corporate.qto.costhistory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCostHistory is a Querydsl query type for CostHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCostHistory extends EntityPathBase<CostHistory> {

    private static final long serialVersionUID = -1715766901L;

    public static final QCostHistory costHistory = new QCostHistory("costHistory");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath changeReason = createString("changeReason");

    public final StringPath costType = createString("costType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> newValue = createNumber("newValue", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> oldValue = createNumber("oldValue", java.math.BigDecimal.class);

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath typeProvider = createString("typeProvider");

    public final StringPath updateBy = createString("updateBy");

    public final DateTimePath<java.util.Date> updateDate = createDateTime("updateDate", java.util.Date.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCostHistory(String variable) {
        super(CostHistory.class, forVariable(variable));
    }

    public QCostHistory(Path<? extends CostHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCostHistory(PathMetadata metadata) {
        super(CostHistory.class, metadata);
    }

}

