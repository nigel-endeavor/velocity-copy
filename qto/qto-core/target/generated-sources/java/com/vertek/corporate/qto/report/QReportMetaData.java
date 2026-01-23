package com.vertek.corporate.qto.report;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReportMetaData is a Querydsl query type for ReportMetaData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportMetaData extends EntityPathBase<ReportMetaData> {

    private static final long serialVersionUID = -763727768L;

    public static final QReportMetaData reportMetaData = new QReportMetaData("reportMetaData");

    public final StringPath headerText = createString("headerText");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath reportName = createString("reportName");

    public final StringPath reportType = createString("reportType");

    public final StringPath sqlText = createString("sqlText");

    public QReportMetaData(String variable) {
        super(ReportMetaData.class, forVariable(variable));
    }

    public QReportMetaData(Path<? extends ReportMetaData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReportMetaData(PathMetadata metadata) {
        super(ReportMetaData.class, metadata);
    }

}

