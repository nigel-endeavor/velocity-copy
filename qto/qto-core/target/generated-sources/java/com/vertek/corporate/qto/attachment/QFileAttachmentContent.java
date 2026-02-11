package com.vertek.corporate.qto.attachment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFileAttachmentContent is a Querydsl query type for FileAttachmentContent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileAttachmentContent extends EntityPathBase<FileAttachmentContent> {

    private static final long serialVersionUID = -1327978364L;

    public static final QFileAttachmentContent fileAttachmentContent = new QFileAttachmentContent("fileAttachmentContent");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final ArrayPath<byte[], Byte> data = createArray("data", byte[].class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFileAttachmentContent(String variable) {
        super(FileAttachmentContent.class, forVariable(variable));
    }

    public QFileAttachmentContent(Path<? extends FileAttachmentContent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFileAttachmentContent(PathMetadata metadata) {
        super(FileAttachmentContent.class, metadata);
    }

}

