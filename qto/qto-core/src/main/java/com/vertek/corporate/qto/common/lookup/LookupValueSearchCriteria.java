package com.vertek.corporate.qto.common.lookup;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import javax.ws.rs.QueryParam;

/**
 * Simple Search Criteria for LookupValues.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.4.0
 */
public class LookupValueSearchCriteria extends BaseSearchCriteria<LookupValue> {

    /** The LookupType type code.*/
    @QueryParam("typeCode")
    private String typeCode;

    /** Active flag.*/
    @QueryParam("active")
    private Boolean active;

    /** The value to filter on. */
    @QueryParam("value")
    protected String value;

    /** The lookup value parent id. */
    @QueryParam("parentId")
    private Long parentId;

    /** Lookup value company id, used to determine tenant. */
    @QueryParam("companyId")
    private Long companyId;


    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(final String typeCode) {
        this.typeCode = typeCode;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(final Boolean active) {
        this.active = active;
    }


    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
