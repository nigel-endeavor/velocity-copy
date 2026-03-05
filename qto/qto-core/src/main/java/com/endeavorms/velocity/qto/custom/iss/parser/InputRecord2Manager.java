package com.endeavorms.velocity.qto.custom.iss.parser;

import org.springframework.stereotype.Component;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;

@Component
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
