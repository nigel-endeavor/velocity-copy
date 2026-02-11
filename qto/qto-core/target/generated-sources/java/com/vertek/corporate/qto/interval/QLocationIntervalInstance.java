package com.vertek.corporate.qto.interval;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationIntervalInstance is a Querydsl query type for LocationIntervalInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationIntervalInstance extends EntityPathBase<LocationIntervalInstance> {

    private static final long serialVersionUID = 1726726819L;

    public static final QLocationIntervalInstance locationIntervalInstance = new QLocationIntervalInstance("locationIntervalInstance");

    public final QIntervalInstance _super = new QIntervalInstance(this);

    //inherited
    public final NumberPath<java.math.BigDecimal> businessDayIntervalTime = _super.businessDayIntervalTime;

    //inherited
    public final NumberPath<java.math.BigDecimal> calendarDayIntervalTime = _super.calendarDayIntervalTime;

    //inherited
    public final NumberPath<java.math.BigDecimal> clientBusinessDayDeductTime = _super.clientBusinessDayDeductTime;

    //inherited
    public final NumberPath<java.math.BigDecimal> clientCalendarDayDeductTime = _super.clientCalendarDayDeductTime;

    //inherited
    public final NumberPath<Long> closeMilestoneInstanceId = _super.closeMilestoneInstanceId;

    //inherited
    public final NumberPath<java.math.BigDecimal> customerBusinessDayDeductTime = _super.customerBusinessDayDeductTime;

    //inherited
    public final NumberPath<java.math.BigDecimal> customerCalendarDayDeductTime = _super.customerCalendarDayDeductTime;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final NumberPath<Long> intervalTypeId = _super.intervalTypeId;

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> openMilestoneInstanceId = _super.openMilestoneInstanceId;

    //inherited
    public final NumberPath<java.math.BigDecimal> providerBusinessDayDeductTime = _super.providerBusinessDayDeductTime;

    //inherited
    public final NumberPath<java.math.BigDecimal> providerCalendarDayDeductTime = _super.providerCalendarDayDeductTime;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLocationIntervalInstance(String variable) {
        super(LocationIntervalInstance.class, forVariable(variable));
    }

    public QLocationIntervalInstance(Path<? extends LocationIntervalInstance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationIntervalInstance(PathMetadata metadata) {
        super(LocationIntervalInstance.class, metadata);
    }

}

