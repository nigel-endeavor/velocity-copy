package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.BaseSearchCriteria;
import com.vertek.corporate.qto.contact.location.LocationContact;

import javax.ws.rs.QueryParam;

/**
 * Search criteria used for filtering task groups.
 * @author fcurran
 * @since 9/10/2024
 */
public class TaskGroupSearchCriteria extends BaseSearchCriteria<TaskGroup> {
    /** The location ID to filter by. */
    @QueryParam("active")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }
}
