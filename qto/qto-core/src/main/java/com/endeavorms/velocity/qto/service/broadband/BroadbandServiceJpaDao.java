package com.endeavorms.velocity.qto.service.broadband;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.service.AbstractServiceJpaDao;

import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.service.broadband.QBroadbandService.broadbandService;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Component
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
