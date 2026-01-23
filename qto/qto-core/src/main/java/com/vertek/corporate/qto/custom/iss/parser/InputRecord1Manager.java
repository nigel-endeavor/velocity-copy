package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardManager;

import javax.inject.Inject;
import java.util.List;

public class InputRecord1Manager extends StandardManager<InputRecord1> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private InputRecord1JpaDao dao;

    @Override
    protected InputRecord1JpaDao getDao() {
        return dao;
    }

    public InputRecord1 findByTicket(final String ticketNumber) {
        return dao.findByTicket(ticketNumber);
    }

}
