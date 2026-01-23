package com.vertek.corporate.qto.activation.attempt.emailView;

import com.vertek.corporate.qto.common.GenericDao;
import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author fcurran
 * @since 6/5/2023
 */
@Stateless
public class ActivationAttemptEmailViewManager extends StandardManager<ActivationAttemptEmailView> {

    /**
     * Persistence tier for ActivationAttemptEmailViews.
     */
    @Inject
    private ActivationAttemptEmailViewJpaDao dao;

    @Override
    protected GenericDao<ActivationAttemptEmailView, Long> getDao() {
        return dao;
    }
}
