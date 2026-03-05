package com.endeavorms.velocity.qto.invoicing.billableMilestone;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMultitenantJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeType;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.invoicing.billableMilestone.QBillableMilestone.billableMilestone;

/**
 * @author mwelicka
 * @since 7/30/2023
 */

@Repository
public class BillableMilestoneJpaDao extends AbstractMultitenantJpaDao<BillableMilestone, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }

    /**
     * Find Billable Milestones for a given tenant.
     * @param tenantId tenant ID.
     * @return List<BillableMilestone>.
     */
    public List<BillableMilestone> findByTenantId(Long tenantId) {
        return new JPAQuery<BillableMilestone>(entityManager)
                .from(billableMilestone)
                        .where(billableMilestone.tenantId.eq(tenantId)).fetch();
    }

}

