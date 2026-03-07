package com.vertek.corporate.qto.invoicing.levelOfEffort;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 7/12/2023
 */
@Stateless
public class LevelOfEffortManager extends StandardManager<LevelOfEffort> {

    /** Persistence tier for LevelOfEffort. */
    @Inject
    private LevelOfEffortJpaDao dao;

    @Override
    protected LevelOfEffortJpaDao getDao() {
        return dao;
    }

    public List<LevelOfEffort> findByCompanyId(final Long companyId) {
        return dao.findByCompanyId(companyId);
    }


        /**
     * Returns the current level of effort for the given name and date.
     * @param loe the name of the level of effort.
     * @param tenantId the tenant id
     * @return the current level of effort.
     */
    public List<LevelOfEffort> findCurrentLoeByNameAndTenant(final String loe, final Long tenantId) {
        return dao.findCurrentLoeByName(loe, tenantId);
    }
}
