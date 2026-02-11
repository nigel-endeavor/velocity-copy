package com.endeavorms.velocity.qto.dataverse;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;

public class CrmIntegrationManager extends StandardManager<CrmIntegration> {

    @Inject
    private CrmIntegrationJpaDao dao;

    @Override
    protected CrmIntegrationJpaDao getDao() {
        return dao;
    }
}
