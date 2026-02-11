package com.vertek.corporate.qto.customfield.field;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomField is a Querydsl query type for CustomField
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomField extends EntityPathBase<CustomField> {

    private static final long serialVersionUID = 1950890779L;

    public static final QCustomField customField = new QCustomField("customField");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final BooleanPath required = createBoolean("required");

    public final ListPath<CustomFieldTab, QCustomFieldTab> tabs = this.<CustomFieldTab, QCustomFieldTab>createList("tabs", CustomFieldTab.class, QCustomFieldTab.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final EnumPath<CustomFieldType> type = createEnum("type", CustomFieldType.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCustomField(String variable) {
        super(CustomField.class, forVariable(variable));
    }

    public QCustomField(Path<? extends CustomField> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomField(PathMetadata metadata) {
        super(CustomField.class, metadata);
    }

}

