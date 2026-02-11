package com.endeavorms.velocity.qto.activation.attempt.emailView;

import com.endeavorms.velocity.qto.common.GenericDao;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * @author fcurran
 * @since 6/5/2023
 */
@Component
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
