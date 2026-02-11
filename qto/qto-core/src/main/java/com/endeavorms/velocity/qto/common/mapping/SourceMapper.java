package com.endeavorms.velocity.qto.common.mapping;

import com.endeavorms.velocity.qto.common.adapter.ImportAdapter;

public class SourceMapper implements PropertyMapper {
    private ImportAdapter adapter;
    private String sourceIndex;

    public SourceMapper() {
    }

    public SourceMapper(ImportAdapter var1, String var2) {
        this.adapter = var1;
        this.sourceIndex = var2;
    }

    public String getValue() throws Exception {
        if (this.sourceIndex == null) {
            throw new Exception("sourceIndex must be set for SourceMapper");
        } else if (this.adapter == null) {
            throw new Exception("Adapter must be set for SourceMapper; sourceIndex=" + this.sourceIndex);
        } else {
            try {
                String var1 = this.adapter.getColumnByName(this.sourceIndex);
                return var1;
            } catch (Exception var4) {
                String var3 = "(" + var4.getClass().getName() + " ) Problem retreiving column " + this.sourceIndex + " from adatper.connection: " + this.adapter.getConnection() + "; .view: " + this.adapter.getView();
                throw new Exception(var3);
            }
        }
    }

    public void setAdapter(ImportAdapter var1) {
        this.adapter = var1;
    }

    public String getSourceIndex() {
        return this.sourceIndex;
    }

    public void setSourceIndex(String var1) {
        this.sourceIndex = var1;
    }

    public ImportAdapter getAdapter() {
        return this.adapter;
    }

    public void setPreMapper(PropertyMapper var1) {
    }
}

