package com.vertek.corporate.qto.customfield.field;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomFieldTab is a Querydsl query type for CustomFieldTab
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomFieldTab extends EntityPathBase<CustomFieldTab> {

    private static final long serialVersionUID = -510168454L;

    public static final QCustomFieldTab customFieldTab = new QCustomFieldTab("customFieldTab");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Long> customFieldId = createNumber("customFieldId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final EnumPath<CustomFieldTabValue> tab = createEnum("tab", CustomFieldTabValue.class);

    public QCustomFieldTab(String variable) {
        super(CustomFieldTab.class, forVariable(variable));
    }

    public QCustomFieldTab(Path<? extends CustomFieldTab> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomFieldTab(PathMetadata metadata) {
        super(CustomFieldTab.class, metadata);
    }

}

