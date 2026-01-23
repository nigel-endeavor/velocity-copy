package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInputRecord2 is a Querydsl query type for InputRecord2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInputRecord2 extends EntityPathBase<InputRecord2> {

    private static final long serialVersionUID = -2031059783L;

    public static final QInputRecord2 inputRecord2 = new QInputRecord2("inputRecord2");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final StringPath circuitOwnership = createString("circuitOwnership");

    public final StringPath connectionType = createString("connectionType");

    public final StringPath dmarcInfo = createString("dmarcInfo");

    public final StringPath downloadSpeed = createString("downloadSpeed");

    public final StringPath downloadSpeedType = createString("downloadSpeedType");

    public final StringPath dsl1fbOwner = createString("dsl1fbOwner");

    public final StringPath dsl1fbProvider = createString("dsl1fbProvider");

    public final StringPath dslPhoneNumber = createString("dslPhoneNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inputRecord1Id = createNumber("inputRecord1Id", Long.class);

    public final StringPath lanIps = createString("lanIps");

    public final StringPath lecCircuitId = createString("lecCircuitId");

    public final DateTimePath<java.util.Date> lecContractExpiryDate = createDateTime("lecContractExpiryDate", java.util.Date.class);

    public final StringPath lineType = createString("lineType");

    public final StringPath modemBrandModel = createString("modemBrandModel");

    public final StringPath modemOnSite = createString("modemOnSite");

    public final StringPath modemOwnership = createString("modemOwnership");

    public final StringPath modemSerialNumber = createString("modemSerialNumber");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath OneFbOrderNumber = createString("OneFbOrderNumber");

    public final StringPath pos = createString("pos");

    public final StringPath RouterBrandModel = createString("RouterBrandModel");

    public final StringPath RouterConfigured = createString("RouterConfigured");

    public final StringPath RouterOwnership = createString("RouterOwnership");

    public final StringPath specialInstallInstructions = createString("specialInstallInstructions");

    public final StringPath StaticIp = createString("StaticIp");

    public final StringPath SubproductType = createString("SubproductType");

    public final StringPath SupplierBan = createString("SupplierBan");

    public final StringPath SupplierSupplierCktId = createString("SupplierSupplierCktId");

    public final StringPath SystemAssetNumber = createString("SystemAssetNumber");

    public final StringPath ticketNumber = createString("ticketNumber");

    public final StringPath UploadSpeed = createString("UploadSpeed");

    public final StringPath UploadSpeedType = createString("UploadSpeedType");

    public final StringPath UserName = createString("UserName");

    public final StringPath UserPassword = createString("UserPassword");

    public QInputRecord2(String variable) {
        super(InputRecord2.class, forVariable(variable));
    }

    public QInputRecord2(Path<? extends InputRecord2> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInputRecord2(PathMetadata metadata) {
        super(InputRecord2.class, metadata);
    }

}

