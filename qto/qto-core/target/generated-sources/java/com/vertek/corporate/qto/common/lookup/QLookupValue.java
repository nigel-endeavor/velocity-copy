package com.vertek.corporate.qto.common.lookup;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLookupValue is a Querydsl query type for LookupValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLookupValue extends EntityPathBase<LookupValue> {

    private static final long serialVersionUID = 1526887569L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLookupValue lookupValue = new QLookupValue("lookupValue");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath display = createString("display");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QLookupType lookupType;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final NumberPath<Integer> sortSequence = createNumber("sortSequence", Integer.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath value = createString("value");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLookupValue(String variable) {
        this(LookupValue.class, forVariable(variable), INITS);
    }

    public QLookupValue(Path<? extends LookupValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLookupValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLookupValue(PathMetadata metadata, PathInits inits) {
        this(LookupValue.class, metadata, inits);
    }

    public QLookupValue(Class<? extends LookupValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.lookupType = inits.isInitialized("lookupType") ? new QLookupType(forProperty("lookupType")) : null;
    }

}

