package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.inject.Inject;

public class InputRecord3Manager extends StandardManager<InputRecord3> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private InputRecord3JpaDao dao;

    @Override
    protected InputRecord3JpaDao getDao() {
        return dao;
    }



    public InputRecord3 findByInputRecord1Id(final Long inputRecord1Id) {
        return dao.findByInputRecord1Id(inputRecord1Id);
    }
}
