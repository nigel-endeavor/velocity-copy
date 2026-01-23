package com.vertek.corporate.qto.brokerage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QServiceBrokerage is a Querydsl query type for ServiceBrokerage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceBrokerage extends EntityPathBase<ServiceBrokerage> {

    private static final long serialVersionUID = 1143976206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QServiceBrokerage serviceBrokerage = new QServiceBrokerage("serviceBrokerage");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath agent = createString("agent");

    public final NumberPath<Double> agentPercent = createNumber("agentPercent", Double.class);

    public final StringPath agentRep = createString("agentRep");

    public final StringPath cieTeamedDealInfo = createString("cieTeamedDealInfo");

    public final NumberPath<java.math.BigDecimal> commissionableArc = createNumber("commissionableArc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> commissionableMrc = createNumber("commissionableMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> commissionableNrc = createNumber("commissionableNrc", java.math.BigDecimal.class);

    public final BooleanPath commissionIcb = createBoolean("commissionIcb");

    public final StringPath commissionPaymentType = createString("commissionPaymentType");

    public final NumberPath<Double> commissionReductionPercent = createNumber("commissionReductionPercent", Double.class);

    public final StringPath commissionsAccountNumber = createString("commissionsAccountNumber");

    public final StringPath commissionsSupplier = createString("commissionsSupplier");

    public final StringPath customerOrderAlias = createString("customerOrderAlias");

    public final BooleanPath engineerResource = createBoolean("engineerResource");

    public final NumberPath<Double> engineerResourceAllocation = createNumber("engineerResourceAllocation", Double.class);

    public final NumberPath<java.math.BigDecimal> expectedCommission = createNumber("expectedCommission", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grossProfit = createNumber("grossProfit", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> grossProfitMrc = createNumber("grossProfitMrc", java.math.BigDecimal.class);

    public final NumberPath<Double> grossProfitMrcMultiplier = createNumber("grossProfitMrcMultiplier", Double.class);

    public final BooleanPath grossProfitMrcNeedsToBeEdited = createBoolean("grossProfitMrcNeedsToBeEdited");

    public final NumberPath<java.math.BigDecimal> grossProfitMrcOverride = createNumber("grossProfitMrcOverride", java.math.BigDecimal.class);

    public final BooleanPath grossProfitNeedsToBeEdited = createBoolean("grossProfitNeedsToBeEdited");

    public final NumberPath<java.math.BigDecimal> grossProfitOverride = createNumber("grossProfitOverride", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath internalCommissionsComments = createString("internalCommissionsComments");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath netProviderPoints = createString("netProviderPoints");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath parentTsd = createString("parentTsd");

    public final StringPath percentResidesFromCarrier = createString("percentResidesFromCarrier");

    public final StringPath promotions = createString("promotions");

    public final BooleanPath referral = createBoolean("referral");

    public final StringPath referralName = createString("referralName");

    public final NumberPath<Double> referralPercent = createNumber("referralPercent", Double.class);

    public final BooleanPath repGrossProfitNeedsToBeEdited = createBoolean("repGrossProfitNeedsToBeEdited");

    public final NumberPath<java.math.BigDecimal> repGrossProfitProduction = createNumber("repGrossProfitProduction", java.math.BigDecimal.class);

    public final StringPath secondaryAgency = createString("secondaryAgency");

    public final StringPath secondaryAgencyRep = createString("secondaryAgencyRep");

    public final com.vertek.corporate.qto.service.QService service;

    public final NumberPath<java.math.BigDecimal> spiffAmount = createNumber("spiffAmount", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> subaccountMrc = createNumber("subaccountMrc", java.math.BigDecimal.class);

    public final StringPath subAgent = createString("subAgent");

    public final NumberPath<Double> subAgentPercent = createNumber("subAgentPercent", Double.class);

    public final StringPath subAgentRep = createString("subAgentRep");

    public final BooleanPath submittedInAdvToProvider = createBoolean("submittedInAdvToProvider");

    public final BooleanPath submittedInAdvToTsd = createBoolean("submittedInAdvToTsd");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final NumberPath<java.math.BigDecimal> totalContractValue = createNumber("totalContractValue", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> upliftMrc = createNumber("upliftMrc", java.math.BigDecimal.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceBrokerage(String variable) {
        this(ServiceBrokerage.class, forVariable(variable), INITS);
    }

    public QServiceBrokerage(Path<? extends ServiceBrokerage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QServiceBrokerage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QServiceBrokerage(PathMetadata metadata, PathInits inits) {
        this(ServiceBrokerage.class, metadata, inits);
    }

    public QServiceBrokerage(Class<? extends ServiceBrokerage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.service = inits.isInitialized("service") ? new com.vertek.corporate.qto.service.QService(forProperty("service"), inits.get("service")) : null;
    }

}

