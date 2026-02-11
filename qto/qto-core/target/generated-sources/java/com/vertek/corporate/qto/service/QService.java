package com.vertek.corporate.qto.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QService is a Querydsl query type for Service
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QService extends EntityPathBase<Service> {

    private static final long serialVersionUID = -536426425L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QService service = new QService("service");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath accountNumber = createString("accountNumber");

    public final StringPath accountPasscode = createString("accountPasscode");

    public final StringPath activationLink = createString("activationLink");

    public final StringPath activationPhone = createString("activationPhone");

    public final BooleanPath active = createBoolean("active");

    public final StringPath additionalIpBlock = createString("additionalIpBlock");

    public final StringPath additionalIpBlockRequired = createString("additionalIpBlockRequired");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath alternateId = createString("alternateId");

    public final NumberPath<java.math.BigDecimal> annualRecurringCost = createNumber("annualRecurringCost", java.math.BigDecimal.class);

    public final BooleanPath autoRenewal = createBoolean("autoRenewal");

    public final BooleanPath billable = createBoolean("billable");

    public final NumberPath<Long> billCycle = createNumber("billCycle", Long.class);

    public final StringPath billingEmail = createString("billingEmail");

    public final BooleanPath billToLocation = createBoolean("billToLocation");

    public final com.vertek.corporate.qto.brokerage.QServiceBrokerage brokerageInfo;

    public final StringPath buildingStatus = createString("buildingStatus");

    public final BooleanPath bundled = createBoolean("bundled");

    public final DateTimePath<java.util.Date> circuitTermEndDate = createDateTime("circuitTermEndDate", java.util.Date.class);

    public final StringPath city = createString("city");

    public final StringPath clientServiceId = createString("clientServiceId");

    public final StringPath clientServiceInfo = createString("clientServiceInfo");

    public final StringPath clientServiceType = createString("clientServiceType");

    public final StringPath connectionHandoffType = createString("connectionHandoffType");

    public final StringPath contractInfo = createString("contractInfo");

    public final DateTimePath<java.util.Date> contractSignedDate = createDateTime("contractSignedDate", java.util.Date.class);

    public final StringPath contractTerm = createString("contractTerm");

    public final BooleanPath coTerminus = createBoolean("coTerminus");

    public final StringPath country = createString("country");

    public final StringPath crossConnectId = createString("crossConnectId");

    public final StringPath currency = createString("currency");

    public final StringPath customerBillingInstructions = createString("customerBillingInstructions");

    public final DateTimePath<java.util.Date> deletionDate = createDateTime("deletionDate", java.util.Date.class);

    public final StringPath description = createString("description");

    public final StringPath disconnectReason = createString("disconnectReason");

    public final StringPath dmarc = createString("dmarc");

    public final StringPath dns1 = createString("dns1");

    public final StringPath dns2 = createString("dns2");

    public final StringPath downloadSpeed = createString("downloadSpeed");

    public final NumberPath<java.math.BigDecimal> earlyTerminationFee = createNumber("earlyTerminationFee", java.math.BigDecimal.class);

    public final BooleanPath eligibleForInventory = createBoolean("eligibleForInventory");

    public final BooleanPath eligibleForUpdate = createBoolean("eligibleForUpdate");

    public final BooleanPath expediteOrder = createBoolean("expediteOrder");

    public final StringPath externalOrderReference = createString("externalOrderReference");

    public final StringPath fieldServicesProvider = createString("fieldServicesProvider");

    public final BooleanPath finalUpdate = createBoolean("finalUpdate");

    public final DateTimePath<java.util.Date> followUpDate = createDateTime("followUpDate", java.util.Date.class);

    public final BooleanPath hasIcb = createBoolean("hasIcb");

    public final BooleanPath hasOsp = createBoolean("hasOsp");

    public final NumberPath<java.math.BigDecimal> icb = createNumber("icb", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath ignoreForRenewals = createBoolean("ignoreForRenewals");

    public final StringPath insideWiringRequired = createString("insideWiringRequired");

    public final NumberPath<Long> inventoryServiceId = createNumber("inventoryServiceId", Long.class);

    public final NumberPath<Long> inventorySortOrder = createNumber("inventorySortOrder", Long.class);

    public final StringPath ipFormat = createString("ipFormat");

    public final BooleanPath isCurrentInventory = createBoolean("isCurrentInventory");

    public final StringPath jobNumber = createString("jobNumber");

    public final StringPath lanBlock = createString("lanBlock");

    public final StringPath lanGateway = createString("lanGateway");

    public final StringPath lanIps = createString("lanIps");

    public final StringPath lanSubnet = createString("lanSubnet");

    public final DateTimePath<java.util.Date> lastStatusChange = createDateTime("lastStatusChange", java.util.Date.class);

    public final StringPath lastUpdateBy = createString("lastUpdateBy");

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final StringPath legacyDiaId = createString("legacyDiaId");

    public final StringPath legacyEquipment = createString("legacyEquipment");

    public final StringPath legacyId = createString("legacyId");

    public final BooleanPath linked = createBoolean("linked");

    public final BooleanPath linkedBundledParent = createBoolean("linkedBundledParent");

    public final NumberPath<Long> linkedBundledParentId = createNumber("linkedBundledParentId", Long.class);

    public final StringPath locationHours = createString("locationHours");

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    public final NumberPath<java.math.BigDecimal> macdCostChange = createNumber("macdCostChange", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> macdRevenueChange = createNumber("macdRevenueChange", java.math.BigDecimal.class);

    public final BooleanPath managedService = createBoolean("managedService");

    public final BooleanPath markedForDeletion = createBoolean("markedForDeletion");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath mediaType = createString("mediaType");

    public final StringPath microsoftLicensing = createString("microsoftLicensing");

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrr = createNumber("mrr", java.math.BigDecimal.class);

    public final StringPath netStatus = createString("netStatus");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrr = createNumber("nrr", java.math.BigDecimal.class);

    public final NumberPath<Integer> numberOfEndpoints = createNumber("numberOfEndpoints", Integer.class);

    public final NumberPath<Integer> numberOfUsers = createNumber("numberOfUsers", Integer.class);

    public final StringPath opportunityNum = createString("opportunityNum");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath orderType = createString("orderType");

    public final NumberPath<java.math.BigDecimal> osp = createNumber("osp", java.math.BigDecimal.class);

    public final StringPath ospConstIntervalEst = createString("ospConstIntervalEst");

    public final NumberPath<java.math.BigDecimal> parentMrc = createNumber("parentMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> parentMrr = createNumber("parentMrr", java.math.BigDecimal.class);

    public final NumberPath<Long> parentServiceId = createNumber("parentServiceId", Long.class);

    public final StringPath poNumber = createString("poNumber");

    public final StringPath postalCode = createString("postalCode");

    public final StringPath productInstallInterval = createString("productInstallInterval");

    public final BooleanPath productionImpacting = createBoolean("productionImpacting");

    public final NumberPath<Integer> progressPercentage = createNumber("progressPercentage", Integer.class);

    public final StringPath projectName = createString("projectName");

    public final StringPath provider = createString("provider");

    public final StringPath providerCircuitId = createString("providerCircuitId");

    public final StringPath providerOrderNum = createString("providerOrderNum");

    public final NumberPath<Long> provisioningServiceId = createNumber("provisioningServiceId", Long.class);

    public final StringPath quoteSolutionId = createString("quoteSolutionId");

    public final StringPath recordSource = createString("recordSource");

    public final StringPath renewalCancelNoticePeriod = createString("renewalCancelNoticePeriod");

    public final StringPath serviceBilledTo = createString("serviceBilledTo");

    public final StringPath shippingAddress1 = createString("shippingAddress1");

    public final StringPath shippingAddress2 = createString("shippingAddress2");

    public final StringPath shippingCity = createString("shippingCity");

    public final StringPath shippingCountry = createString("shippingCountry");

    public final StringPath shippingPostalCode = createString("shippingPostalCode");

    public final StringPath shippingState = createString("shippingState");

    public final StringPath shipTo = createString("shipTo");

    public final NumberPath<Long> sortOrder = createNumber("sortOrder", Long.class);

    public final StringPath speed = createString("speed");

    public final StringPath state = createString("state");

    public final StringPath status = createString("status");

    public final StringPath subOrderType = createString("subOrderType");

    public final StringPath subProductType = createString("subProductType");

    public final StringPath subStatus = createString("subStatus");

    public final StringPath summaryBill = createString("summaryBill");

    public final StringPath technicalNotes = createString("technicalNotes");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath ticketNumber = createString("ticketNumber");

    public final StringPath tieDownInfo = createString("tieDownInfo");

    public final StringPath trunkGroup = createString("trunkGroup");

    public final StringPath tspCode = createString("tspCode");

    public final DateTimePath<java.util.Date> tspCodeExpirationDate = createDateTime("tspCodeExpirationDate", java.util.Date.class);

    public final StringPath type = createString("type");

    public final NumberPath<Long> typeId = createNumber("typeId", Long.class);

    public final StringPath underlyingProvider = createString("underlyingProvider");

    public final BooleanPath updateClient = createBoolean("updateClient");

    public final StringPath uploadSpeed = createString("uploadSpeed");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath wanGateway = createString("wanGateway");

    public final StringPath wanIps = createString("wanIps");

    public final StringPath wanSubnet = createString("wanSubnet");

    public QService(String variable) {
        this(Service.class, forVariable(variable), INITS);
    }

    public QService(Path<? extends Service> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QService(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QService(PathMetadata metadata, PathInits inits) {
        this(Service.class, metadata, inits);
    }

    public QService(Class<? extends Service> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brokerageInfo = inits.isInitialized("brokerageInfo") ? new com.vertek.corporate.qto.brokerage.QServiceBrokerage(forProperty("brokerageInfo"), inits.get("brokerageInfo")) : null;
    }

}

