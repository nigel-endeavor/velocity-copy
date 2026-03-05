package com.endeavorms.velocity.qto.custom.iss.parser;

import org.springframework.stereotype.Component;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;
import java.util.List;

@Component
public class InputRecord4Manager extends StandardManager<InputRecord4> {

    /**
     * Persistence tier for JeopNote.
     */
    @Inject
    private InputRecord4JpaDao dao;

    @Override
    protected InputRecord4JpaDao getDao() {
        return dao;
    }



    public List<InputRecord4> findByInputRecord1Id(final Long inputRecord1Id) {
        return dao.findByInputRecord1Id(inputRecord1Id);
    }
}
