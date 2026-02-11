package com.endeavorms.velocity.qto.subject;

import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;

import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanySubjectManager extends StandardManager<CompanySubject> {

    @Inject
    private CompanySubjectJpaDao dao;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Override
    protected CompanySubjectJpaDao getDao() {
        return dao;
    }

    /**
     * Verifies that the current user is allowed to see the given company.
     * @param companyId the company id to verify.
     * @return true if the user is allowed to see the company, false otherwise.
     */
    public boolean verifyCompany(final Long companyId) {
        Company company = companyManager.retrieve(companyId);
        List<Long> allowedMCIds = getAllowedMasterCustomerIds();
        return allowedMCIds.contains(company.getId())
                || allowedMCIds.contains(company.getMasterCustomerId())
                || tenantSubjectManager.getAllowedTenantIds().contains(company.getTenantId());
    }

    /**
     * Gets the list of company id's that the current user is allowed to see.
     * @return a List of Longs.
     */
    public List<Long> getAllowedMasterCustomerIds() {
        return getDao().getAllowedMasterCustomerIds();
    }

    /**
     * Gets the list of tenant id's that the current users assigned MC's belong to.
     * @return
     */
    public List<Long> getAllowedMasterCustomerTenantIds() {
        return getDao().getAllowedMasterCustomerTenantIds();
    }

}
