package com.endeavorms.velocity.qto.common;

import com.endeavorms.velocity.qto.subject.TenantSubject;
import jakarta.persistence.EntityManager;

import java.util.Collections;
import java.util.List;

/**
 * Stub TenantSubjectJpaDao for tests when database is disabled.
 * Returns empty/safe defaults for all DAO methods.
 */
public class StubTenantSubjectJpaDao extends TenantSubjectJpaDao {

    public StubTenantSubjectJpaDao(EntityManager entityManager) {
        setEntityManager(entityManager);
    }

    @Override
    public Tenant getCurrentTenant() {
        return null;
    }

    @Override
    public void updateSelectedTenant(Long subjectId, Long tenantId) {
        // no-op
    }

    @Override
    public List<Long> getAllowedTenantIds() {
        return Collections.emptyList();
    }
}
