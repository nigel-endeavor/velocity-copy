package com.vertek.corporate.qto.activation.issue;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActivationIssue is a Querydsl query type for ActivationIssue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationIssue extends EntityPathBase<ActivationIssue> {

    private static final long serialVersionUID = -674450741L;

    public static final QActivationIssue activationIssue = new QActivationIssue("activationIssue");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<Long> activationAttemptId = createNumber("activationAttemptId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final StringPath primaryRootCause = createString("primaryRootCause");

    public final NumberPath<Long> rank = createNumber("rank", Long.class);

    public final StringPath secondaryRootCause = createString("secondaryRootCause");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath tertiaryRootCause = createString("tertiaryRootCause");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QActivationIssue(String variable) {
        super(ActivationIssue.class, forVariable(variable));
    }

    public QActivationIssue(Path<? extends ActivationIssue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivationIssue(PathMetadata metadata) {
        super(ActivationIssue.class, metadata);
    }

}

