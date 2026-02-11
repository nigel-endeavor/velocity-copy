package com.endeavorms.velocity.qto.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.endeavorms.velocity.qto.service.QService.service;

/**
 * @author rcasey
 * @since 1/6/2023
 */
@Component
public class ServiceJpaDao extends AbstractMasterCustomerJpaDao<Service> {
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
     * Retrieves all services matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching services.
     */
    public PaginatedResult<Service> findBySearchCriteria(final ServiceSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<Service>(entityManager).from(service)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given ServiceSearchCriteria.
     * @param criteria the criteria to filter by.
     * @return relevant criteria.
     */
    public Predicate getExpression(final ServiceSearchCriteria criteria) {
        BooleanExpression expression = service.id.isNotNull();
//        addTenantFilter(expression, service.tenantId, true);

        if (criteria.getLocationId() != null) {
            expression = expression.and(service.locationId.eq(criteria.getLocationId()));
        }

        return expression;
    }

    /**
     * Get list of services for a location.
     * @param locationId locationId.
     * @return services.
     */
    public List<Service> findByLocationId(final Long locationId) {
      return new JPAQuery<Service>(entityManager)
              .from(service)
              .where(service.locationId.eq(locationId))
              .fetch();

  }

    /**
     * Returns a Service.
     *
     * @param serviceId Service ID.
     * @param tenantId  Tenant ID.
     * @return Activation Scheule Object.
     */
    public Service findByIdAndTenant(final Long serviceId, final Long tenantId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.id.eq(serviceId)
                        .and(service.tenantId.eq(tenantId)))
                .fetchOne();
    }

