package com.vertek.corporate.qto.jeop;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author llevit
 */
@Stateless
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
