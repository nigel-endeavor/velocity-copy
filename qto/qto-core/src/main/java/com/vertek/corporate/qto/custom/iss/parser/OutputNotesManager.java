package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.inject.Inject;
import java.util.List;

public class OutputNotesManager extends StandardManager<OutputNotes> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private OutPutNotesJpaDao dao;

    @Override
    protected OutPutNotesJpaDao getDao() {
        return dao;
    }



    public List<OutputNotes> findAll(final Long tenantId) {
        return dao.findAll(tenantId);
    }
}
