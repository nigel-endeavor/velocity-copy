package com.endeavorms.velocity.qto.subject;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.SecurityUtils;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.subject.QSubjectCustomWorklist.subjectCustomWorklist;
import static com.endeavorms.velocity.qto.subject.QCustomWorklist.customWorklist;

@Component
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
