package com.endeavorms.velocity.qto.custom.iss.parser;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;
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
