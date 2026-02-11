package com.vertek.corporate.qto.interval;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIntervalInstance is a Querydsl query type for IntervalInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntervalInstance extends EntityPathBase<IntervalInstance> {

    private static final long serialVersionUID = -992248626L;

    public static final QIntervalInstance intervalInstance = new QIntervalInstance("intervalInstance");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> businessDayIntervalTime = createNumber("businessDayIntervalTime", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> calendarDayIntervalTime = createNumber("calendarDayIntervalTime", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> clientBusinessDayDeductTime = createNumber("clientBusinessDayDeductTime", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> clientCalendarDayDeductTime = createNumber("clientCalendarDayDeductTime", java.math.BigDecimal.class);

    public final NumberPath<Long> closeMilestoneInstanceId = createNumber("closeMilestoneInstanceId", Long.class);

    public final NumberPath<java.math.BigDecimal> customerBusinessDayDeductTime = createNumber("customerBusinessDayDeductTime", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> customerCalendarDayDeductTime = createNumber("customerCalendarDayDeductTime", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> intervalTypeId = createNumber("intervalTypeId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> openMilestoneInstanceId = createNumber("openMilestoneInstanceId", Long.class);

    public final NumberPath<java.math.BigDecimal> providerBusinessDayDeductTime = createNumber("providerBusinessDayDeductTime", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> providerCalendarDayDeductTime = createNumber("providerCalendarDayDeductTime", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QIntervalInstance(String variable) {
        super(IntervalInstance.class, forVariable(variable));
    }

    public QIntervalInstance(Path<? extends IntervalInstance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIntervalInstance(PathMetadata metadata) {
        super(IntervalInstance.class, metadata);
    }

}

