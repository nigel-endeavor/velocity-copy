package com.vertek.corporate.qto.company;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 1/2/2024
 */
@Stateless
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
