package com.vertek.corporate.qto.solution;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSolution is a Querydsl query type for Solution
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSolution extends EntityPathBase<Solution> {

    private static final long serialVersionUID = -1301203047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSolution solution = new QSolution("solution");

    public final com.vertek.corporate.qto.common.QStandardBaseEntity _super = new com.vertek.corporate.qto.common.QStandardBaseEntity(this);

    public final StringPath additionalProperties = createString("additionalProperties");

    public final StringPath address = createString("address");

    public final NumberPath<Long> addressStatusId = createNumber("addressStatusId", Long.class);

    public final NumberPath<Long> addressValid = createNumber("addressValid", Long.class);

    public final StringPath apiProductName = createString("apiProductName");

    public final StringPath baseCurrency = createString("baseCurrency");

    public final NumberPath<Long> baseCurrencyId = createNumber("baseCurrencyId", Long.class);

    public final StringPath bldgStatus = createString("bldgStatus");

    public final StringPath buildingCompetitiveRating = createString("buildingCompetitiveRating");

    public final StringPath catalog = createString("catalog");

    public final StringPath city = createString("city");

    public final StringPath countryCode = createString("countryCode");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath customerLocationId = createString("customerLocationId");

    public final StringPath customProductName = createString("customProductName");

    public final StringPath dispositionCode = createString("dispositionCode");

    public final NumberPath<Long> downloadSpeed = createNumber("downloadSpeed", Long.class);

    public final StringPath flexField1 = createString("flexField1");

    public final StringPath flexField2 = createString("flexField2");

    public final StringPath globalLocationId = createString("globalLocationId");

    public final DateTimePath<java.util.Date> handledTime = createDateTime("handledTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> installInterval = createNumber("installInterval", Long.class);

    public final StringPath lastmileSupplier = createString("lastmileSupplier");

    public final StringPath latitude = createString("latitude");

    public final StringPath locationId = createString("locationId");

    public final StringPath longitude = createString("longitude");

    public final StringPath mediaType = createString("mediaType");

    public final NumberPath<java.math.BigDecimal> mrc = createNumber("mrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrcCost = createNumber("mrcCost", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> mrcTotalPrice = createNumber("mrcTotalPrice", java.math.BigDecimal.class);

    public final StringPath netStatus = createString("netStatus");

    //inherited
    public final BooleanPath new$ = _super.new$;

    public final StringPath note = createString("note");

    public final NumberPath<java.math.BigDecimal> nrc = createNumber("nrc", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrcCost = createNumber("nrcCost", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> nrcTotalPrice = createNumber("nrcTotalPrice", java.math.BigDecimal.class);

    public final NumberPath<Long> preferredSupplier = createNumber("preferredSupplier", Long.class);

    public final StringPath priceState = createString("priceState");

    public final StringPath pricingType = createString("pricingType");

    public final StringPath product = createString("product");

    public final StringPath provider = createString("provider");

    public final com.vertek.corporate.qto.quote.QQuote quote;

    public final StringPath quoteCurrency = createString("quoteCurrency");

    public final StringPath secondaryDesignator = createString("secondaryDesignator");

    public final StringPath secondaryNumber = createString("secondaryNumber");

    public final StringPath siteId = createString("siteId");

    public final StringPath siteName = createString("siteName");

    public final StringPath speed = createString("speed");

    public final StringPath state = createString("state");

    public final StringPath status = createString("status");

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    public final StringPath term = createString("term");

    public final StringPath uniqueKey = createString("uniqueKey");

    public final NumberPath<Long> uploadSpeed = createNumber("uploadSpeed", Long.class);

    public final StringPath vendorQuoteId = createString("vendorQuoteId");

    public final StringPath vendorSolutionId = createString("vendorSolutionId");

    public final StringPath zip = createString("zip");

    public QSolution(String variable) {
        this(Solution.class, forVariable(variable), INITS);
    }

    public QSolution(Path<? extends Solution> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSolution(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSolution(PathMetadata metadata, PathInits inits) {
        this(Solution.class, metadata, inits);
    }

    public QSolution(Class<? extends Solution> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.quote = inits.isInitialized("quote") ? new com.vertek.corporate.qto.quote.QQuote(forProperty("quote")) : null;
    }

}

