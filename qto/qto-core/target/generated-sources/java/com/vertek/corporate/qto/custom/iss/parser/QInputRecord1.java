package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInputRecord1 is a Querydsl query type for InputRecord1
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInputRecord1 extends EntityPathBase<InputRecord1> {

    private static final long serialVersionUID = -2031059784L;

    public static final QInputRecord1 inputRecord1 = new QInputRecord1("inputRecord1");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final StringPath Address1 = createString("Address1");

    public final StringPath Address2 = createString("Address2");

    public final StringPath airCardFlag = createString("airCardFlag");

    public final StringPath brand = createString("brand");

    public final StringPath city = createString("city");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    public final StringPath dbu = createString("dbu");

    public final DateTimePath<java.util.Date> estInstallCompDate = createDateTime("estInstallCompDate", java.util.Date.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath franchise = createString("franchise");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> issDueDate = createDateTime("issDueDate", java.util.Date.class);

    public final StringPath itemNo = createString("itemNo");

    public final StringPath jobNumber = createString("jobNumber");

    public final StringPath lconName = createString("lconName");

    public final StringPath lconNumber = createString("lconNumber");

    public final StringPath lecProvider = createString("lecProvider");

    public final StringPath networkUse = createString("networkUse");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath parentEntity = createString("parentEntity");

    public final StringPath phoneLineUsed = createString("phoneLineUsed");

    public final StringPath processedFolder = createString("processedFolder");

    public final StringPath providerTn = createString("providerTn");

    public final StringPath purchaseOrderNumber = createString("purchaseOrderNumber");

    public final StringPath quantity = createString("quantity");

    public final DateTimePath<java.util.Date> salesOrderCompDate = createDateTime("salesOrderCompDate", java.util.Date.class);

    public final StringPath sarNumber = createString("sarNumber");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath siteInfo = createString("siteInfo");

    public final StringPath siteName = createString("siteName");

    public final StringPath siteType = createString("siteType");

    public final StringPath state = createString("state");

    public final StringPath SystemAssetNumber = createString("SystemAssetNumber");

    public final StringPath systemType = createString("systemType");

    public final StringPath ticketNumber = createString("ticketNumber");

    public final StringPath trackerId = createString("trackerId");

    public final StringPath vendorName = createString("vendorName");

    public final StringPath vendorNumber = createString("vendorNumber");

    public final StringPath zip = createString("zip");

    public QInputRecord1(String variable) {
        super(InputRecord1.class, forVariable(variable));
    }

    public QInputRecord1(Path<? extends InputRecord1> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInputRecord1(PathMetadata metadata) {
        super(InputRecord1.class, metadata);
    }

}

