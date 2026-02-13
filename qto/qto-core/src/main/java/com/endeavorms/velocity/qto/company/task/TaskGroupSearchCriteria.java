package com.endeavorms.velocity.qto.company.task;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.contact.location.LocationContact;


/**
 * Search criteria used for filtering task groups.
 * @author fcurran
 * @since 9/10/2024
 */
public class TaskGroupSearchCriteria extends BaseSearchCriteria<TaskGroup> {
    /** The location ID to filter by. */
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }
}
