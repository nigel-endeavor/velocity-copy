package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.StandardManager;

/**
 * Base class for business logic pertaining to Milestone implementations.
 * @author rconnolly
 * @since 1.1.0
 * @param <T> a Milestone Type.
 */
public abstract class AbstractMilestoneManager<T extends Milestone> extends StandardManager<T> {

    @Override
    protected abstract AbstractMilestoneJpaDao<T> getDao();


    /**
     * Gets a Milestone by code.
     * @param code the Milestone's code.
     * @return a Milestone with the given code.
     */
    public Milestone retrieveByCode(final String code) {
        PreconditionsUtil.checkArgument(code, "A Milestone code is required");
        return getDao().retrieveByCode(code);
    }
}