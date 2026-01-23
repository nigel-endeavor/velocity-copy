package com.vertek.corporate.qto.activation;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@Stateless
public class ActivationViewManager extends StandardManager<ActivationView> {

    /**
     * Persistence tier for ActivationView.
     */
    @Inject
    private ActivationViewJpaDao dao;

    @Override
    protected ActivationViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<ActivationView> findBySearchCriteria(final ActivationViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public ActivationWorklistMeta getWorklistMeta(final ActivationViewSearchCriteria criteria) {
        return getDao().getWorklistMeta(criteria);
    }
}
