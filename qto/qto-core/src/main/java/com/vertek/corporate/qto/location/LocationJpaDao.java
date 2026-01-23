package com.vertek.corporate.qto.location;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PlatformDatabase;
import com.vertek.corporate.qto.service.Service;

import java.sql.Date;
import java.time.LocalDate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.location.QLocation.location;
import static com.vertek.corporate.qto.service.QService.service;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@Stateless
public class LocationJpaDao extends AbstractMasterCustomerJpaDao<Location> {
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

    public PaginatedResult<Location> findBySearchCriteria(final LocationSearchCriteria criteria) {
        return new PaginatedResult<>(new JPAQuery<Location>(entityManager).from(location)
                .where(getExpression(criteria))
                .offset(criteria.getOffset())
                .limit(criteria.getLimit())
                .fetchResults()); //todo: assess deprecation, this will require changes to PaginatedResult
    }

    /**
     * Gets the where clause for a given LocationSearchCriteria.
     *
     * @param criteria the criteria to filter by.
     * @return relevant results.
     */
    public Predicate getExpression(final LocationSearchCriteria criteria) {

        //todo: filter expression

        return location.id.isNotNull();
    }

    /**
     * Get list of locations by Order ID.
     *
     * @param orderId orderId.
     * @return list of locations;
     */
    public List<Location> findByOrderId(Long orderId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.orderId.eq(orderId))
                .fetch();
    }

    /**
     * Gets services by the given location id.
     *
     * @param locationId the location id to filter by.
     * @return list of services;
     */
    public List<Service> findServicesByLocationId(final Long locationId) {
        return new JPAQuery<Service>(entityManager)
                .from(service)
                .where(service.locationId.eq(locationId))
                .fetch();
    }

    /**
     * Returns a Location.
     *
     * @param locationId Location ID.
     * @param tenantId   Tenant ID.
     * @return Activation Scheule Object.
     */
    public Location findByIdAndTenant(final Long locationId, final Long tenantId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.id.eq(locationId)
                        .and(location.tenantId.eq(tenantId)))
                .fetchOne();
    }

      /**
     * Returns a Location.
     *
     * @param clientLocationId Location ID.
     * @param tenantId   Tenant ID.
     * @return Activation Scheule Object.
     */
    public List<Location> findByClientLocIdAndTenant(final String clientLocationId, final Long tenantId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.clientLocationId.eq(clientLocationId)
                        .and(location.tenantId.eq(tenantId)))
                .fetch();
    }

    /**
     * Returns an Inventory Location.
     *
     * @param clientLocationId Location ID.
     * @param tenantId   Tenant ID.
     * @return Activation Scheule Object.
     */
    public Location findInvByClientLocIdAndTenant(final String clientLocationId, final Long tenantId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.clientLocationId.eq(clientLocationId)
                        .and(location.isCurrentInventory.eq(true))
                        .and(location.tenantId.eq(tenantId)))
                .fetchOne();
    }

      /**
     * Returns a Location.
     *
     * @param legacyId Location ID.
     * @param tenantId   Tenant ID.
     * @return Activation Scheule Object.
     */
    public Location findByLegacyIdAndTenant(final String legacyId, final Long tenantId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.legacyId.eq(legacyId)
                        .and(location.tenantId.eq(tenantId)))
                .fetchOne();
    }

    /**
     * Get list of locations by Order ID.
     *
     * @param parentId parentId.
     * @return list of locations;
     */
    public List<Location> findByParentId(Long parentId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.parentLocationId.eq(parentId))
                .fetch();
    }

     /**
     * Gets the root (top level parent id) of the location tree for the given location.
     * @param locationId locationId
     * @return root location id
     */
    public Long getLocationTreeRoot(final Long locationId) {
        return ((Number) entityManager.createNativeQuery("CALL GetParentLocationTreeRoot(?)")
                .setParameter(1, locationId)
                .getSingleResult()).longValue();
    }

    public Location findByInventoryId(Long inventoryLocationId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.id.eq(inventoryLocationId))
                .fetchOne();
    }

    public List<Location> findAllProvisioningByInventoryId(Long inventoryLocationId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.inventoryLocationId.eq(inventoryLocationId))
                .fetch();
    }

    public Location findInvByProvisioningId(Long provisioningLocationId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.provisioningLocationId.eq(provisioningLocationId)
                        .and(location.isCurrentInventory.eq(true)))
                .fetchOne();
    }

    public List<Location> findOpenByClientLocIdAndTenant(String clientLocationId, Long tenantId) {
        return new JPAQuery<Location>(entityManager)
                .from(location)
                .where(location.clientLocationId.eq(clientLocationId)
                        .and(location.tenantId.eq(tenantId))
                        .and(location.status.notIn(TerminalLocationStatuses.getStatuses())))
                .fetch();
    }

    public List<Location> findMarkedForDeletion() {
        LocalDate localDate = LocalDate.now().minusDays(30);
        Date deletionDate = Date.valueOf(localDate);
        return new JPAQuery<Location>(entityManager)
        .from(location)
        .where(location.markedForDeletion.isTrue()
                .and(location.deletionDate.lt(deletionDate)))
        .fetch();

    }
}
