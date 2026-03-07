package com.vertek.corporate.qto.milestone;

import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.interval.IntervalInstance;
import com.vertek.corporate.qto.interval.IntervalInstanceManager;

import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Base business logic for MilestoneInstances.
 * @param <T> type derived from MilestoneInstance.
 * @author mmaloney
 */
public abstract class AbstractMilestoneInstanceManager<T extends MilestoneInstance> extends StandardManager<T> {

    @Inject
    private IntervalInstanceManager intervalInstanceManager;

    /**
     * Implementations must supply a DAO for their Entity Type.
     * @return a GenericDao implementation.
     */
    protected abstract AbstractMilestoneInstanceJpaDao<T> getDao();

    /**
     * Implementations must supply a DAO for their Entity Type.
     * @return a GenericDao implementation.
     */
    protected abstract MilestoneManager getMilestoneManager();

    /**
     * Implementations handle milestone events.
     * @param milestoneInstance the MilestoneInstance.
     */
    protected abstract void handleMilestoneEvent(final T milestoneInstance);

    /**
     * This method is to be used when you don't want to trigger the event handler logic.
     * @param recordId Id of record to relate milestone instance to.
     * @param milestoneCode Milestone Code.
     * @param milestoneDate Milestone Date.
     * @return The created milestone.
     */
    public T createNoEventHandler(final Long recordId, final String milestoneCode, final Date milestoneDate) {
        Milestone milestone = getMilestoneManager().retrieveByCode(milestoneCode);

        // Finds if an instance already exists for the given milestone code
        List<T> latestMilestones = listMaxMilestoneInstances(recordId);
        T existingInstance = null;
        for (T milestoneInstance : latestMilestones) {
            if (milestoneInstance.getMilestone().getCode().equals(milestoneCode)) {
                existingInstance = milestoneInstance;
                break;
            }
        }

        if (existingInstance != null) {
            throw new UnsupportedOperationException(milestoneCode + " is not adjustable.");
        }

        T milestoneInstance = newMilestoneInstance(recordId);
        milestoneInstance.setCount(0);
        milestoneInstance.setHistoric(false);
        milestoneInstance.setMilestoneDate(milestoneDate);
        milestoneInstance.setMilestone(milestone);
        milestoneInstance = super.create(milestoneInstance);

        return milestoneInstance;
    }

    @Override
    public T create(final T entity) {
        // lookup milestone based on code
        if (entity.getMilestone().getId() == null) {
            Milestone milestone = getMilestoneManager().retrieveByCode(entity.getMilestone().getCode());
            entity.setMilestone(milestone);
        }
        entity.setCount(0);
        MilestoneInstance milestoneInstance = super.create(entity);
        handleMilestoneEvent((T) milestoneInstance);
        return (T) milestoneInstance;
    }

    /**
     * This method is to be used when you don't want to trigger the event handler logic.
     * @param entity milestone instance.
     * @return The new instance.
     */
    public T editNoEventHandler(final T entity) {
        T existing = retrieve(entity.getId());
        MilestoneInstance milestoneInstance = entity;
        if (entity.getMilestoneDate() == null) {
            //kludge to allow for null milestone date until we later remove it explicitly
            entity.setMilestoneDate(new Date());
            List<IntervalInstance> intervalInstances = intervalInstanceManager.findByMilestoneInstanceId(entity.getId());
            for (IntervalInstance intervalInstance : intervalInstances) {
                if (entity.getId().equals(intervalInstance.getOpenMilestoneInstanceId())) {
                    intervalInstanceManager.remove(intervalInstance.getId());
                } else if (entity.getId().equals(intervalInstance.getCloseMilestoneInstanceId())) {
                    intervalInstanceManager.reopenIntervalInstance(intervalInstance);
                }
            }
            remove(existing.getId());
            //end kludge
            entity.setMilestoneDate(null);
        } else {
            milestoneInstance = super.edit(entity);
        }
        return (T) milestoneInstance;
    }

