package com.endeavorms.velocity.qto.fileimport.importactivity;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import java.util.List;

public class ImportActivitySearchCriteria extends BaseSearchCriteria<ImportActivity> {

    private List<String> importType;

    private List<String> uploadedBy;

    public List<String> getImportType() {
        return importType;
    }

    public void setImportType(final List<String> importType) {
        this.importType = importType;
    }

    public List<String> getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(final List<String> uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
}
