package com.vertek.corporate.qto.dataverse;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCrmIntegration is a Querydsl query type for CrmIntegration
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCrmIntegration extends EntityPathBase<CrmIntegration> {

    private static final long serialVersionUID = 1155773998L;

    public static final QCrmIntegration crmIntegration = new QCrmIntegration("crmIntegration");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath opportunityNum = createString("opportunityNum");

    public final DateTimePath<java.util.Date> requestDate = createDateTime("requestDate", java.util.Date.class);

    public final StringPath response = createString("response");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCrmIntegration(String variable) {
        super(CrmIntegration.class, forVariable(variable));
    }

    public QCrmIntegration(Path<? extends CrmIntegration> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCrmIntegration(PathMetadata metadata) {
        super(CrmIntegration.class, metadata);
    }

}

