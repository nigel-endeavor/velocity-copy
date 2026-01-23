package com.vertek.corporate.qto.contact;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QContact is a Querydsl query type for Contact
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContact extends EntityPathBase<Contact> {

    private static final long serialVersionUID = 383445021L;

    public static final QContact contact = new QContact("contact");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath email = createString("email");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath lastUpdateBy = createString("lastUpdateBy");

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath notes = createString("notes");

    public final NumberPath<Long> parentContactId = createNumber("parentContactId", Long.class);

    public final StringPath phone = createString("phone");

    public final StringPath role = createString("role");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final EnumPath<ContactType> type = createEnum("type", ContactType.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QContact(String variable) {
        super(Contact.class, forVariable(variable));
    }

    public QContact(Path<? extends Contact> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContact(PathMetadata metadata) {
        super(Contact.class, metadata);
    }

}

