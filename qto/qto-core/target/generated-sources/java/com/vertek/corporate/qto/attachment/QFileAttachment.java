package com.vertek.corporate.qto.attachment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileAttachment is a Querydsl query type for FileAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileAttachment extends EntityPathBase<FileAttachment> {

    private static final long serialVersionUID = -1214927307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFileAttachment fileAttachment = new QFileAttachment("fileAttachment");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final QFileAttachmentContent content;

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> fileModifiedDate = createDateTime("fileModifiedDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath mimeType = createString("mimeType");

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> parentFileAttachmentId = createNumber("parentFileAttachmentId", Long.class);

    public final NumberPath<Long> parentOwnerId = createNumber("parentOwnerId", Long.class);

    public final NumberPath<Long> size = createNumber("size", Long.class);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final DateTimePath<java.util.Date> uploadDate = createDateTime("uploadDate", java.util.Date.class);

    public final StringPath uploadedByUserName = createString("uploadedByUserName");

    public final NumberPath<Long> vendorId = createNumber("vendorId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFileAttachment(String variable) {
        this(FileAttachment.class, forVariable(variable), INITS);
    }

    public QFileAttachment(Path<? extends FileAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFileAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFileAttachment(PathMetadata metadata, PathInits inits) {
        this(FileAttachment.class, metadata, inits);
    }

    public QFileAttachment(Class<? extends FileAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QFileAttachmentContent(forProperty("content")) : null;
    }

}

