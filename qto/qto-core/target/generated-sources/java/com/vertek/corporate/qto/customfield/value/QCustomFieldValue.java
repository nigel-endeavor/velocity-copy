package com.vertek.corporate.qto.customfield.value;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomFieldValue is a Querydsl query type for CustomFieldValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomFieldValue extends EntityPathBase<CustomFieldValue> {

    private static final long serialVersionUID = 842091391L;

    public static final QCustomFieldValue customFieldValue = new QCustomFieldValue("customFieldValue");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final NumberPath<Long> customFieldId = createNumber("customFieldId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath value = createString("value");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCustomFieldValue(String variable) {
        super(CustomFieldValue.class, forVariable(variable));
    }

    public QCustomFieldValue(Path<? extends CustomFieldValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomFieldValue(PathMetadata metadata) {
        super(CustomFieldValue.class, metadata);
    }

}

