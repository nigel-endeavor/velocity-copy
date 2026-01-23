package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFtdiCustomFieldValue is a Querydsl query type for FtdiCustomFieldValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiCustomFieldValue extends EntityPathBase<FtdiCustomFieldValue> {

    private static final long serialVersionUID = -840824223L;

    public static final QFtdiCustomFieldValue ftdiCustomFieldValue = new QFtdiCustomFieldValue("ftdiCustomFieldValue");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> customFieldId = createNumber("customFieldId", Long.class);

    public final StringPath fieldValue = createString("fieldValue");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiCustomFieldValue(String variable) {
        super(FtdiCustomFieldValue.class, forVariable(variable));
    }

    public QFtdiCustomFieldValue(Path<? extends FtdiCustomFieldValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiCustomFieldValue(PathMetadata metadata) {
        super(FtdiCustomFieldValue.class, metadata);
    }

}

