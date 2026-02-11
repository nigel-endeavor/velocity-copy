package com.endeavorms.velocity.qto.service._4g5g;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.service.AbstractServiceJpaDao;

import org.springframework.stereotype.Component;
import java.util.List;

import static com.endeavorms.velocity.qto.service._4g5g.QGService.gService;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@Component
public class GServiceJpaDao extends AbstractServiceJpaDao<GService> {

    /**
     * Returns a list of Service.
     *
     * @param locationId Location ID.
     * @return List if services.
     */
    public List<GService> findByLocationId(final Long locationId) {
        return new JPAQuery<GService>(entityManager)
                .from(gService)
                .where(gService.locationId.eq(locationId))
                .fetch();
    }
}
