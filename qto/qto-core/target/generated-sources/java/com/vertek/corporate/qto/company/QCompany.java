package com.vertek.corporate.qto.company;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 1845455831L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompany company = new QCompany("company");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<Long> accountManager = createNumber("accountManager", Long.class);

    public final StringPath accountNotes = createString("accountNotes");

    public final BooleanPath active = createBoolean("active");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final BooleanPath automatedEmailsEnabled = createBoolean("automatedEmailsEnabled");

    public final StringPath automateEmailAddresses = createString("automateEmailAddresses");

    public final ListPath<com.vertek.corporate.qto.contact.Contact, com.vertek.corporate.qto.contact.QContact> billingContacts = this.<com.vertek.corporate.qto.contact.Contact, com.vertek.corporate.qto.contact.QContact>createList("billingContacts", com.vertek.corporate.qto.contact.Contact.class, com.vertek.corporate.qto.contact.QContact.class, PathInits.DIRECT2);

    public final StringPath city = createString("city");

    public final StringPath clientId = createString("clientId");

    public final StringPath country = createString("country");

    public final BooleanPath duplicatedMasterCustomerDetails = createBoolean("duplicatedMasterCustomerDetails");

    public final NumberPath<Long> i90ProjectManager = createNumber("i90ProjectManager", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inventoryLocationCount = createNumber("inventoryLocationCount", Long.class);

    public final NumberPath<java.math.BigDecimal> inventoryMrc = createNumber("inventoryMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> inventoryMrr = createNumber("inventoryMrr", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> inventoryNrr = createNumber("inventoryNrr", java.math.BigDecimal.class);

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final QCompany parentCompany;

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Long> provisioner = createNumber("provisioner", Long.class);

    public final StringPath state = createString("state");

    public final NumberPath<Long> taskGroupId = createNumber("taskGroupId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    public final StringPath uuid = createString("uuid");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCompany(String variable) {
        this(Company.class, forVariable(variable), INITS);
    }

    public QCompany(Path<? extends Company> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompany(PathMetadata metadata, PathInits inits) {
        this(Company.class, metadata, inits);
    }

    public QCompany(Class<? extends Company> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parentCompany = inits.isInitialized("parentCompany") ? new QCompany(forProperty("parentCompany"), inits.get("parentCompany")) : null;
    }

}

