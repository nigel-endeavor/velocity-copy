package com.vertek.corporate.qto.ftdi;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFtdiCustomField is a Querydsl query type for FtdiCustomField
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFtdiCustomField extends EntityPathBase<FtdiCustomField> {

    private static final long serialVersionUID = -829543184L;

    public static final QFtdiCustomField ftdiCustomField = new QFtdiCustomField("ftdiCustomField");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final StringPath dataType = createString("dataType");

    public final StringPath fieldName = createString("fieldName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final ListPath<FtdiCustomFieldValue, QFtdiCustomFieldValue> values = this.<FtdiCustomFieldValue, QFtdiCustomFieldValue>createList("values", FtdiCustomFieldValue.class, QFtdiCustomFieldValue.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFtdiCustomField(String variable) {
        super(FtdiCustomField.class, forVariable(variable));
    }

    public QFtdiCustomField(Path<? extends FtdiCustomField> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFtdiCustomField(PathMetadata metadata) {
        super(FtdiCustomField.class, metadata);
    }

}

