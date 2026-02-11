package com.vertek.corporate.qto.common.lookup;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLookupType is a Querydsl query type for LookupType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLookupType extends EntityPathBase<LookupType> {

    private static final long serialVersionUID = 1711786010L;

    public static final QLookupType lookupType = new QLookupType("lookupType");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath category = createString("category");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath modifiable = createBoolean("modifiable");

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> parentLookupTypeId = createNumber("parentLookupTypeId", Long.class);

    public final NumberPath<Integer> sortStrategy = createNumber("sortStrategy", Integer.class);

    public final StringPath typeCode = createString("typeCode");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLookupType(String variable) {
        super(LookupType.class, forVariable(variable));
    }

    public QLookupType(Path<? extends LookupType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLookupType(PathMetadata metadata) {
        super(LookupType.class, metadata);
    }

}

