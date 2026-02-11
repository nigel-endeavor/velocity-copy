package com.vertek.corporate.qto.attachment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QServiceFileAttachment is a Querydsl query type for ServiceFileAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QServiceFileAttachment extends EntityPathBase<ServiceFileAttachment> {

    private static final long serialVersionUID = 1803711134L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QServiceFileAttachment serviceFileAttachment = new QServiceFileAttachment("serviceFileAttachment");

    public final QFileAttachment _super;

    // inherited
    public final QFileAttachmentContent content;

    //inherited
    public final StringPath description;

    //inherited
    public final DateTimePath<java.util.Date> fileModifiedDate;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final NumberPath<Long> masterCustomerId;

    //inherited
    public final StringPath mimeType;

    //inherited
    public final StringPath name;

    //inherited
    public final BooleanPath new$;

    //inherited
    public final NumberPath<Long> parentFileAttachmentId;

    //inherited
    public final NumberPath<Long> parentOwnerId;

    public final StringPath serviceDisplayText = createString("serviceDisplayText");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    //inherited
    public final NumberPath<Long> size;

    //inherited
    public final NumberPath<Long> tenantId;

    //inherited
    public final DateTimePath<java.util.Date> uploadDate;

    //inherited
    public final StringPath uploadedByUserName;

    //inherited
    public final NumberPath<Long> vendorId;

    //inherited
    public final NumberPath<Integer> version;

    public QServiceFileAttachment(String variable) {
        this(ServiceFileAttachment.class, forVariable(variable), INITS);
    }

    public QServiceFileAttachment(Path<? extends ServiceFileAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QServiceFileAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QServiceFileAttachment(PathMetadata metadata, PathInits inits) {
        this(ServiceFileAttachment.class, metadata, inits);
    }

    public QServiceFileAttachment(Class<? extends ServiceFileAttachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QFileAttachment(type, metadata, inits);
        this.content = _super.content;
        this.description = _super.description;
        this.fileModifiedDate = _super.fileModifiedDate;
        this.id = _super.id;
        this.masterCustomerId = _super.masterCustomerId;
        this.mimeType = _super.mimeType;
        this.name = _super.name;
        this.new$ = _super.new$;
        this.parentFileAttachmentId = _super.parentFileAttachmentId;
        this.parentOwnerId = _super.parentOwnerId;
        this.size = _super.size;
        this.tenantId = _super.tenantId;
        this.uploadDate = _super.uploadDate;
        this.uploadedByUserName = _super.uploadedByUserName;
        this.vendorId = _super.vendorId;
        this.version = _super.version;
    }

}

