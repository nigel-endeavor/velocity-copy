package com.endeavorms.velocity.qto.contact.location;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

/**
 * Search criteria used for filtering location contacts.
 * @author fcurran
 * @since 3/31/2023
 */
public class LocationContactSearchCriteria extends BaseSearchCriteria<LocationContact> {
    /** The location ID to filter by. */
    @QueryParam("locationId")
    private Long locationId;
    /** Open search query string. */
    @QueryParam("search")
    private String search;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }
}
