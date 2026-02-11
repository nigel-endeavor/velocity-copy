package com.endeavorms.velocity.qto.service.historyview;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.service.historyview.QServiceHistoryView.serviceHistoryView;

/**
 * @author rcasey
 * @since 10/27/2023
 */
@Component
public class ServiceHistoryViewJpaDao extends AbstractMasterCustomerJpaDao<ServiceHistoryView> {

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
     * Gets the root (top level parent id) of the service tree for the given service.
     * @param serviceId serviceId
     * @return root service id
     */
    public Long getServiceTreeRoot(final Long serviceId) {
        return ((Number) entityManager.createNativeQuery("CALL GetServiceTreeRoot(?)")
                .setParameter(1, serviceId)
                .getSingleResult()).longValue();
    }


    public List<ServiceHistoryView> findByParentServiceId(final Long parentServiceId) {
        return new JPAQuery<ServiceHistoryView>(entityManager)
                .from(serviceHistoryView)
                .where(serviceHistoryView.parentServiceId.eq(parentServiceId))
                .fetch();
    }
}
