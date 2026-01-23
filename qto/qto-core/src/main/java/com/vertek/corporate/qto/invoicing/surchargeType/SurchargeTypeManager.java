package com.vertek.corporate.qto.invoicing.surchargeType;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.company.CompanyManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Business logic layer for Surcharge Types.
 */
@Stateless
public class SurchargeTypeManager extends StandardManager<SurchargeType> {
    /** Business logic layer for companies. */
    @Inject
    private CompanyManager companyManager;
    /** Persistence layer for Surcharges. */
    @Inject
    private SurchargeTypeJpaDao dao;
    @Override
    protected SurchargeTypeJpaDao getDao() {
        return dao;
    }

    /**
     * Find SurchargeTypes by search criteria.
     * @param criteria search criteria.
     * @return PaginatedResult<SurchargeType>.
     */
    public PaginatedResult<SurchargeType> findBySearchCriteria(final SurchargeTypeSearchCriteria criteria) {
        if (criteria.getCompanyId() != null) {
            criteria.setTenantId(companyManager.retrieve(criteria.getCompanyId()).getTenantId());
        }
        return dao.findBySearchCriteria(criteria);
    }

    /**
     * Find a SurchargeType by type and tenantId.
     * @param type surcharge type.
     * @param tenantId tenant id.
     * @return SurchargeType.
     */
    public SurchargeType findByType(final String type,
                                    final Long tenantId) {
        return dao.findByType(type, tenantId);
    }
}
