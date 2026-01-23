package com.vertek.corporate.qto.config;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanyConfigurationProperty is a Querydsl query type for CompanyConfigurationProperty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyConfigurationProperty extends EntityPathBase<CompanyConfigurationProperty> {

    private static final long serialVersionUID = -380909531L;

    public static final QCompanyConfigurationProperty companyConfigurationProperty = new QCompanyConfigurationProperty("companyConfigurationProperty");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    public final BooleanPath modifiable = createBoolean("modifiable");

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath type = createString("type");

    public final StringPath value = createString("value");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCompanyConfigurationProperty(String variable) {
        super(CompanyConfigurationProperty.class, forVariable(variable));
    }

    public QCompanyConfigurationProperty(Path<? extends CompanyConfigurationProperty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanyConfigurationProperty(PathMetadata metadata) {
        super(CompanyConfigurationProperty.class, metadata);
    }

}

