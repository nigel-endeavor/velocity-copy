package com.vertek.corporate.qto.subject;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.SecurityUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.subject.QCompanySubject.companySubject;

public class CompanySubjectJpaDao extends AbstractJpaDao<CompanySubject, Long> {

    @Override
    @Inject
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
