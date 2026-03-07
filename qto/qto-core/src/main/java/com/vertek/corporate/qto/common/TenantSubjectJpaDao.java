package com.vertek.corporate.qto.common;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.subject.TenantSubject;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.subject.QTenantSubject.tenantSubject;

/**
 * Base class for TenantSubject DAOs.
 * @author mmeehan
 * @since 1.4 - 12/7/12 11:49 AM
 */
public class TenantSubjectJpaDao extends AbstractJpaDao<TenantSubject, Long> {

    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TenantSubjectJpaDao.class);

    @Inject
    @Override
    protected void setEntityManager(@PlatformDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Gets the currently selected Tenant.
     * Falls back to the first active tenant linked to the user if none is explicitly selected.
     * @return the currently selected Tenant, or null if user has no linked tenants.
     */
    public Tenant getCurrentTenant() {
        Tenant selectedTenant = (Tenant) ((JPAQuery) new JPAQuery(entityManager)
                .select(tenantSubject.tenant)
                .from(tenantSubject)
                .where(tenantSubject.subject.emailAddress.eq(SecurityUtils.getLoggedInUser())
                        .and(tenantSubject.isSelected.isTrue()).and(tenantSubject.tenant.active.isTrue())))
                .fetchOne();

        // Fallback: if no explicitly-selected tenant, return the first active tenant for this user
        if (selectedTenant == null) {
            LOGGER.debug("No selected tenant found for user '{}'; falling back to first active tenant.",
                    SecurityUtils.getLoggedInUser());
            selectedTenant = (Tenant) ((JPAQuery) new JPAQuery(entityManager)
                    .select(tenantSubject.tenant)
                    .from(tenantSubject)
                    .where(tenantSubject.subject.emailAddress.eq(SecurityUtils.getLoggedInUser())
                            .and(tenantSubject.tenant.active.isTrue()))
                    .limit(1))
                    .fetchFirst();
        }

        return selectedTenant;
    }

    public void updateSelectedTenant(final Long subjectId, final Long tenantId) {
        this.entityManager.createNativeQuery("UPDATE tenant_subject SET tenant_id = :tenantId WHERE subject_id = :subjectId")
                .setParameter("tenantId", tenantId)
                .setParameter("subjectId", subjectId)
                .executeUpdate();
    }

    /**
     * Get a list of tenant id's that the current user is allowed to see.
     * @return a List of Longs.
     */
    public List<Long> getAllowedTenantIds() {

        // todo: we probably should be checking the active flag here too.
        String username = SecurityUtils.getLoggedInUser();

        JPAQuery query = (JPAQuery) new JPAQuery(entityManager)
                .select(tenantSubject.tenant.id)
                .from(tenantSubject)
                .where(tenantSubject.subject.emailAddress.toLowerCase().eq(username));

        List<Long> allowedIds = query.fetch();

        LOGGER.trace("allowedIds = {}", allowedIds);

        return allowedIds;
    }
}
