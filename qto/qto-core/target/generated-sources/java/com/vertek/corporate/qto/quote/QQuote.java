package com.vertek.corporate.qto.quote;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuote is a Querydsl query type for Quote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuote extends EntityPathBase<Quote> {

    private static final long serialVersionUID = -2116849195L;

    public static final QQuote quote = new QQuote("quote");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final NumberPath<Integer> accountId = createNumber("accountId", Integer.class);

    public final StringPath accountName = createString("accountName");

    public final StringPath additionalProperties = createString("additionalProperties");

    public final DateTimePath<java.util.Date> handledTime = createDateTime("handledTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath quoteNumber = createString("quoteNumber");

    public final StringPath quoteProvider = createString("quoteProvider");

    public final StringPath quoteString = createString("quoteString");

    public final ListPath<com.vertek.corporate.qto.solution.Solution, com.vertek.corporate.qto.solution.QSolution> solutions = this.<com.vertek.corporate.qto.solution.Solution, com.vertek.corporate.qto.solution.QSolution>createList("solutions", com.vertek.corporate.qto.solution.Solution.class, com.vertek.corporate.qto.solution.QSolution.class, PathInits.DIRECT2);

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final StringPath userEmail = createString("userEmail");

    public final StringPath userName = createString("userName");

    public final StringPath vendorQuoteId = createString("vendorQuoteId");

    public QQuote(String variable) {
        super(Quote.class, forVariable(variable));
    }

    public QQuote(Path<? extends Quote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQuote(PathMetadata metadata) {
        super(Quote.class, metadata);
    }

}

