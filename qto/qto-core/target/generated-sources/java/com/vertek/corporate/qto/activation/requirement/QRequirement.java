package com.vertek.corporate.qto.activation.requirement;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRequirement is a Querydsl query type for Requirement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRequirement extends EntityPathBase<Requirement> {

    private static final long serialVersionUID = -1775507371L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRequirement requirement = new QRequirement("requirement");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.vertek.corporate.qto.common.lookup.QLookupValue lookupValue;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final BooleanPath required = createBoolean("required");

    public final NumberPath<Long> requirementTemplateId = createNumber("requirementTemplateId", Long.class);

    public final NumberPath<Long> sortOrder = createNumber("sortOrder", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QRequirement(String variable) {
        this(Requirement.class, forVariable(variable), INITS);
    }

    public QRequirement(Path<? extends Requirement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRequirement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRequirement(PathMetadata metadata, PathInits inits) {
        this(Requirement.class, metadata, inits);
    }

    public QRequirement(Class<? extends Requirement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lookupValue = inits.isInitialized("lookupValue") ? new com.vertek.corporate.qto.common.lookup.QLookupValue(forProperty("lookupValue"), inits.get("lookupValue")) : null;
    }

}

