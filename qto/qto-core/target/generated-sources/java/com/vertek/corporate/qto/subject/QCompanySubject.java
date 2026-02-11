package com.vertek.corporate.qto.subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompanySubject is a Querydsl query type for CompanySubject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompanySubject extends EntityPathBase<CompanySubject> {

    private static final long serialVersionUID = 369255622L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompanySubject companySubject = new QCompanySubject("companySubject");

    public final com.vertek.corporate.qto.common.QStandardVersionedBaseEntity _super = new com.vertek.corporate.qto.common.QStandardVersionedBaseEntity(this);

    public final com.vertek.corporate.qto.company.QCompany company;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSelected = createBoolean("isSelected");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final QSubjectView subject;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCompanySubject(String variable) {
        this(CompanySubject.class, forVariable(variable), INITS);
    }

    public QCompanySubject(Path<? extends CompanySubject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompanySubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompanySubject(PathMetadata metadata, PathInits inits) {
        this(CompanySubject.class, metadata, inits);
    }

    public QCompanySubject(Class<? extends CompanySubject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new com.vertek.corporate.qto.company.QCompany(forProperty("company"), inits.get("company")) : null;
        this.subject = inits.isInitialized("subject") ? new QSubjectView(forProperty("subject")) : null;
    }

}

