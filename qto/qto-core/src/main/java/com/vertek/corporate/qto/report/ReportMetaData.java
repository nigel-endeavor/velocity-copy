package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "report_meta_data")
public class ReportMetaData implements BaseEntity<Long> {
    @Id
    @Column(name = "report_id")
    private Long id;
            
    @Column(name = "report_name")
    private String reportName;

    @Column(name = "report_type")
    private String reportType;

    @Column(name = "sql_text")
    private String sqlText;

    @Column(name = "header_text")
    private String headerText;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(final String reportName) {
        this.reportName = reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(final String reportType) {
        this.reportType = reportType;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(final String sqlText) {
        this.sqlText = sqlText;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(final String headerText) {
        this.headerText = headerText;
    }
}
