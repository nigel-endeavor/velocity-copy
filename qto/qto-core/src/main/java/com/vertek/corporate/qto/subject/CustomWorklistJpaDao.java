package com.vertek.corporate.qto.subject;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.Tenant;
import com.vertek.corporate.qto.common.TenantSubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.subject.QCustomWorklist.customWorklist;

@Stateless
public class CustomWorklistJpaDao extends AbstractJpaDao<CustomWorklist, Long> {

    @Inject
    private SubjectManager subjectManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CustomWorklist> findByLoggedInUser(final String worklistName) {
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        return new JPAQuery<CustomWorklist>(entityManager)
                .from(customWorklist)
                .where((customWorklist.authorId.eq(subject.getId())
                        .or(customWorklist.shared.isTrue()))
                        .and(customWorklist.worklistName.eq(worklistName)))
                .orderBy(customWorklist.name.asc())
                .fetch();
    }

    public List<CustomWorklist> findByTenantId(final Long tenantId) {
        return new JPAQuery<CustomWorklist>(entityManager)
                .from(customWorklist)
                .where(customWorklist.tenantId.eq(tenantId))
                .fetch();
    }

    public List<CustomWorklist> findSharedOrOwned(final String worklistName) {
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        Tenant tenant = tenantSubjectManager.getCurrentTenant();
        return new JPAQuery<CustomWorklist>(entityManager)
                .from(customWorklist)
                .where((customWorklist.authorId.eq(subject.getId())
                        .or(customWorklist.shared.isTrue()))
                        .and(customWorklist.worklistName.eq(worklistName))
                        .and(customWorklist.tenantId.eq(tenant.getId())))
                .orderBy(customWorklist.name.asc())
                .fetch();
    }
}
