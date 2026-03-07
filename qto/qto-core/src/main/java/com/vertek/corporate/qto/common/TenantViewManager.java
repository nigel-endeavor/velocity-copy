package com.vertek.corporate.qto.common;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.company.Company;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @since 5.20.0
 */
@Stateless
public class TenantViewManager extends StandardManager<TenantView> {

    /** Data access methods for TenantViews.*/
    @Inject
    private TenantViewJpaDao dao;

    @Override
    public TenantViewJpaDao getDao() {
        return dao;
    }

        /**
     * Returns all tenants
     */
    public List<TenantView> getAllTenants() {
        return dao.getAllTenants();
    }

        /**
     * Returns the tenant with the given id
     * * @param id
     * @return
     */
    public TenantView findById(Long id) {
        return dao.findById(id);
    }

    public TenantView findByName(String name) {
        return dao.findByName(name);
    }

}
