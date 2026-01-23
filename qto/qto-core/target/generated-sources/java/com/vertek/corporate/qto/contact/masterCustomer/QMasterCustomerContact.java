package com.vertek.corporate.qto.contact.masterCustomer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMasterCustomerContact is a Querydsl query type for MasterCustomerContact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMasterCustomerContact extends EntityPathBase<MasterCustomerContact> {

    private static final long serialVersionUID = -1772083851L;

    public static final QMasterCustomerContact masterCustomerContact = new QMasterCustomerContact("masterCustomerContact");

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

    public final NumberPath<Long> masterCustomerContactId = createNumber("masterCustomerContactId", Long.class);

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

    public QMasterCustomerContact(String variable) {
        super(MasterCustomerContact.class, forVariable(variable));
    }

    public QMasterCustomerContact(Path<? extends MasterCustomerContact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMasterCustomerContact(PathMetadata metadata) {
        super(MasterCustomerContact.class, metadata);
    }

}

