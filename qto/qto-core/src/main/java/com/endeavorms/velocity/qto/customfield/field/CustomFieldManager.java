package com.endeavorms.velocity.qto.customfield.field;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
