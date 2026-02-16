package com.endeavorms.velocity.qto.subject;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.SecurityUtils;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.subject.QCompanySubject.companySubject;

@Component
public class CompanySubjectJpaDao extends AbstractJpaDao<CompanySubject, Long> {

    @Override
    @Autowired
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets the list of company id's that the current user is allowed to see.
     * @return a List of Longs.
     */
    public List<Long> getAllowedMasterCustomerIds() {
        String username = SecurityUtils.getLoggedInUser();
        return new JPAQuery<List<Long>>(entityManager)
                .select(companySubject.company.id)
                .from(companySubject)
                .where(companySubject.subject.emailAddress.toLowerCase().eq(username))
                .fetch();
    }

    /**
     * Gets the list of tenant id's that the current users assigned MC's belong to.
     * @return
     */
    public List<Long> getAllowedMasterCustomerTenantIds() {
        String username = SecurityUtils.getLoggedInUser();
        return new JPAQuery<List<Long>>(entityManager)
                .select(companySubject.company.tenantId)
                .from(companySubject)
                .where(companySubject.subject.emailAddress.toLowerCase().eq(username))
                .fetch();
    }

}
