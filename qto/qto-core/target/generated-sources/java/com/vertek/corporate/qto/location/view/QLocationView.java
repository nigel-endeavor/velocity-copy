package com.vertek.corporate.qto.location.view;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationView is a Querydsl query type for LocationView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationView extends EntityPathBase<LocationView> {

    private static final long serialVersionUID = -1149904385L;

    public static final QLocationView locationView = new QLocationView("locationView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath address = createString("address");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final NumberPath<java.math.BigDecimal> annualRecurringCost = createNumber("annualRecurringCost", java.math.BigDecimal.class);

    public final StringPath city = createString("city");

    public final StringPath clientLocationId = createString("clientLocationId");

    public final StringPath clientLocationInfo = createString("clientLocationInfo");

    public final StringPath clientLocationType = createString("clientLocationType");

    public final StringPath clientOrderId = createString("clientOrderId");

    public final StringPath clientProjectManager = createString("clientProjectManager");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath companyName = createString("companyName");

    public final DateTimePath<java.util.Date> completionDate = createDateTime("completionDate", java.util.Date.class);

    public final NumberPath<Long> countServices = createNumber("countServices", Long.class);

    public final StringPath endCustomerClientId = createString("endCustomerClientId");

    public final DateTimePath<java.util.Date> greatestMilestoneDate = createDateTime("greatestMilestoneDate", java.util.Date.class);

    public final StringPath greatestMilestoneName = createString("greatestMilestoneName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath latestNote = createString("latestNote");

    public final StringPath locationName = createString("locationName");

    public final StringPath locationStatus = createString("locationStatus");

    public final NumberPath<Long> macCount = createNumber("macCount", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrr = createNumber("mrr", java.math.BigDecimal.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrr = createNumber("nrr", java.math.BigDecimal.class);

    public final StringPath openJeop = createString("openJeop");

    public final StringPath openJeopResponsibilities = createString("openJeopResponsibilities");

    public final StringPath openJeops = createString("openJeops");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath parentCompanyClientId = createString("parentCompanyClientId");

    public final StringPath parentCompanyName = createString("parentCompanyName");

    public final StringPath postalCode = createString("postalCode");

    public final NumberPath<Long> progressPercentage = createNumber("progressPercentage", Long.class);

    public final StringPath provisioner = createString("provisioner");

    public final StringPath recordSource = createString("recordSource");

    public final StringPath services = createString("services");

    public final BooleanPath showBundledIcon = createBoolean("showBundledIcon");

    public final BooleanPath showJeopIcon = createBoolean("showJeopIcon");

    public final BooleanPath showLinkedIcon = createBoolean("showLinkedIcon");

    public final BooleanPath showOpenDisconnectIcon = createBoolean("showOpenDisconnectIcon");

    public final BooleanPath showOpenMacIcon = createBoolean("showOpenMacIcon");

    public final StringPath stateProvince = createString("stateProvince");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath vertekProjectManager = createString("vertekProjectManager");

    public QLocationView(String variable) {
        super(LocationView.class, forVariable(variable));
    }

    public QLocationView(Path<? extends LocationView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationView(PathMetadata metadata) {
        super(LocationView.class, metadata);
    }

}

