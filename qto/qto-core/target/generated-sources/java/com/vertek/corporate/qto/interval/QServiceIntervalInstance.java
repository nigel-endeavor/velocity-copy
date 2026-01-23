package com.vertek.corporate.qto.interval;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceIntervalInstance is a Querydsl query type for ServiceIntervalInstance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceIntervalInstance extends EntityPathBase<ServiceIntervalInstance> {

    private static final long serialVersionUID = 826756731L;

    public static final QServiceIntervalInstance serviceIntervalInstance = new QServiceIntervalInstance("serviceIntervalInstance");

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

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> openMilestoneInstanceId = _super.openMilestoneInstanceId;

    //inherited
    public final NumberPath<java.math.BigDecimal> providerBusinessDayDeductTime = _super.providerBusinessDayDeductTime;

    //inherited
    public final NumberPath<java.math.BigDecimal> providerCalendarDayDeductTime = _super.providerCalendarDayDeductTime;

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceIntervalInstance(String variable) {
        super(ServiceIntervalInstance.class, forVariable(variable));
    }

    public QServiceIntervalInstance(Path<? extends ServiceIntervalInstance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceIntervalInstance(PathMetadata metadata) {
        super(ServiceIntervalInstance.class, metadata);
    }

}

