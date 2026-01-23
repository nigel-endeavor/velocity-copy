package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardManager;

import javax.inject.Inject;

public class InputRecord5Manager extends StandardManager<InputRecord5> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private InputRecord5JpaDao dao;

    @Override
    protected InputRecord5JpaDao getDao() {
        return dao;
    }

    public InputRecord5 findByInputRecord1Id(final Long inputRecord1Id) {
        return dao.findByInputRecord1Id(inputRecord1Id);
    }
}
