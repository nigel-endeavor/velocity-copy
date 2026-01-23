package com.vertek.corporate.qto.template.email;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailTemplate is a Querydsl query type for EmailTemplate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailTemplate extends EntityPathBase<EmailTemplate> {

    private static final long serialVersionUID = -1441201211L;

    public static final QEmailTemplate emailTemplate = new QEmailTemplate("emailTemplate");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final StringPath body = createString("body");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastUpdateBy = createString("lastUpdateBy");

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath subject = createString("subject");

    public final EnumPath<com.vertek.corporate.qto.template.TemplateType> templateType = createEnum("templateType", com.vertek.corporate.qto.template.TemplateType.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QEmailTemplate(String variable) {
        super(EmailTemplate.class, forVariable(variable));
    }

    public QEmailTemplate(Path<? extends EmailTemplate> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailTemplate(PathMetadata metadata) {
        super(EmailTemplate.class, metadata);
    }

}

