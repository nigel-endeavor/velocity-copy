package com.endeavorms.velocity.qto.activation.attempt.emailView;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


/**
 * @author fcurran
 * @since 6/5/2023
 */
@Component
public class ActivationAttemptEmailViewJpaDao extends AbstractMultitenantJpaDao<ActivationAttemptEmailView, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }
}
