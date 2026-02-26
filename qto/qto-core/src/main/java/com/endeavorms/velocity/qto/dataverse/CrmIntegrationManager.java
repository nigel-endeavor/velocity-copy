package com.endeavorms.velocity.qto.dataverse;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CrmIntegrationManager extends StandardManager<CrmIntegration> {

    @Autowired
    private CrmIntegrationJpaDao dao;

    @Override
    protected CrmIntegrationJpaDao getDao() {
        return dao;
    }
}
