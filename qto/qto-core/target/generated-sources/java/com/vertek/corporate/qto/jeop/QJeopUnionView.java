package com.vertek.corporate.qto.jeop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QJeopUnionView is a Querydsl query type for JeopUnionView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QJeopUnionView extends EntityPathBase<JeopUnionView> {

    private static final long serialVersionUID = 622445339L;

    public static final QJeopUnionView jeopUnionView = new QJeopUnionView("jeopUnionView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath assignedTo = createString("assignedTo");

    public final NumberPath<Integer> businessDaysOpen = createNumber("businessDaysOpen", Integer.class);

    public final NumberPath<Integer> calendarDaysOpen = createNumber("calendarDaysOpen", Integer.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath level = createString("level");

    public final StringPath levelJeop = createString("levelJeop");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath originator = createString("originator");

    public final StringPath responsibility = createString("responsibility");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QJeopUnionView(String variable) {
        super(JeopUnionView.class, forVariable(variable));
    }

    public QJeopUnionView(Path<? extends JeopUnionView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QJeopUnionView(PathMetadata metadata) {
        super(JeopUnionView.class, metadata);
    }

}

