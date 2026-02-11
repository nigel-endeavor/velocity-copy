package com.vertek.corporate.qto.contact.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderContact is a Querydsl query type for OrderContact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderContact extends EntityPathBase<OrderContact> {

    private static final long serialVersionUID = 454088565L;

    public static final QOrderContact orderContact = new QOrderContact("orderContact");

    public final com.vertek.corporate.qto.contact.QContact _super = new com.vertek.corporate.qto.contact.QContact(this);

    //inherited
    public final BooleanPath active = _super.active;

    //inherited
    public final NumberPath<Long> companyId = _super.companyId;

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final StringPath firstName = _super.firstName;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath lastName = _super.lastName;

    //inherited
    public final StringPath lastUpdateBy = _super.lastUpdateBy;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate = _super.lastUpdateDate;

    //inherited
    public final NumberPath<Long> legacyId = _super.legacyId;

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final StringPath notes = _super.notes;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    //inherited
    public final NumberPath<Long> parentContactId = _super.parentContactId;

    //inherited
    public final StringPath phone = _super.phone;

    //inherited
    public final StringPath role = _super.role;

    //inherited
    public final NumberPath<Integer> sortOrder = _super.sortOrder;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final EnumPath<com.vertek.corporate.qto.contact.ContactType> type = _super.type;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QOrderContact(String variable) {
        super(OrderContact.class, forVariable(variable));
    }

    public QOrderContact(Path<? extends OrderContact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderContact(PathMetadata metadata) {
        super(OrderContact.class, metadata);
    }

}

