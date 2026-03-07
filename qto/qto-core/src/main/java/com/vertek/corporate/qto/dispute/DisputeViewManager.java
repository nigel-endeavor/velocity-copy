package com.vertek.corporate.qto.dispute;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author fcurran
 * @since 9/18/2023
 */
@Stateless
public class DisputeViewManager extends StandardManager<DisputeView> {

    /**
     * Persistence tier for ServiceView.
     */
    @Inject
    private DisputeViewJpaDao dao;

    @Override
    protected DisputeViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<DisputeView> findBySearchCriteria(final DisputeViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    /**
     * Gets the meta data for the worklist built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the worklist.
     */
    public DisputeWorklistMeta getDisputeWorklistMeta(final DisputeViewSearchCriteria criteria) {
        return getDao().getDisputeWorklistMeta(criteria);
    }

    public List<String> findServiceTypes(){
        return getDao().findServiceTypes();
    }
}
