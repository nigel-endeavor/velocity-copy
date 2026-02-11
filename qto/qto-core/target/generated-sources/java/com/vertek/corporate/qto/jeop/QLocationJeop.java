package com.vertek.corporate.qto.jeop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationJeop is a Querydsl query type for LocationJeop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationJeop extends EntityPathBase<LocationJeop> {

    private static final long serialVersionUID = -808194226L;

    public static final QLocationJeop locationJeop = new QLocationJeop("locationJeop");

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

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

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

    //inherited
    public final DateTimePath<java.util.Date> startDate = _super.startDate;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLocationJeop(String variable) {
        super(LocationJeop.class, forVariable(variable));
    }

    public QLocationJeop(Path<? extends LocationJeop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationJeop(PathMetadata metadata) {
        super(LocationJeop.class, metadata);
    }

}