    @Override
    public T edit(final T entity) {
        T existing = retrieve(entity.getId());
        MilestoneInstance milestoneInstance = entity;
        if (entity.getMilestoneDate() == null) {
            //kludge to allow for null milestone date until we later remove it explicitly
            entity.setMilestoneDate(new Date());
            List<IntervalInstance> intervalInstances = intervalInstanceManager.findByMilestoneInstanceId(entity.getId());
            for (IntervalInstance intervalInstance : intervalInstances) {
                if (entity.getId().equals(intervalInstance.getOpenMilestoneInstanceId())) {
                    intervalInstanceManager.remove(intervalInstance.getId());
                } else if (entity.getId().equals(intervalInstance.getCloseMilestoneInstanceId())) {
                    intervalInstanceManager.reopenIntervalInstance(intervalInstance);
                }
            }
            remove(existing.getId());
            //end kludge
            entity.setMilestoneDate(null);
        } else {
            milestoneInstance = super.edit(entity);
        }
        handleMilestoneEvent((T) milestoneInstance);
        return (T) milestoneInstance;
    }

    /**
     * Gets the milestones with the greatest date related to a record (Order, Site, etc.).
     * @param recordId the record for which to list milestones.
     * @return the milestone instances with the most greatest date.
     */
    public List<T> listMaxMilestoneInstances(final Long recordId) {
        List<T> maxMilestoneInstances = Lists.newArrayList();
        List<T> milestoneInstances = getDao().listByRecord(recordId);
        List<String> milestoneCodes = Lists.newArrayList();
        for (T milestoneInstance : milestoneInstances) {
            String milestoneName = milestoneInstance.getMilestone().getCode();
            if (!milestoneCodes.contains(milestoneName)) {
                maxMilestoneInstances.add(milestoneInstance);
                milestoneCodes.add(milestoneName);
            }
        }
        return maxMilestoneInstances;
    }

    /**
     * Adds a new instance of the specified milestone type.
     * If milestone instance already exists for record and is not adjustable, this method will fail.
     * @param recordId Id of record to relate milestone instance to.
     * @param milestoneCode Milestone Code.
     * @param milestoneDate Milestone Date.
     * @return The created milestone.
     */
    public T create(final Long recordId, final String milestoneCode, final Date milestoneDate) {
        Milestone milestone = getMilestoneManager().retrieveByCode(milestoneCode);

        // Finds if an instance already exists for the given milestone code
        List<T> latestMilestones = listMaxMilestoneInstances(recordId);
        T existingInstance = null;
        for (T milestoneInstance : latestMilestones) {
            if (milestoneInstance.getMilestone().getCode().equals(milestoneCode)) {
                existingInstance = milestoneInstance;
                break;
            }
        }

        if (existingInstance != null) {
            throw new UnsupportedOperationException(milestoneCode + " is not adjustable.");
        }

        T milestoneInstance = newMilestoneInstance(recordId);
        milestoneInstance.setCount(0);
        milestoneInstance.setHistoric(false);
        milestoneInstance.setMilestoneDate(milestoneDate);
        milestoneInstance.setMilestone(milestone);
        milestoneInstance = super.create(milestoneInstance);

        handleMilestoneEvent((T) milestoneInstance);

        return milestoneInstance;
    }

    /**
     * Creates a new instance of T with the specified record id.
     * @param recordId The record id (order id / site id / schedule id) to set on the new instance.
     * @return The new instance.
     */
    protected abstract T newMilestoneInstance(final Long recordId);

    /**
     * Sets the record id on a concrete instance of T.
     * @param milestoneInstance The instance of T to set the record id (order id / site id / schedule id) on.
     * @param recordId The record id to set.
     */
    protected abstract void setRecordId(final T milestoneInstance, final Long recordId);


    public T editInvoiceId(final T entity) {
        return super.edit(entity);
    }
}
