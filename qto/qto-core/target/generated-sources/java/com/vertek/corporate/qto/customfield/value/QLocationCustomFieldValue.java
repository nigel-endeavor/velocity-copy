package com.vertek.corporate.qto.customfield.value;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLocationCustomFieldValue is a Querydsl query type for LocationCustomFieldValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationCustomFieldValue extends EntityPathBase<LocationCustomFieldValue> {

    private static final long serialVersionUID = 141467220L;

    public static final QLocationCustomFieldValue locationCustomFieldValue = new QLocationCustomFieldValue("locationCustomFieldValue");

    public final QCustomFieldValue _super = new QCustomFieldValue(this);

    //inherited
    public final NumberPath<Long> customFieldId = _super.customFieldId;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final StringPath value = _super.value;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QLocationCustomFieldValue(String variable) {
        super(LocationCustomFieldValue.class, forVariable(variable));
    }

    public QLocationCustomFieldValue(Path<? extends LocationCustomFieldValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLocationCustomFieldValue(PathMetadata metadata) {
        super(LocationCustomFieldValue.class, metadata);
    }

}

