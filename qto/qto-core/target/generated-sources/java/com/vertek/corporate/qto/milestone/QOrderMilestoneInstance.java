package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderMilestoneInstance is a Querydsl query type for OrderMilestoneInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderMilestoneInstance extends EntityPathBase<OrderMilestoneInstance> {

    private static final long serialVersionUID = 2037971530L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderMilestoneInstance orderMilestoneInstance = new QOrderMilestoneInstance("orderMilestoneInstance");

    public final QMilestoneInstance _super;

    //inherited
    public final NumberPath<Integer> count;

    //inherited
    public final BooleanPath historic;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Long> invoiceId;

    //inherited
    public final NumberPath<Long> legacyId;

    //inherited
    public final NumberPath<Long> masterCustomerId;

    // inherited
    public final QMilestone milestone;

    //inherited
    public final DateTimePath<java.util.Date> milestoneDate;

    //inherited
    public final BooleanPath new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    //inherited
    public final StringPath param;

    //inherited
    public final NumberPath<Long> parentMilestoneInstanceId;

    //inherited
    public final NumberPath<Long> tenantId;

    //inherited
    public final NumberPath<Integer> version;

    public QOrderMilestoneInstance(String variable) {
        this(OrderMilestoneInstance.class, forVariable(variable), INITS);
    }

    public QOrderMilestoneInstance(Path<? extends OrderMilestoneInstance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderMilestoneInstance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderMilestoneInstance(PathMetadata metadata, PathInits inits) {
        this(OrderMilestoneInstance.class, metadata, inits);
    }

    public QOrderMilestoneInstance(Class<? extends OrderMilestoneInstance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QMilestoneInstance(type, metadata, inits);
        this.count = _super.count;
        this.historic = _super.historic;
        this.id = _super.id;
        this.invoiceId = _super.invoiceId;
        this.legacyId = _super.legacyId;
        this.masterCustomerId = _super.masterCustomerId;
        this.milestone = _super.milestone;
        this.milestoneDate = _super.milestoneDate;
        this.new$ = _super.new$;
        this.param = _super.param;
        this.parentMilestoneInstanceId = _super.parentMilestoneInstanceId;
        this.tenantId = _super.tenantId;
        this.version = _super.version;
    }

}

