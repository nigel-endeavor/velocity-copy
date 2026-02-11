package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiOrderTypeCustomFields is a Querydsl query type for FtdiOrderTypeCustomFields
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiOrderTypeCustomFields extends EntityPathBase<FtdiOrderTypeCustomFields> {

    private static final long serialVersionUID = 1229127033L;

    public static final QFtdiOrderTypeCustomFields ftdiOrderTypeCustomFields = new QFtdiOrderTypeCustomFields("ftdiOrderTypeCustomFields");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Long> customFieldId = createNumber("customFieldId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderTypeId = createNumber("orderTypeId", Long.class);

    public QFtdiOrderTypeCustomFields(String variable) {
        super(FtdiOrderTypeCustomFields.class, forVariable(variable));
    }

    public QFtdiOrderTypeCustomFields(Path<? extends FtdiOrderTypeCustomFields> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiOrderTypeCustomFields(PathMetadata metadata) {
        super(FtdiOrderTypeCustomFields.class, metadata);
    }

}