    /**
     * Returns a List of Services.
     * @param serviceIds service IDs.
     * @param tenantId tenantId
     * @return List of Services.
     */
    public List<Service> findByIdsAndTenant(final List<Long> serviceIds, final Long tenantId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.id.in(serviceIds)
                        .and(service.tenantId.eq(tenantId)))
                .fetch();
    }

     /**
     * Returns a Service.
     *
     * @param ticketNumber Ticket ID.
     * @param tenantId  Tenant ID.
     * @return Activation Scheule Object.
     */
    public List<Service> findByTicketNumberAndTenant(final String ticketNumber, final Long tenantId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.ticketNumber.eq(ticketNumber)
                        .and(service.tenantId.eq(tenantId)))
                .fetch();
    }

    /**
     * Returns a Service.
     *
     * @param clientServiceId Client Service ID.
     * @param tenantId  Tenant ID.
     * @return a Service
     */
    public List<Service> findByClientServiceIdAndTenant(final String clientServiceId, final Long tenantId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.clientServiceId.eq(clientServiceId)
                        .and(service.tenantId.eq(tenantId)))
                .fetch();
    }

     /**
     * Returns a Service.
     *
     * @param clientServiceId Client Service ID.
     * @param locationId  Location ID.
     * @return a Service
     */
    public Service findByClientServiceIdAndLocationId(final String clientServiceId, final Long locationId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.clientServiceId.eq(clientServiceId)
                        .and(service.locationId.eq(locationId)))
                .fetchOne();
    }

    /**
     * Get a disconnect service.
     * @param parentId parentId.
     * @return service.
     */
    public Service findDisconnectByParentId(final Long parentId) {
      return new JPAQuery<Service>(entityManager)
              .from(service)
              .where(service.parentServiceId.eq(parentId)
                      .and(service.orderType.eq(OrderType.DISCONNECT.getOrderType()))
                      .and(service.status.notIn("Service Cancelled", "Change In Assignment", "Disconnect Cancelled")))
              .fetchOne();
    }


    public Service findLeafServiceForService(final Long id) {
        //for the given service, find the leaf service
        //if the service is a leaf service, return it
        //create native query that can get the leaf service by only using service parent_ids
        Query query = entityManager.createNativeQuery("WITH RECURSIVE ChildHierarchy AS (" +
                "   SELECT service_id, parent_service_id " +
                "   FROM service " +
                "   WHERE service_id = :serviceId " +
                "   UNION ALL " +
                "   SELECT s.service_id, s.parent_service_id " +
                "   FROM service s " +
                "   JOIN ChildHierarchy c ON c.service_id = s.parent_service_id" +
                ") " +
                "SELECT service_id " +
                "FROM ChildHierarchy " +
                "WHERE NOT EXISTS ( " +
                "        SELECT 1 " +
                "        FROM service " +
                "        WHERE parent_service_id = ChildHierarchy.service_id " +
                ");");
        query.setParameter("serviceId", id);
        Long serviceId = Long.valueOf(query.getSingleResult().toString());
        return retrieve(serviceId);
    }

    /**
     * Finds all services that are children of the given parent id.
     * @param parentId the parent id.
     * @return the list of services.
     */
    public List<Service> findByParentId(final Long parentId) {
        return new JPAQuery<Service>(entityManager).from(service).where(service.parentServiceId.eq(parentId)).fetch();
    }

    /**
     * Finds all services that are children of the given associated parent id.
     * @param parentId the parent id.
     * @return the list of services.
     */
    public List<Service> findAssociatedByParentId(final Long parentId) {
        return new JPAQuery<Service>(entityManager).from(service)
                .where(service.linkedBundledParentId.eq(parentId)).fetch();
    }

    /**
     * Finds all services that are children of the given parent id and are not in a terminal status.
     * @param inventoryId the parent id.
     * @return the list of services.
     */
    public List<Service> findOpenByInventoryId(final Long inventoryId) {
        return new JPAQuery<Service>(entityManager).from(service)
                .where((service.inventoryServiceId.eq(inventoryId).or(service.parentServiceId.eq(inventoryId)))
                        .and(service.status.notIn(TerminalServiceStatuses.getStatuses()))).fetch();
    }

    /**
     * Finds all non-terminal or hold services that have an alternate ID.
     * @param tenantId the tenant id to filter by.
     * @return matching services.
     */
    public List<Service> findOpenServicesWithAlternateId(final Long tenantId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.status.notIn(TerminalServiceStatuses.getStatuses())
                        .and(service.status.ne("On Hold"))
                        .and(service.tenantId.eq(tenantId))
                        .and(service.alternateId.isNotNull()))
                .fetch();
    }

    /**
     * Returns all services with matching tenant id.
     * @param tenantId the tenant id
     * @return list of services
     */
    public List<Service> findByTenantId(final Long tenantId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.tenantId.eq(tenantId))
                .fetch();
    }

    public Service findInventoryByProvisioningId(Long provisioningServiceId) {
      return new JPAQuery<Service>(entityManager)
              .from(service)
              .where(service.provisioningServiceId.eq(provisioningServiceId)
                      .and(service.isCurrentInventory.eq(true)))
              .fetchOne();
    }

    public Service findProvisioningByInventoryId(Long inventoryServiceId) {
      return new JPAQuery<Service>(entityManager)
              .from(service)
              .where(service.inventoryServiceId.eq(inventoryServiceId)
                      .and(service.isCurrentInventory.eq(false)))
              .orderBy(service.id.desc())
              .fetchFirst();
    }

    public List<Service> findAllProvisioningByInventoryId(Long inventoryServiceId) {
      return new JPAQuery<Service>(entityManager)
              .from(service)
              .where(service.inventoryServiceId.eq(inventoryServiceId)
                      .and(service.isCurrentInventory.eq(false)))
              .orderBy(service.id.desc())
              .fetch();
    }

    /**
     * Get a macd service.
     * @param parentId parentId.
     * @return services.
     */
    public List<Service> findMacdByParentId(final Long parentId) {
      return new JPAQuery<Service>(entityManager)
              .from(service)
              .where(service.parentServiceId.eq(parentId)
              .and(service.orderType.in(OrderType.DISCONNECT.getOrderType(), OrderType.CHANGE.getOrderType(),
                      OrderType.MOVE.getOrderType(), OrderType.ADD.getOrderType())))
              .fetch();

  }

  public List<Service> findMarkedForDeletion() {
        LocalDate localDate = LocalDate.now().minusDays(30);
        Date deletionDate = Date.valueOf(localDate);
    return new JPAQuery<Service>(entityManager)
            .from(service)
            .where(service.markedForDeletion.eq(true)
            .and(service.deletionDate.lt(deletionDate)))
            .fetch();
  }
}
