package com.endeavorms.velocity.qto.company;

import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 1/2/2024
 */
@Component
public class CompanyViewManager extends StandardManager<CompanyView> {

    @Inject
    private CompanyViewJpaDao dao;

    @Override
    protected CompanyViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<CompanyView> findBySearchCriteria(final CompanyViewSearchCriteria criteria) {
        return dao.findBySearchCriteria(criteria);
    }

    public MasterCustomerWorklistMeta getMasterCustomerWorklistMeta(final CompanyViewSearchCriteria criteria) {
        return getDao().getMasterCustomerWorklistMeta(criteria);
    }
}
