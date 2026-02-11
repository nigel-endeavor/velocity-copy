package com.endeavorms.velocity.qto.subject;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class CustomWorklistManager extends StandardManager<CustomWorklist> {

    @Inject
    private CustomWorklistJpaDao dao;

    @Override
    protected CustomWorklistJpaDao getDao() {
        return dao;
    }

    public List<CustomWorklist> findByTenantId(Long tenantId) {
        return dao.findByTenantId(tenantId);
    }

    public List<CustomWorklist> findSharedOrOwned(final String worklistName) {
        return dao.findSharedOrOwned(worklistName);
    }
}
