package com.endeavorms.velocity.qto.jeop;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * @author llevit
 */
@Component
public class JeopManager extends StandardManager<Jeop> {

    /**
     * Persistence tier for LocationJeop.
     */
    @Inject
    private JeopJpaDao dao;

    @Override
    protected JeopJpaDao getDao() {
        return dao;
    }
}
