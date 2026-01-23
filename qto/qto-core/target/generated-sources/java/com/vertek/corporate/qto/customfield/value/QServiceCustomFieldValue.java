package com.vertek.corporate.qto.customfield.value;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QServiceCustomFieldValue is a Querydsl query type for ServiceCustomFieldValue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceCustomFieldValue extends EntityPathBase<ServiceCustomFieldValue> {

    private static final long serialVersionUID = -1530371258L;

    public static final QServiceCustomFieldValue serviceCustomFieldValue = new QServiceCustomFieldValue("serviceCustomFieldValue");

    public final QCustomFieldValue _super = new QCustomFieldValue(this);

    //inherited
    public final NumberPath<Long> customFieldId = _super.customFieldId;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final StringPath value = _super.value;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QServiceCustomFieldValue(String variable) {
        super(ServiceCustomFieldValue.class, forVariable(variable));
    }

    public QServiceCustomFieldValue(Path<? extends ServiceCustomFieldValue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QServiceCustomFieldValue(PathMetadata metadata) {
        super(ServiceCustomFieldValue.class, metadata);
    }

}

