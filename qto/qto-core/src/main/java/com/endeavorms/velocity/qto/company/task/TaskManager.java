package com.endeavorms.velocity.qto.company.task;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
