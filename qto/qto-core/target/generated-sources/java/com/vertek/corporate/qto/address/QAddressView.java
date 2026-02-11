package com.vertek.corporate.qto.address;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressView is a Querydsl query type for AddressView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddressView extends EntityPathBase<AddressView> {

    private static final long serialVersionUID = 734529546L;

    public static final QAddressView addressView = new QAddressView("addressView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath country = createString("country");

    public final StringPath id = createString("id");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath postalCode = createString("postalCode");

    public final StringPath state = createString("state");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QAddressView(String variable) {
        super(AddressView.class, forVariable(variable));
    }

    public QAddressView(Path<? extends AddressView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressView(PathMetadata metadata) {
        super(AddressView.class, metadata);
    }

}

