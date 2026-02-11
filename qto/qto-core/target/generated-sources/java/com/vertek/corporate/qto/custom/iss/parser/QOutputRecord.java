package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOutputRecord is a Querydsl query type for OutputRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOutputRecord extends EntityPathBase<OutputRecord> {

    private static final long serialVersionUID = 21660308L;

    public static final QOutputRecord outputRecord = new QOutputRecord("outputRecord");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final StringPath aircardProduct = createString("aircardProduct");

    public final StringPath aircardProvider = createString("aircardProvider");

    public final StringPath aircardStatus = createString("aircardStatus");

    public final StringPath aircardStatusDate = createString("aircardStatusDate");

    public final StringPath aircardTn = createString("aircardTn");

    public final StringPath appointmentDate = createString("appointmentDate");

    public final StringPath appointmentTime = createString("appointmentTime");

    public final StringPath carrierOrderSubmitted = createString("carrierOrderSubmitted");

    public final StringPath circuitOwner = createString("circuitOwner");

    public final StringPath ClientDueDate = createString("ClientDueDate");

    public final StringPath completeDate = createString("completeDate");

    public final StringPath confirmedFocDate = createString("confirmedFocDate");

    public final StringPath custBillStartDate = createString("custBillStartDate");

    public final StringPath customerRequestedDate = createString("customerRequestedDate");

    public final StringPath demarc = createString("demarc");

    public final StringPath downloadSpeed = createString("downloadSpeed");

    public final StringPath downloadSpeedType = createString("downloadSpeedType");

    public final StringPath dsl1fbOrder = createString("dsl1fbOrder");

    public final StringPath dslConnectionType = createString("dslConnectionType");

    public final StringPath dslLineType = createString("dslLineType");

    public final StringPath dslNumber = createString("dslNumber");

    public final StringPath dslOwner = createString("dslOwner");

    public final StringPath dslProvider = createString("dslProvider");

    public final StringPath ErfuPtdDate = createString("ErfuPtdDate");

    public final StringPath estimatedFocDate = createString("estimatedFocDate");

    public final StringPath hostOmsNumber = createString("hostOmsNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath installationStatus = createString("installationStatus");

    public final StringPath installationStatusDate = createString("installationStatusDate");

    public final StringPath jeop = createString("jeop");

    public final StringPath lanIps = createString("lanIps");

    public final StringPath LecContractExpiryDate = createString("LecContractExpiryDate");

    public final StringPath localLecCircuitId = createString("localLecCircuitId");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final StringPath modemMakeModel = createString("modemMakeModel");

    public final StringPath modemOnSite = createString("modemOnSite");

    public final StringPath modemOwnership = createString("modemOwnership");

    public final StringPath modemSerialNumber = createString("modemSerialNumber");

    public final StringPath modemShipped = createString("modemShipped");

    public final StringPath modemTrackingNumber = createString("modemTrackingNumber");

    public final StringPath mosScore = createString("mosScore");

    public final StringPath networkUse = createString("networkUse");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath pos = createString("pos");

    public final StringPath preinstallationChecklistComplete = createString("preinstallationChecklistComplete");

    public final StringPath project = createString("project");

    public final StringPath RevisedCompletionDate = createString("RevisedCompletionDate");

    public final StringPath routerConfigured = createString("routerConfigured");

    public final StringPath routerMakeModel = createString("routerMakeModel");

    public final StringPath routerOwnLease = createString("routerOwnLease");

    public final StringPath routerShipped = createString("routerShipped");

    public final StringPath routerTrackingNumber = createString("routerTrackingNumber");

    public final StringPath schedulingSegment = createString("schedulingSegment");

    public final StringPath signonffNumber = createString("signonffNumber");

    public final StringPath siteDescription = createString("siteDescription");

    public final StringPath staticIp = createString("staticIp");

    public final StringPath storeDowntime = createString("storeDowntime");

    public final StringPath subproductType = createString("subproductType");

    public final StringPath supplierBan = createString("supplierBan");

    public final StringPath supplierOrderNumber = createString("supplierOrderNumber");

    public final StringPath supplierSupplierCktId = createString("supplierSupplierCktId");

    public final StringPath supplierTn = createString("supplierTn");

    public final StringPath systemAssetNumber = createString("systemAssetNumber");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final StringPath ticketNumber = createString("ticketNumber");

    public final StringPath ticketStatus = createString("ticketStatus");

    public final StringPath uploadSpeed = createString("uploadSpeed");

    public final StringPath uploadSpeedType = createString("uploadSpeedType");

    public final StringPath userName = createString("userName");

    public final StringPath userPassword = createString("userPassword");

    public final StringPath vci = createString("vci");

    public final StringPath vpi = createString("vpi");

    public final StringPath wanIps = createString("wanIps");

    public final StringPath wanRouterIp = createString("wanRouterIp");

    public final StringPath WapSerialNumber = createString("WapSerialNumber");

    public final StringPath WapSerialNumber2 = createString("WapSerialNumber2");

    public final StringPath WapSerialNumber3 = createString("WapSerialNumber3");

    public QOutputRecord(String variable) {
        super(OutputRecord.class, forVariable(variable));
    }

    public QOutputRecord(Path<? extends OutputRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOutputRecord(PathMetadata metadata) {
        super(OutputRecord.class, metadata);
    }

}

