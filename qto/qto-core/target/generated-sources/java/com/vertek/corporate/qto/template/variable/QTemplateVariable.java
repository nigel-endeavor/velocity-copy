package com.vertek.corporate.qto.template.variable;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTemplateVariable is a Querydsl query type for TemplateVariable
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTemplateVariable extends EntityPathBase<TemplateVariable> {

    private static final long serialVersionUID = 865669161L;

    public static final QTemplateVariable templateVariable = new QTemplateVariable("templateVariable");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath path = createString("path");

    public final EnumPath<com.vertek.corporate.qto.template.TemplateType> templateType = createEnum("templateType", com.vertek.corporate.qto.template.TemplateType.class);

    public final StringPath type = createString("type");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTemplateVariable(String variable) {
        super(TemplateVariable.class, forVariable(variable));
    }

    public QTemplateVariable(Path<? extends TemplateVariable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTemplateVariable(PathMetadata metadata) {
        super(TemplateVariable.class, metadata);
    }

}

