package com.vertek.corporate.qto.attachment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.attachment.QServiceFileAttachment.serviceFileAttachment;
import static com.vertek.corporate.qto.service.QService.service;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Stateless
public class ServiceFileAttachmentJpaDao extends AbstractMasterCustomerJpaDao<ServiceFileAttachment> {
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
     * Retrieves all ServiceFileAttachments with a given service id.
     * @param serviceId service id.
     * @return matching ServiceFileAttachments.
     */
    public PaginatedResult<ServiceFileAttachment> findByServiceIdPaginated(final Long serviceId) {
        BooleanExpression expression = serviceFileAttachment.serviceId.eq(serviceId);
        expression = addMasterCustomerAndTenantFilter(expression, serviceFileAttachment.masterCustomerId, serviceFileAttachment.tenantId, true);

        return new PaginatedResult<>(new JPAQuery<ServiceFileAttachment>(entityManager)
                .from(serviceFileAttachment)
                .where(expression)
                .fetchResults());
    }

    /**
     * Retrieves all ServiceFileAttachments with a given service id.
     * @param serviceId service id.
     * @return matching ServiceFileAttachments.
     */
    public List<ServiceFileAttachment> findByServiceId(final Long serviceId) {
        BooleanExpression expression = serviceFileAttachment.serviceId.eq(serviceId);
        expression = addMasterCustomerAndTenantFilter(expression, serviceFileAttachment.masterCustomerId, serviceFileAttachment.tenantId, true);

        return new JPAQuery<ServiceFileAttachment>(entityManager)
                .from(serviceFileAttachment)
                .where(expression)
                .fetch();
    }

    /**
     * Retrieves all ServiceFileAttachments for services associated with a given location id.
     * @param locationId location id.
     * @return matching ServiceFileAttachments.
     */
    public PaginatedResult<ServiceFileAttachment> findByLocationId(final Long locationId) {
        BooleanExpression expression = serviceFileAttachment.serviceId.in(
                JPAExpressions.select(service.id)
                        .from(service)
                        .where(service.locationId.eq(locationId))
        );
        expression = addMasterCustomerAndTenantFilter(expression, serviceFileAttachment.masterCustomerId, serviceFileAttachment.tenantId, true);

        return new PaginatedResult<>(new JPAQuery<ServiceFileAttachment>(entityManager)
                .from(serviceFileAttachment)
                .where(expression)
                .fetchResults());
    }

    /**
     * Retrieves a ServiceFileAttachment by its inventory location or service id.
     * @param parentOwnerId parent attachment's location or service id.
     * @param parentFileAttachmentId attachment parent id.
     * @return matching ServiceFileAttachment.
     */
    public ServiceFileAttachment findByInventoryRecordId(Long parentOwnerId, Long parentFileAttachmentId) {
        return new JPAQuery<ServiceFileAttachment>(entityManager)
                .from(serviceFileAttachment)
                .where(serviceFileAttachment.id.eq(parentFileAttachmentId)
                        .and(serviceFileAttachment.serviceId.eq(parentOwnerId)))
                .fetchOne();
    }

    public ServiceFileAttachment findByParentId(Long id) {
        return new JPAQuery<ServiceFileAttachment>(entityManager)
                .from(serviceFileAttachment)
                .where(serviceFileAttachment.parentFileAttachmentId.eq(id)).fetchOne();
    }
}
