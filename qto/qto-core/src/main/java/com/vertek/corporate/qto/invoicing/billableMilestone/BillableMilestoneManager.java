package com.vertek.corporate.qto.invoicing.billableMilestone;
import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;


@Stateless
public class BillableMilestoneManager extends StandardManager<BillableMilestone> {

    @Inject
    private BillableMilestoneJpaDao dao;

    @Override
    protected BillableMilestoneJpaDao getDao() {
        return dao;
    }


    /**
     * Find Billable Milestones for a given tenant.
     * @param tenantId tenant ID.
     * @return List<BillableMilestone>.
     */
    public List<BillableMilestone> findByTenantId(Long tenantId) {
        return dao.findByTenantId(tenantId);
    }
}
