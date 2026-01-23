package com.vertek.corporate.qto.company.task;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTaskGroup is a Querydsl query type for TaskGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTaskGroup extends EntityPathBase<TaskGroup> {

    private static final long serialVersionUID = 2120920567L;

    public static final QTaskGroup taskGroup = new QTaskGroup("taskGroup");

    public final com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity _super = new com.vertek.corporate.qto.common.QAbstractMasterCustomerOwnedEntity(this);

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDefault = createBoolean("isDefault");

    //inherited
    public final NumberPath<Long> masterCustomerId = _super.masterCustomerId;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath taskNames = createString("taskNames");

    public final ListPath<Task, QTask> tasks = this.<Task, QTask>createList("tasks", Task.class, QTask.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> tenantId = _super.tenantId;

    public final StringPath usedBy = createString("usedBy");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QTaskGroup(String variable) {
        super(TaskGroup.class, forVariable(variable));
    }

    public QTaskGroup(Path<? extends TaskGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTaskGroup(PathMetadata metadata) {
        super(TaskGroup.class, metadata);
    }

}

