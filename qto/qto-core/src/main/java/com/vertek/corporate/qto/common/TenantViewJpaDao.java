package com.vertek.corporate.qto.common;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.common.QTenantView.tenantView;

@Stateless
public class TenantViewJpaDao extends AbstractJpaDao<TenantView, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Returns all tenants
     */
    public List<TenantView> getAllTenants() {
        return new JPAQuery<TenantView>(entityManager).from(tenantView).fetch();
    }

    /**
     * Returns the tenant with the given id
     * * @param id
     * @return
     */
    public TenantView findById(Long id) {
        return new JPAQuery<TenantView>(entityManager)
                .from(tenantView)
                .where(tenantView.id.eq(id))
                .fetchOne();
    }

    public TenantView findByName(String name) {
        return new JPAQuery<TenantView>(entityManager)
                .from(tenantView)
                .where(tenantView.name.eq(name))
                .fetchOne();
    }
}
