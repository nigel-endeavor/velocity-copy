package com.vertek.corporate.qto.service.microsoftLicenses;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMicrosoftLicensesService is a Querydsl query type for MicrosoftLicensesService
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMicrosoftLicensesService extends EntityPathBase<MicrosoftLicensesService> {

    private static final long serialVersionUID = -920982735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMicrosoftLicensesService microsoftLicensesService = new QMicrosoftLicensesService("microsoftLicensesService");

    public final com.vertek.corporate.qto.service.QService _super;

    //inherited
    public final StringPath accountNumber;

    //inherited
    public final StringPath accountPasscode;

    //inherited
    public final StringPath activationLink;

    //inherited
    public final StringPath activationPhone;

    //inherited
    public final BooleanPath active;

    //inherited
    public final StringPath additionalIpBlock;

    //inherited
    public final StringPath additionalIpBlockRequired;

    //inherited
    public final StringPath address1;

    //inherited
    public final StringPath address2;

    //inherited
    public final StringPath alternateId;

    //inherited
    public final NumberPath<java.math.BigDecimal> annualRecurringCost;

    //inherited
    public final BooleanPath autoRenewal;

    //inherited
    public final BooleanPath billable;

    //inherited
    public final NumberPath<Long> billCycle;

    //inherited
    public final StringPath billingEmail;

    //inherited
    public final BooleanPath billToLocation;

    // inherited
    public final com.vertek.corporate.qto.brokerage.QServiceBrokerage brokerageInfo;

    //inherited
    public final StringPath buildingStatus;

    //inherited
    public final BooleanPath bundled;

    public final NumberPath<Long> businessPremium = createNumber("businessPremium", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> circuitTermEndDate;

    //inherited
    public final StringPath city;

    //inherited
    public final StringPath clientServiceId;

    //inherited
    public final StringPath clientServiceInfo;

    //inherited
    public final StringPath clientServiceType;

    //inherited
    public final StringPath connectionHandoffType;

    //inherited
    public final StringPath contractInfo;

    //inherited
    public final DateTimePath<java.util.Date> contractSignedDate;

    //inherited
    public final StringPath contractTerm;

    //inherited
    public final BooleanPath coTerminus;

    //inherited
    public final StringPath country;

    //inherited
    public final StringPath crossConnectId;

    //inherited
    public final StringPath currency;

    //inherited
    public final StringPath customerBillingInstructions;

    public final NumberPath<Long> defenderForOffice365Plan1 = createNumber("defenderForOffice365Plan1", Long.class);

    public final NumberPath<Long> defenderForOffice365Plan2 = createNumber("defenderForOffice365Plan2", Long.class);

    //inherited
    public final DateTimePath<java.util.Date> deletionDate;

    //inherited
    public final StringPath description;

    //inherited
    public final StringPath disconnectReason;

    //inherited
    public final StringPath dmarc;

    //inherited
    public final StringPath dns1;

    //inherited
    public final StringPath dns2;

    //inherited
    public final StringPath downloadSpeed;

    public final NumberPath<Long> e3 = createNumber("e3", Long.class);

    public final NumberPath<Long> e5 = createNumber("e5", Long.class);

    //inherited
    public final NumberPath<java.math.BigDecimal> earlyTerminationFee;

    //inherited
    public final BooleanPath eligibleForInventory;

    //inherited
    public final BooleanPath eligibleForUpdate;

    //inherited
    public final BooleanPath expediteOrder;

    //inherited
    public final StringPath externalOrderReference;

    //inherited
    public final StringPath fieldServicesProvider;

    //inherited
    public final BooleanPath finalUpdate;

    //inherited
    public final DateTimePath<java.util.Date> followUpDate;

    //inherited
    public final BooleanPath hasIcb;

    //inherited
    public final BooleanPath hasOsp;

    //inherited
    public final NumberPath<java.math.BigDecimal> icb;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final BooleanPath ignoreForRenewals;

    //inherited
    public final StringPath insideWiringRequired;

    //inherited
    public final NumberPath<Long> inventoryServiceId;

    //inherited
    public final NumberPath<Long> inventorySortOrder;

    //inherited
    public final StringPath ipFormat;

    //inherited
    public final BooleanPath isCurrentInventory;

    //inherited
    public final StringPath jobNumber;

    //inherited
    public final StringPath lanBlock;

    //inherited
    public final StringPath lanGateway;

    //inherited
    public final StringPath lanIps;

    //inherited
    public final StringPath lanSubnet;

    //inherited
    public final DateTimePath<java.util.Date> lastStatusChange;

    //inherited
    public final StringPath lastUpdateBy;

    //inherited
    public final DateTimePath<java.util.Date> lastUpdateDate;

    //inherited
    public final StringPath legacyDiaId;

    //inherited
    public final StringPath legacyEquipment;

    //inherited
    public final StringPath legacyId;

    //inherited
    public final BooleanPath linked;

    //inherited
    public final BooleanPath linkedBundledParent;

    //inherited
    public final NumberPath<Long> linkedBundledParentId;

    //inherited
    public final StringPath locationHours;

    //inherited
    public final NumberPath<Long> locationId;

    //inherited
    public final NumberPath<java.math.BigDecimal> macdCostChange;

    //inherited
    public final NumberPath<java.math.BigDecimal> macdRevenueChange;

    //inherited
    public final BooleanPath managedService;

    //inherited
    public final BooleanPath markedForDeletion;

    //inherited
    public final NumberPath<Long> masterCustomerId;

    //inherited
    public final StringPath mediaType;

    public final NumberPath<Long> microsoftDefenderForEndpointP1 = createNumber("microsoftDefenderForEndpointP1", Long.class);

    public final NumberPath<Long> microsoftDefenderForEndpointP2 = createNumber("microsoftDefenderForEndpointP2", Long.class);

    public final NumberPath<Long> microsoftEntraIdP1 = createNumber("microsoftEntraIdP1", Long.class);

    public final NumberPath<Long> microsoftEntraIdP2 = createNumber("microsoftEntraIdP2", Long.class);

    public final NumberPath<Long> microsoftEntraSuite = createNumber("microsoftEntraSuite", Long.class);

    public final NumberPath<Long> microsoftIntunePlan1 = createNumber("microsoftIntunePlan1", Long.class);

    public final NumberPath<Long> microsoftIntunePlan2 = createNumber("microsoftIntunePlan2", Long.class);

    //inherited
    public final StringPath microsoftLicensing;

    //inherited
    public final NumberPath<java.math.BigDecimal> mrc;

    //inherited
    public final NumberPath<java.math.BigDecimal> mrr;

    //inherited
    public final StringPath netStatus;

    //inherited
    public final BooleanPath new$;

    //inherited
    public final NumberPath<java.math.BigDecimal> nrc;

    //inherited
    public final NumberPath<java.math.BigDecimal> nrr;

    //inherited
    public final NumberPath<Integer> numberOfEndpoints;

    //inherited
    public final NumberPath<Integer> numberOfUsers;

    //inherited
    public final StringPath opportunityNum;

    //inherited
    public final NumberPath<Long> orderId;

    //inherited
    public final StringPath orderType;

    //inherited
    public final NumberPath<java.math.BigDecimal> osp;

    //inherited
    public final StringPath ospConstIntervalEst;

    //inherited
    public final NumberPath<java.math.BigDecimal> parentMrc;

    //inherited
    public final NumberPath<java.math.BigDecimal> parentMrr;

    //inherited
    public final NumberPath<Long> parentServiceId;

    //inherited
    public final StringPath poNumber;

    //inherited
    public final StringPath postalCode;

    //inherited
    public final StringPath productInstallInterval;

    //inherited
    public final BooleanPath productionImpacting;

    //inherited
    public final NumberPath<Integer> progressPercentage;

    //inherited
    public final StringPath projectName;

    //inherited
    public final StringPath provider;

    //inherited
    public final StringPath providerCircuitId;

    //inherited
    public final StringPath providerOrderNum;

    //inherited
    public final NumberPath<Long> provisioningServiceId;

    //inherited
    public final StringPath quoteSolutionId;

    //inherited
    public final StringPath recordSource;

    //inherited
    public final StringPath renewalCancelNoticePeriod;

    //inherited
    public final StringPath serviceBilledTo;

    //inherited
    public final StringPath shippingAddress1;

    //inherited
    public final StringPath shippingAddress2;

    //inherited
    public final StringPath shippingCity;

    //inherited
    public final StringPath shippingCountry;

    //inherited
    public final StringPath shippingPostalCode;

    //inherited
    public final StringPath shippingState;

    //inherited
    public final StringPath shipTo;

    //inherited
    public final NumberPath<Long> sortOrder;

    //inherited
    public final StringPath speed;

    //inherited
    public final StringPath state;

    //inherited
    public final StringPath status;

    //inherited
    public final StringPath subOrderType;

    //inherited
    public final StringPath subProductType;

    //inherited
    public final StringPath subStatus;

    //inherited
    public final StringPath summaryBill;

    //inherited
    public final StringPath technicalNotes;

    //inherited
    public final NumberPath<Long> tenantId;

    //inherited
    public final StringPath ticketNumber;

    //inherited
    public final StringPath tieDownInfo;

    //inherited
    public final StringPath trunkGroup;

    //inherited
    public final StringPath tspCode;

    //inherited
    public final DateTimePath<java.util.Date> tspCodeExpirationDate;

    //inherited
    public final StringPath type;

    //inherited
    public final NumberPath<Long> typeId;

    //inherited
    public final StringPath underlyingProvider;

    //inherited
    public final BooleanPath updateClient;

    //inherited
    public final StringPath uploadSpeed;

    //inherited
    public final NumberPath<Integer> version;

    //inherited
    public final StringPath wanGateway;

    //inherited
    public final StringPath wanIps;

    //inherited
    public final StringPath wanSubnet;

    public QMicrosoftLicensesService(String variable) {
        this(MicrosoftLicensesService.class, forVariable(variable), INITS);
    }

    public QMicrosoftLicensesService(Path<? extends MicrosoftLicensesService> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMicrosoftLicensesService(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMicrosoftLicensesService(PathMetadata metadata, PathInits inits) {
        this(MicrosoftLicensesService.class, metadata, inits);
    }

    public QMicrosoftLicensesService(Class<? extends MicrosoftLicensesService> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.vertek.corporate.qto.service.QService(type, metadata, inits);
        this.accountNumber = _super.accountNumber;
        this.accountPasscode = _super.accountPasscode;
        this.activationLink = _super.activationLink;
        this.activationPhone = _super.activationPhone;
        this.active = _super.active;
        this.additionalIpBlock = _super.additionalIpBlock;
        this.additionalIpBlockRequired = _super.additionalIpBlockRequired;
        this.address1 = _super.address1;
        this.address2 = _super.address2;
        this.alternateId = _super.alternateId;
        this.annualRecurringCost = _super.annualRecurringCost;
        this.autoRenewal = _super.autoRenewal;
        this.billable = _super.billable;
        this.billCycle = _super.billCycle;
        this.billingEmail = _super.billingEmail;
        this.billToLocation = _super.billToLocation;
        this.brokerageInfo = _super.brokerageInfo;
        this.buildingStatus = _super.buildingStatus;
        this.bundled = _super.bundled;
        this.circuitTermEndDate = _super.circuitTermEndDate;
        this.city = _super.city;
        this.clientServiceId = _super.clientServiceId;
        this.clientServiceInfo = _super.clientServiceInfo;
        this.clientServiceType = _super.clientServiceType;
        this.connectionHandoffType = _super.connectionHandoffType;
        this.contractInfo = _super.contractInfo;
        this.contractSignedDate = _super.contractSignedDate;
        this.contractTerm = _super.contractTerm;
        this.coTerminus = _super.coTerminus;
        this.country = _super.country;
        this.crossConnectId = _super.crossConnectId;
        this.currency = _super.currency;
        this.customerBillingInstructions = _super.customerBillingInstructions;
        this.deletionDate = _super.deletionDate;
        this.description = _super.description;
        this.disconnectReason = _super.disconnectReason;
        this.dmarc = _super.dmarc;
        this.dns1 = _super.dns1;
        this.dns2 = _super.dns2;
        this.downloadSpeed = _super.downloadSpeed;
        this.earlyTerminationFee = _super.earlyTerminationFee;
        this.eligibleForInventory = _super.eligibleForInventory;
        this.eligibleForUpdate = _super.eligibleForUpdate;
        this.expediteOrder = _super.expediteOrder;
        this.externalOrderReference = _super.externalOrderReference;
        this.fieldServicesProvider = _super.fieldServicesProvider;
        this.finalUpdate = _super.finalUpdate;
        this.followUpDate = _super.followUpDate;
        this.hasIcb = _super.hasIcb;
        this.hasOsp = _super.hasOsp;
        this.icb = _super.icb;
        this.id = _super.id;
        this.ignoreForRenewals = _super.ignoreForRenewals;
        this.insideWiringRequired = _super.insideWiringRequired;
        this.inventoryServiceId = _super.inventoryServiceId;
        this.inventorySortOrder = _super.inventorySortOrder;
        this.ipFormat = _super.ipFormat;
        this.isCurrentInventory = _super.isCurrentInventory;
        this.jobNumber = _super.jobNumber;
        this.lanBlock = _super.lanBlock;
        this.lanGateway = _super.lanGateway;
        this.lanIps = _super.lanIps;
        this.lanSubnet = _super.lanSubnet;
        this.lastStatusChange = _super.lastStatusChange;
        this.lastUpdateBy = _super.lastUpdateBy;
        this.lastUpdateDate = _super.lastUpdateDate;
        this.legacyDiaId = _super.legacyDiaId;
        this.legacyEquipment = _super.legacyEquipment;
        this.legacyId = _super.legacyId;
        this.linked = _super.linked;
        this.linkedBundledParent = _super.linkedBundledParent;
        this.linkedBundledParentId = _super.linkedBundledParentId;
        this.locationHours = _super.locationHours;
        this.locationId = _super.locationId;
        this.macdCostChange = _super.macdCostChange;
        this.macdRevenueChange = _super.macdRevenueChange;
        this.managedService = _super.managedService;
        this.markedForDeletion = _super.markedForDeletion;
        this.masterCustomerId = _super.masterCustomerId;
        this.mediaType = _super.mediaType;
        this.microsoftLicensing = _super.microsoftLicensing;
        this.mrc = _super.mrc;
        this.mrr = _super.mrr;
        this.netStatus = _super.netStatus;
        this.new$ = _super.new$;
        this.nrc = _super.nrc;
        this.nrr = _super.nrr;
        this.numberOfEndpoints = _super.numberOfEndpoints;
        this.numberOfUsers = _super.numberOfUsers;
        this.opportunityNum = _super.opportunityNum;
        this.orderId = _super.orderId;
        this.orderType = _super.orderType;
        this.osp = _super.osp;
        this.ospConstIntervalEst = _super.ospConstIntervalEst;
        this.parentMrc = _super.parentMrc;
        this.parentMrr = _super.parentMrr;
        this.parentServiceId = _super.parentServiceId;
        this.poNumber = _super.poNumber;
        this.postalCode = _super.postalCode;
        this.productInstallInterval = _super.productInstallInterval;
        this.productionImpacting = _super.productionImpacting;
        this.progressPercentage = _super.progressPercentage;
        this.projectName = _super.projectName;
        this.provider = _super.provider;
        this.providerCircuitId = _super.providerCircuitId;
        this.providerOrderNum = _super.providerOrderNum;
        this.provisioningServiceId = _super.provisioningServiceId;
        this.quoteSolutionId = _super.quoteSolutionId;
        this.recordSource = _super.recordSource;
        this.renewalCancelNoticePeriod = _super.renewalCancelNoticePeriod;
        this.serviceBilledTo = _super.serviceBilledTo;
        this.shippingAddress1 = _super.shippingAddress1;
        this.shippingAddress2 = _super.shippingAddress2;
        this.shippingCity = _super.shippingCity;
        this.shippingCountry = _super.shippingCountry;
        this.shippingPostalCode = _super.shippingPostalCode;
        this.shippingState = _super.shippingState;
        this.shipTo = _super.shipTo;
        this.sortOrder = _super.sortOrder;
        this.speed = _super.speed;
        this.state = _super.state;
        this.status = _super.status;
        this.subOrderType = _super.subOrderType;
        this.subProductType = _super.subProductType;
        this.subStatus = _super.subStatus;
        this.summaryBill = _super.summaryBill;
        this.technicalNotes = _super.technicalNotes;
        this.tenantId = _super.tenantId;
        this.ticketNumber = _super.ticketNumber;
        this.tieDownInfo = _super.tieDownInfo;
        this.trunkGroup = _super.trunkGroup;
        this.tspCode = _super.tspCode;
        this.tspCodeExpirationDate = _super.tspCodeExpirationDate;
        this.type = _super.type;
        this.typeId = _super.typeId;
        this.underlyingProvider = _super.underlyingProvider;
        this.updateClient = _super.updateClient;
        this.uploadSpeed = _super.uploadSpeed;
        this.version = _super.version;
        this.wanGateway = _super.wanGateway;
        this.wanIps = _super.wanIps;
        this.wanSubnet = _super.wanSubnet;
    }

}

