package com.vertek.corporate.qto.company.task;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.inject.Inject;
import java.util.List;

public class TaskManager extends StandardManager<Task> {
    @Inject
    private TaskJpaDao dao;

    @Override
    protected TaskJpaDao getDao() {
        return dao;
    }

    public List<Task> findByLookupValueId(final Long lookupValueId) {
        return dao.findByLookupValueId(lookupValueId);
    }
}
