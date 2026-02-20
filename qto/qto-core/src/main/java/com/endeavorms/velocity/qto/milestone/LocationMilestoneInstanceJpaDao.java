package com.endeavorms.velocity.qto.milestone;

import static com.endeavorms.velocity.qto.milestone.QLocationMilestoneInstance.locationMilestoneInstance;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 * Persistence tier for LocationMilestoneInstances.
 *
 * @author fcurran
 */
@Repository
public class LocationMilestoneInstanceJpaDao extends AbstractMilestoneInstanceJpaDao<LocationMilestoneInstance> {
    @Override
    public List<LocationMilestoneInstance> listByRecord(final Long locationId) {
        PreconditionsUtil.checkArgument(locationId, "A locationId is required");
        BooleanExpression expression = locationMilestoneInstance.locationId.eq(locationId);
        expression = addMasterCustomerAndTenantFilter(expression, locationMilestoneInstance.masterCustomerId, locationMilestoneInstance.tenantId, true);
        JPQLQuery<LocationMilestoneInstance> query = new JPAQuery<LocationMilestoneInstance>(entityManager)
                .from(locationMilestoneInstance)
                .where(expression)
                .orderBy(locationMilestoneInstance.count.desc(),
                        locationMilestoneInstance.milestoneDate.desc());
        return query.fetch();
    }

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
     * Checks if an instance exists for the given location and milestone code.
     * @param locationId order id.
     * @param milestoneCode milestone code.
     * @return True if an instance already exists, false otherwise.
     */
    public boolean doesMilestoneExist(final Long locationId, final String milestoneCode) {
        PreconditionsUtil.checkArgument(locationId, "An orderId is required");
        PreconditionsUtil.checkArgument(milestoneCode, "A milestoneCode is required");
        BooleanExpression expression = locationMilestoneInstance.locationId.eq(locationId)
                        .and(locationMilestoneInstance.milestone.code.eq(milestoneCode));

          JPQLQuery<LocationMilestoneInstance> query =  new JPAQuery<LocationMilestoneInstance>(entityManager)
                .from(locationMilestoneInstance)
                .where(expression)
                .limit(1);

          LocationMilestoneInstance mi = query.fetchOne();

        return mi != null;

    }

        /**
     * Retrieves current milestone by name.
     * @param id service id.
     * @param milestoneCode milestone code.
     * @return current order milestone instance.
     */
    public LocationMilestoneInstance retrieveCurrentMilestoneByCode(final Long id, final String milestoneCode) {
                BooleanExpression expression = locationMilestoneInstance.locationId.eq(id)
                        .and(locationMilestoneInstance.milestone.code.eq(milestoneCode));

          JPQLQuery<LocationMilestoneInstance> query =  new JPAQuery<LocationMilestoneInstance>(entityManager)
                .from(locationMilestoneInstance)
                .where(expression)
                .limit(1);

          LocationMilestoneInstance mi = query.fetchOne();
          return mi;
    }

     /**
     * Retrieves current Billable Milestones.
     *
     * @param tenantId            Tenant id.
     * @param milestoneCode milestone code.
     * @param endDate       end date.
     * @return current order milestone instance.
     */
    public List<LocationMilestoneInstance> retrieveBillableMilestones(final Long tenantId, final String milestoneCode,
                                                                     final Date endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        BooleanExpression expression = locationMilestoneInstance.tenantId.eq(tenantId)
                .and(locationMilestoneInstance.milestone.code.eq(milestoneCode)
                        .and(locationMilestoneInstance.invoiceId.isNull()))
                .and(locationMilestoneInstance.milestoneDate.before(endDate));

        List<LocationMilestoneInstance> milestones = new JPAQuery<LocationMilestoneInstance>(entityManager)
                .from(locationMilestoneInstance)
                .where(expression)
                .orderBy(locationMilestoneInstance.locationId.asc())
                .fetch();

        return milestones;
    }

      /**
     * Retrieves current milestone by Invoice ID.
     * @param invoiceId invoice id.
     * @return  milestone instance.
     */
    public List<LocationMilestoneInstance> findByInvoiceId(final Long invoiceId) {
        BooleanExpression expression = locationMilestoneInstance.invoiceId.eq(invoiceId);
        return new JPAQuery<LocationMilestoneInstance>(entityManager)
                .from(locationMilestoneInstance)
                .where(expression)
                .fetch();
    }

    /**
     * Retrieves current milestone by Location ID.
     * @param locationId location id.
     * @return  milestone instances.
     */
    public List<LocationMilestoneInstance> findByLocationId(final Long locationId) {
        BooleanExpression expression = locationMilestoneInstance.locationId.eq(locationId);
        return new JPAQuery<LocationMilestoneInstance>(entityManager)
                .from(locationMilestoneInstance)
                .where(expression)
                .fetch();
    }

}
