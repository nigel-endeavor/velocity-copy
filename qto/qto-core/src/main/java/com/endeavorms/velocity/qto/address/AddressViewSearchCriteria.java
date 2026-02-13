package com.endeavorms.velocity.qto.address;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


/**
 * @author rcasey
 * @since 6/12/2024
 */
public class AddressViewSearchCriteria extends BaseSearchCriteria<AddressView> {

    private String search;

    private Long companyId;

    private boolean type;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public boolean getType() {
        return type;
    }

    public void setType(final boolean type) {
        this.type = type;
    }
}
