package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;

import java.util.List;

/**
 * Base data access for MilestoneInstances.
 * @param <T> type derived from MilestoneInstance.
 * @author mmaloney
 */
public abstract class AbstractMilestoneInstanceJpaDao<T extends MilestoneInstance>
        extends AbstractMasterCustomerJpaDao<T> {

    /**
     * Gets all milestones associated with a record (Order, Site, Schedule).
     * @param recordId the record id.
     * @return all milestones by record sorted by date (desc).
     */
    public abstract List<T> listByRecord(final Long recordId);
}