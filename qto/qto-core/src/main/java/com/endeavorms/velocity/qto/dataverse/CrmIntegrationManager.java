package com.endeavorms.velocity.qto.dataverse;

import org.springframework.stereotype.Component;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;

@Component
public class CrmIntegrationManager extends StandardManager<CrmIntegration> {

    @Inject
    private CrmIntegrationJpaDao dao;

    @Override
    protected CrmIntegrationJpaDao getDao() {
        return dao;
    }
}
