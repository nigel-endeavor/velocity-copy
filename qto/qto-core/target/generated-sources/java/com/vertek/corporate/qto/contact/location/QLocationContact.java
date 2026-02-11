package com.vertek.corporate.qto.contact.location;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationContact is a Querydsl query type for LocationContact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationContact extends EntityPathBase<LocationContact> {

    private static final long serialVersionUID = -1446964267L;

    public static final QLocationContact locationContact = new QLocationContact("locationContact");

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

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final StringPath notes = _super.notes;

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

    public QLocationContact(String variable) {
        super(LocationContact.class, forVariable(variable));
    }

    public QLocationContact(Path<? extends LocationContact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationContact(PathMetadata metadata) {
        super(LocationContact.class, metadata);
    }

}

