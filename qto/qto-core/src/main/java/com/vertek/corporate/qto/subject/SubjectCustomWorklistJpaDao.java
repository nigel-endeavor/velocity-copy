package com.vertek.corporate.qto.subject;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.SecurityUtils;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.subject.QSubjectCustomWorklist.subjectCustomWorklist;
import static com.vertek.corporate.qto.subject.QCustomWorklist.customWorklist;

@Stateless
public class SubjectCustomWorklistJpaDao extends AbstractJpaDao<SubjectCustomWorklist, Long> {

    @Inject
    private SubjectManager subjectManager;

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<SubjectCustomWorklist> findByLoggedInUser(final String worklistName) {
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        return new JPAQuery<SubjectCustomWorklist>(entityManager)
                .from(subjectCustomWorklist).rightJoin(customWorklist).on(subjectCustomWorklist.customWorklistId.eq(customWorklist.id))
                .where(subjectCustomWorklist.subjectId.eq(subject.getId())
                        .and(customWorklist.worklistName.eq(worklistName)))
                .orderBy(customWorklist.name.asc())
                .fetch();
    }

    public List<SubjectCustomWorklist> findByNameAndTenant(String name, Long tenantId) {
        return new JPAQuery<SubjectCustomWorklist>(entityManager)
                .from(subjectCustomWorklist).rightJoin(customWorklist).on(subjectCustomWorklist.customWorklistId.eq(customWorklist.id))
                .where(customWorklist.name.eq(name)
                        .and(customWorklist.tenantId.eq(tenantId)))
                .fetch();
    }

    public List<SubjectCustomWorklist> findByCustomWorklistId(Long id) {
        return new JPAQuery<SubjectCustomWorklist>(entityManager)
                .from(subjectCustomWorklist)
                .where(subjectCustomWorklist.customWorklistId.eq(id))
                .fetch();
    }
}
