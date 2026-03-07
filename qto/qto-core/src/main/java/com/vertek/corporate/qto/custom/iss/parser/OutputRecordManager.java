package com.vertek.corporate.qto.custom.iss.parser;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.inject.Inject;
import java.util.List;

public class OutputRecordManager extends StandardManager<OutputRecord> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private OutputRecordJpaDao dao;

    @Override
    protected OutputRecordJpaDao getDao() {
        return dao;
    }


    public List<OutputRecord> findAll(final Long tenantId) {
        return dao.findAll(tenantId);
    }
}
