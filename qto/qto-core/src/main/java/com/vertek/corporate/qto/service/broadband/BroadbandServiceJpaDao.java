package com.vertek.corporate.qto.service.broadband;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.service.AbstractServiceJpaDao;

import jakarta.ejb.Stateless;

import static com.vertek.corporate.qto.service.broadband.QBroadbandService.broadbandService;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Stateless
public class BroadbandServiceJpaDao extends AbstractServiceJpaDao<BroadbandService> {


    /**
     * Returns a Service.
     *
     * @param serviceId Service ID.
     * @param tenantId  Tenant ID.
     * @return Activation Scheule Object.
     */
    public BroadbandService findByIdAndTenant(final Long serviceId, final Long tenantId) {
        return new JPAQuery<BroadbandService>(entityManager)
                .from(broadbandService)
                .where(broadbandService.id.eq(serviceId)
                        .and(broadbandService.tenantId.eq(tenantId)))
                .fetchOne();
    }
}
