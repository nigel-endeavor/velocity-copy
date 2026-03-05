package com.endeavorms.velocity.qto.custom.iss.parser;

import org.springframework.stereotype.Component;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;

@Component
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
