package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInputRecord5 is a Querydsl query type for InputRecord5
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInputRecord5 extends EntityPathBase<InputRecord5> {

    private static final long serialVersionUID = -2031059780L;

    public static final QInputRecord5 inputRecord5 = new QInputRecord5("inputRecord5");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inputRecord1Id = createNumber("inputRecord1Id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath ppoaPasswordAc = createString("ppoaPasswordAc");

    public final StringPath ppoaUsernameAc = createString("ppoaUsernameAc");

    public final StringPath productTypeAc = createString("productTypeAc");

    public final StringPath routerBrandModelAc = createString("routerBrandModelAc");

    public final StringPath routerConfiguredAc = createString("routerConfiguredAc");

    public final StringPath routerOwnershipAc = createString("routerOwnershipAc");

    public final StringPath systemAssetNumber = createString("systemAssetNumber");

    public final StringPath ticketNumber = createString("ticketNumber");

    public QInputRecord5(String variable) {
        super(InputRecord5.class, forVariable(variable));
    }

    public QInputRecord5(Path<? extends InputRecord5> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInputRecord5(PathMetadata metadata) {
        super(InputRecord5.class, metadata);
    }

}

