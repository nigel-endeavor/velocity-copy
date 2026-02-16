package com.endeavorms.velocity.qto.activation;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@Component
public class ActivationViewManager extends StandardManager<ActivationView> {

    /**
     * Persistence tier for ActivationView.
     */
    @Autowired
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
