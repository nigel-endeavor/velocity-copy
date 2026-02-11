package com.vertek.corporate.qto.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCustomWorklist is a Querydsl query type for CustomWorklist
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomWorklist extends EntityPathBase<CustomWorklist> {

    private static final long serialVersionUID = -281493353L;

    public static final QCustomWorklist customWorklist = new QCustomWorklist("customWorklist");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final NumberPath<Long> authorId = createNumber("authorId", Long.class);

    public final StringPath authorName = createString("authorName");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> lastModifiedDate = createDateTime("lastModifiedDate", java.util.Date.class);

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final BooleanPath shared = createBoolean("shared");

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath worklistName = createString("worklistName");

    public QCustomWorklist(String variable) {
        super(CustomWorklist.class, forVariable(variable));
    }

    public QCustomWorklist(Path<? extends CustomWorklist> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCustomWorklist(PathMetadata metadata) {
        super(CustomWorklist.class, metadata);
    }

}

