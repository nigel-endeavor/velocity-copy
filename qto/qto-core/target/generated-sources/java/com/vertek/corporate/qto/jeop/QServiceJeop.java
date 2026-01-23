package com.vertek.corporate.qto.jeop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceJeop is a Querydsl query type for ServiceJeop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceJeop extends EntityPathBase<ServiceJeop> {

    private static final long serialVersionUID = 818078580L;

    public static final QServiceJeop serviceJeop = new QServiceJeop("serviceJeop");

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

    //inherited
    public final StringPath originator = _super.originator;

    //inherited
    public final StringPath responsibility = _super.responsibility;

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> startDate = _super.startDate;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceJeop(String variable) {
        super(ServiceJeop.class, forVariable(variable));
    }

    public QServiceJeop(Path<? extends ServiceJeop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceJeop(PathMetadata metadata) {
        super(ServiceJeop.class, metadata);
    }

}

