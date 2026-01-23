package com.vertek.corporate.qto.company;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanyView is a Querydsl query type for CompanyView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyView extends EntityPathBase<CompanyView> {

    private static final long serialVersionUID = 1179670300L;

    public static final QCompanyView companyView = new QCompanyView("companyView");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath accountManager = createString("accountManager");

    public final BooleanPath active = createBoolean("active");

    public final StringPath billingContactEmail = createString("billingContactEmail");

    public final StringPath billingContactName = createString("billingContactName");

    public final StringPath billingContactPhone = createString("billingContactPhone");

    public final StringPath clientId = createString("clientId");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inventoryLocationCount = createNumber("inventoryLocationCount", Long.class);

    public final NumberPath<java.math.BigDecimal> inventoryMrc = createNumber("inventoryMrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> inventoryMrr = createNumber("inventoryMrr", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> inventoryNrr = createNumber("inventoryNrr", java.math.BigDecimal.class);

    public final StringPath lastCompletedTask = createString("lastCompletedTask");

    public final NumberPath<Long> legacyId = createNumber("legacyId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath nextTask = createString("nextTask");

    public final StringPath nextTaskAssignedTo = createString("nextTaskAssignedTo");

    public final NumberPath<java.math.BigDecimal> progressPercentage = createNumber("progressPercentage", java.math.BigDecimal.class);

    public final NumberPath<Long> remainingTasks = createNumber("remainingTasks", Long.class);

    public final StringPath status = createString("status");

    public final NumberPath<Long> taskGroupId = createNumber("taskGroupId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath tenantName = createString("tenantName");

    public final StringPath type = createString("type");

    public final StringPath uuid = createString("uuid");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCompanyView(String variable) {
        super(CompanyView.class, forVariable(variable));
    }

    public QCompanyView(Path<? extends CompanyView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanyView(PathMetadata metadata) {
        super(CompanyView.class, metadata);
    }

}

