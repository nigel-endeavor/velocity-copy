package com.vertek.corporate.qto.subject;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
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
