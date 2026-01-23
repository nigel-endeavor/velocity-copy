package com.vertek.corporate.qto.fileimport.importactivity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QImportActivity is a Querydsl query type for ImportActivity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImportActivity extends EntityPathBase<ImportActivity> {

    private static final long serialVersionUID = 1749762774L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QImportActivity importActivity = new QImportActivity("importActivity");

    public final com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractTenantOwnedEntity(this);

    public final com.vertek.corporate.qto.attachment.QFileAttachment errorFileAttachment;

    public final com.vertek.corporate.qto.attachment.QFileAttachment fileAttachment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> importEndDate = createDateTime("importEndDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> importStartDate = createDateTime("importStartDate", java.util.Date.class);

    public final StringPath importType = createString("importType");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> numFailed = createNumber("numFailed", Long.class);

    public final NumberPath<Long> numSuccessful = createNumber("numSuccessful", Long.class);

    public final EnumPath<com.vertek.corporate.qto.fileimport.ImportActivityStatus> status = createEnum("status", com.vertek.corporate.qto.fileimport.ImportActivityStatus.class);

    public final StringPath statusDetails = createString("statusDetails");

    public final NumberPath<Long> subjectId = createNumber("subjectId", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QImportActivity(String variable) {
        this(ImportActivity.class, forVariable(variable), INITS);
    }

    public QImportActivity(Path<? extends ImportActivity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QImportActivity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QImportActivity(PathMetadata metadata, PathInits inits) {
        this(ImportActivity.class, metadata, inits);
    }

    public QImportActivity(Class<? extends ImportActivity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.errorFileAttachment = inits.isInitialized("errorFileAttachment") ? new com.vertek.corporate.qto.attachment.QFileAttachment(forProperty("errorFileAttachment"), inits.get("errorFileAttachment")) : null;
        this.fileAttachment = inits.isInitialized("fileAttachment") ? new com.vertek.corporate.qto.attachment.QFileAttachment(forProperty("fileAttachment"), inits.get("fileAttachment")) : null;
    }

}

