package com.vertek.corporate.qto.jeop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJeop is a Querydsl query type for Jeop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJeop extends EntityPathBase<Jeop> {

    private static final long serialVersionUID = -811750663L;

    public static final QJeop jeop = new QJeop("jeop");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath assignedTo = createString("assignedTo");

    public final NumberPath<Integer> businessDaysOpen = createNumber("businessDaysOpen", Integer.class);

    public final NumberPath<Integer> calendarDaysOpen = createNumber("calendarDaysOpen", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    public final StringPath level = createString("level");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final StringPath originator = createString("originator");

    public final StringPath responsibility = createString("responsibility");

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QJeop(String variable) {
        super(Jeop.class, forVariable(variable));
    }

    public QJeop(Path<? extends Jeop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJeop(PathMetadata metadata) {
        super(Jeop.class, metadata);
    }

}

