package com.endeavorms.velocity.qto.invoicing.billableMilestone;
import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;


@Component
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
