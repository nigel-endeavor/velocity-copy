package com.vertek.corporate.qto.attachment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanyFileAttachment is a Querydsl query type for CompanyFileAttachment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanyFileAttachment extends EntityPathBase<CompanyFileAttachment> {

    private static final long serialVersionUID = 1067461222L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanyFileAttachment companyFileAttachment = new QCompanyFileAttachment("companyFileAttachment");

    public final QFileAttachment _super;

    public final NumberPath<Long> companyId = createNumber("companyId", Long.class);

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

    public QCompanyFileAttachment(String variable) {
        this(CompanyFileAttachment.class, forVariable(variable), INITS);
    }

    public QCompanyFileAttachment(Path<? extends CompanyFileAttachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanyFileAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanyFileAttachment(PathMetadata metadata, PathInits inits) {
        this(CompanyFileAttachment.class, metadata, inits);
    }

    public QCompanyFileAttachment(Class<? extends CompanyFileAttachment> type, PathMetadata metadata, PathInits inits) {
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

