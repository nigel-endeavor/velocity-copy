package com.vertek.corporate.qto.customfield.field;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class CustomFieldManager extends StandardManager<CustomField> {

    @Inject
    private CustomFieldJpaDao customFieldJpaDao;

    @Override
    protected CustomFieldJpaDao getDao() {
        return customFieldJpaDao;
    }

    public List<CustomField> findActiveByTab(final CustomFieldTabValue tab) {
        return customFieldJpaDao.findActiveByTab(tab);
    }

    public CustomField findByNameAndTenant(final String name, final Long tenantId) {
        return customFieldJpaDao.findByNameAndTenant(name, tenantId);
    }

}
