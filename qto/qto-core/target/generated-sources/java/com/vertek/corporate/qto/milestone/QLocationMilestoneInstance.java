package com.vertek.corporate.qto.milestone;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationMilestoneInstance is a Querydsl query type for LocationMilestoneInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationMilestoneInstance extends EntityPathBase<LocationMilestoneInstance> {

    private static final long serialVersionUID = 701854781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationMilestoneInstance locationMilestoneInstance = new QLocationMilestoneInstance("locationMilestoneInstance");

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

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId;

    // inherited
    public final QMilestone milestone;

    //inherited
    public final DateTimePath<java.util.Date> milestoneDate;

    //inherited
    public final BooleanPath new$;

    //inherited
    public final StringPath param;

    //inherited
    public final NumberPath<Long> parentMilestoneInstanceId;

    //inherited
    public final NumberPath<Long> tenantId;

    //inherited
    public final NumberPath<Integer> version;

    public QLocationMilestoneInstance(String variable) {
        this(LocationMilestoneInstance.class, forVariable(variable), INITS);
    }

    public QLocationMilestoneInstance(Path<? extends LocationMilestoneInstance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationMilestoneInstance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationMilestoneInstance(PathMetadata metadata, PathInits inits) {
        this(LocationMilestoneInstance.class, metadata, inits);
    }

    public QLocationMilestoneInstance(Class<? extends LocationMilestoneInstance> type, PathMetadata metadata, PathInits inits) {
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

