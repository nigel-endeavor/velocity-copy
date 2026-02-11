package com.vertek.corporate.qto.activation.requirement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivationAttemptRequirement is a Querydsl query type for ActivationAttemptRequirement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivationAttemptRequirement extends EntityPathBase<ActivationAttemptRequirement> {

    private static final long serialVersionUID = 600349978L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivationAttemptRequirement activationAttemptRequirement = new QActivationAttemptRequirement("activationAttemptRequirement");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> activationAttemptId = createNumber("activationAttemptId", Long.class);

    public final StringPath comment = createString("comment");

    public final BooleanPath complete = createBoolean("complete");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final QRequirement requirement;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QActivationAttemptRequirement(String variable) {
        this(ActivationAttemptRequirement.class, forVariable(variable), INITS);
    }

    public QActivationAttemptRequirement(Path<? extends ActivationAttemptRequirement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivationAttemptRequirement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivationAttemptRequirement(PathMetadata metadata, PathInits inits) {
        this(ActivationAttemptRequirement.class, metadata, inits);
    }

    public QActivationAttemptRequirement(Class<? extends ActivationAttemptRequirement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.requirement = inits.isInitialized("requirement") ? new QRequirement(forProperty("requirement"), inits.get("requirement")) : null;
    }

}

