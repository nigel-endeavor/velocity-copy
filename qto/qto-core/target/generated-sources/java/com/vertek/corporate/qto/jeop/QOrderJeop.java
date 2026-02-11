package com.vertek.corporate.qto.jeop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderJeop is a Querydsl query type for OrderJeop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderJeop extends EntityPathBase<OrderJeop> {

    private static final long serialVersionUID = 80249933L;

    public static final QOrderJeop orderJeop = new QOrderJeop("orderJeop");

    public final QJeop _super = new QJeop(this);

    //inherited
    public final StringPath assignedTo = _super.assignedTo;

    //inherited
    public final NumberPath<Integer> businessDaysOpen = _super.businessDaysOpen;

    //inherited
    public final NumberPath<Integer> calendarDaysOpen = _super.calendarDaysOpen;

    //inherited
    public final StringPath description = _super.description;

    //inherited
    public final DateTimePath<java.util.Date> endDate = _super.endDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final NumberPath<Long> legacyId = _super.legacyId;

    //inherited
    public final StringPath level = _super.level;

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final StringPath note = _super.note;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    //inherited
    public final StringPath originator = _super.originator;

    //inherited
    public final StringPath responsibility = _super.responsibility;

    //inherited
    public final DateTimePath<java.util.Date> startDate = _super.startDate;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QOrderJeop(String variable) {
        super(OrderJeop.class, forVariable(variable));
    }

    public QOrderJeop(Path<? extends OrderJeop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderJeop(PathMetadata metadata) {
        super(OrderJeop.class, metadata);
    }

}

