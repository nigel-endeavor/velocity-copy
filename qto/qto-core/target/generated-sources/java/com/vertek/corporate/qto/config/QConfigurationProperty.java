package com.vertek.corporate.qto.config;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfigurationProperty is a Querydsl query type for ConfigurationProperty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfigurationProperty extends EntityPathBase<ConfigurationProperty> {

    private static final long serialVersionUID = -1817617132L;

    public static final QConfigurationProperty configurationProperty = new QConfigurationProperty("configurationProperty");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath key = createString("key");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath value = createString("value");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QConfigurationProperty(String variable) {
        super(ConfigurationProperty.class, forVariable(variable));
    }

    public QConfigurationProperty(Path<? extends ConfigurationProperty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfigurationProperty(PathMetadata metadata) {
        super(ConfigurationProperty.class, metadata);
    }

}

