package com.vertek.corporate.qto.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProviderIntervalsView is a Querydsl query type for ProviderIntervalsView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProviderIntervalsView extends EntityPathBase<ProviderIntervalsView> {

    private static final long serialVersionUID = 1886949949L;

    public static final QProviderIntervalsView providerIntervalsView = new QProviderIntervalsView("providerIntervalsView");

    public final NumberPath<Double> businessDayIntervalTime = createNumber("businessDayIntervalTime", Double.class);

    public final NumberPath<Double> calendarDayIntervalTime = createNumber("calendarDayIntervalTime", Double.class);

    public final NumberPath<Double> clientBusinessDayDeductTime = createNumber("clientBusinessDayDeductTime", Double.class);

    public final NumberPath<Double> clientCalendarDayDeductTime = createNumber("clientCalendarDayDeductTime", Double.class);

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath closeMilestoneCode = createString("closeMilestoneCode");

    public final StringPath companyName = createString("companyName");

    public final NumberPath<Double> customerCalendarDayDeductTime = createNumber("customerCalendarDayDeductTime", Double.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> intervalInstanceId = createNumber("intervalInstanceId", Long.class);

    public final StringPath intervalTypeCode = createString("intervalTypeCode");

    public final StringPath intervalTypeDesc = createString("intervalTypeDesc");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath locationName = createString("locationName");

    public final StringPath masterCompanyName = createString("masterCompanyName");

    public final NumberPath<Long> masterCustomerId = createNumber("masterCustomerId", Long.class);

    public final StringPath openMilestoneCode = createString("openMilestoneCode");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath provider = createString("provider");

    public final NumberPath<Double> providerBusinessDayDeductTime = createNumber("providerBusinessDayDeductTime", Double.class);

    public final NumberPath<Double> providerCalendarDayDeductTime = createNumber("providerCalendarDayDeductTime", Double.class);

    public final BooleanPath serviceActive = createBoolean("serviceActive");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath serviceType = createString("serviceType");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public QProviderIntervalsView(String variable) {
        super(ProviderIntervalsView.class, forVariable(variable));
    }

    public QProviderIntervalsView(Path<? extends ProviderIntervalsView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProviderIntervalsView(PathMetadata metadata) {
        super(ProviderIntervalsView.class, metadata);
    }

}

