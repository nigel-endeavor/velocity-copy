package com.vertek.corporate.qto.message;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMessageThreadWatcher is a Querydsl query type for MessageThreadWatcher
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageThreadWatcher extends EntityPathBase<MessageThreadWatcher> {

    private static final long serialVersionUID = 381215015L;

    public static final QMessageThreadWatcher messageThreadWatcher = new QMessageThreadWatcher("messageThreadWatcher");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> messageThreadId = createNumber("messageThreadId", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> subjectId = createNumber("subjectId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMessageThreadWatcher(String variable) {
        super(MessageThreadWatcher.class, forVariable(variable));
    }

    public QMessageThreadWatcher(Path<? extends MessageThreadWatcher> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageThreadWatcher(PathMetadata metadata) {
        super(MessageThreadWatcher.class, metadata);
    }

}

