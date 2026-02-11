package com.vertek.corporate.qto.message;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageThread is a Querydsl query type for MessageThread
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessageThread extends EntityPathBase<MessageThread> {

    private static final long serialVersionUID = -1231018283L;

    public static final QMessageThread messageThread = new QMessageThread("messageThread");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> locationId = createNumber("locationId", Long.class);

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final ListPath<Message, QMessage> messages = this.<Message, QMessage>createList("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final ListPath<MessageThreadWatcher, QMessageThreadWatcher> subjects = this.<MessageThreadWatcher, QMessageThreadWatcher>createList("subjects", MessageThreadWatcher.class, QMessageThreadWatcher.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath title = createString("title");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QMessageThread(String variable) {
        super(MessageThread.class, forVariable(variable));
    }

    public QMessageThread(Path<? extends MessageThread> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMessageThread(PathMetadata metadata) {
        super(MessageThread.class, metadata);
    }

}

