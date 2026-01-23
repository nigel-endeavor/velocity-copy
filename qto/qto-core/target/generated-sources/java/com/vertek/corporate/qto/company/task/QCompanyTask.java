package com.vertek.corporate.qto.company.task;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyTask is a Querydsl query type for CompanyTask
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyTask extends EntityPathBase<CompanyTask> {

    private static final long serialVersionUID = -299694497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyTask companyTask = new QCompanyTask("companyTask");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final StringPath assignedTo = createString("assignedTo");

    public final StringPath comment = createString("comment");

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final DateTimePath<java.util.Date> completeDate = createDateTime("completeDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> sortOrder = createNumber("sortOrder", Long.class);

    public final QTask task;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath value = createString("value");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCompanyTask(String variable) {
        this(CompanyTask.class, forVariable(variable), INITS);
    }

    public QCompanyTask(Path<? extends CompanyTask> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyTask(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyTask(PathMetadata metadata, PathInits inits) {
        this(CompanyTask.class, metadata, inits);
    }

    public QCompanyTask(Class<? extends CompanyTask> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.task = inits.isInitialized("task") ? new QTask(forProperty("task"), inits.get("task")) : null;
    }

}

