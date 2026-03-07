package com.vertek.corporate.qto.dataverse;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.inject.Inject;

public class CrmIntegrationManager extends StandardManager<CrmIntegration> {

    @Inject
    private CrmIntegrationJpaDao dao;

    @Override
    protected CrmIntegrationJpaDao getDao() {
        return dao;
    }
}
