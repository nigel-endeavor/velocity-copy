package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardManager;

import javax.inject.Inject;

public class InputRecord2Manager extends StandardManager<InputRecord2> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private InputRecord2JpaDao dao;

    @Override
    protected InputRecord2JpaDao getDao() {
        return dao;
    }


    public InputRecord2 findByInputRecord1Id(final Long inputRecord1Id) {
        return dao.findByInputRecord1Id(inputRecord1Id);
    }
}
