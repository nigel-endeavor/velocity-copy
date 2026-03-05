package com.endeavorms.velocity.qto.custom.iss.parser;

import org.springframework.stereotype.Component;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;
import java.util.List;

@Component
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
