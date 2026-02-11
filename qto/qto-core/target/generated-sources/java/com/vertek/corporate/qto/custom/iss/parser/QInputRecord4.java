package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInputRecord4 is a Querydsl query type for InputRecord4
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInputRecord4 extends EntityPathBase<InputRecord4> {

    private static final long serialVersionUID = -2031059781L;

    public static final QInputRecord4 inputRecord4 = new QInputRecord4("inputRecord4");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inputRecord1Id = createNumber("inputRecord1Id", Long.class);

    public final StringPath itemNo = createString("itemNo");

    public final NumberPath<Long> needQuantity = createNumber("needQuantity", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath systemAssetNumber = createString("systemAssetNumber");

    public final StringPath ticketNumber = createString("ticketNumber");

    public QInputRecord4(String variable) {
        super(InputRecord4.class, forVariable(variable));
    }

    public QInputRecord4(Path<? extends InputRecord4> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInputRecord4(PathMetadata metadata) {
        super(InputRecord4.class, metadata);
    }

}

