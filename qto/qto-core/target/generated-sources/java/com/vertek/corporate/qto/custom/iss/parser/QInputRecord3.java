package com.vertek.corporate.qto.custom.iss.parser;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInputRecord3 is a Querydsl query type for InputRecord3
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInputRecord3 extends EntityPathBase<InputRecord3> {

    private static final long serialVersionUID = -2031059782L;

    public static final QInputRecord3 inputRecord3 = new QInputRecord3("inputRecord3");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final StringPath circuitWanIp = createString("circuitWanIp");

    public final StringPath demarcComments = createString("demarcComments");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> inputRecord1Id = createNumber("inputRecord1Id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath omsBillingAccountNumber = createString("omsBillingAccountNumber");

    public final StringPath routerSerialNumber = createString("routerSerialNumber");

    public final StringPath systemAssetNumber = createString("systemAssetNumber");

    public final StringPath ticketNumber = createString("ticketNumber");

    public final StringPath wanRouterIp = createString("wanRouterIp");

    public final StringPath wapSerialNumber = createString("wapSerialNumber");

    public final StringPath wapSerialNumber2 = createString("wapSerialNumber2");

    public final StringPath wapSerialNumber3 = createString("wapSerialNumber3");

    public QInputRecord3(String variable) {
        super(InputRecord3.class, forVariable(variable));
    }

    public QInputRecord3(Path<? extends InputRecord3> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInputRecord3(PathMetadata metadata) {
        super(InputRecord3.class, metadata);
    }

}

