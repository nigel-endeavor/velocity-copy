package com.vertek.corporate.qto.ftdi;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.ftdi.QFtdiDispatch.ftdiDispatch;
import static com.vertek.corporate.qto.activation.schedule.QActivationSchedule.activationSchedule;
import static com.vertek.corporate.qto.service.QService.service;


@Stateless
public class FtdiDispatchJpaDao extends AbstractJpaDao<FtdiDispatch, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Returns an Activation Schedule.
     *
     * @param vendorId Dispatch Vendor ID.
     * @param tenantId   Tenant ID.
     * @return Activation Schedule Object.
     */
    public List<FtdiDispatch> findByVendorIdAndTenant(final String vendorId, final Long tenantId) {
        return new JPAQuery<FtdiDispatch>(entityManager)
                .from(ftdiDispatch)
                .join(activationSchedule).on(ftdiDispatch.scheduleId.eq(activationSchedule.id))
                .where(ftdiDispatch.vendorDispatchId.eq(vendorId)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .orderBy(new OrderSpecifier<Long>(Order.DESC, ftdiDispatch.id))
                .fetch();
    }

      /**
     * Returns an Activation Schedule.
     *
     * @param id Dispatch ID.
     * @param tenantId   Tenant ID.
     * @return Activation Schedule Object.
     */
    public FtdiDispatch findByIdAndTenant(final Long id, final Long tenantId) {
        return new JPAQuery<FtdiDispatch>(entityManager)
                .from(ftdiDispatch)
                .join(activationSchedule).on(ftdiDispatch.scheduleId.eq(activationSchedule.id))
                .where(ftdiDispatch.id.eq(id)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .orderBy(new OrderSpecifier<Long>(Order.DESC, ftdiDispatch.id))
                .fetchOne();
    }

        /**
     * Returns an Activation Schedule.
     *
     * @param scheduleId Solution ID.
     * @param tenantId   Tenant ID.
     * @return Activation Schedule Object.
     */
    public List<FtdiDispatch> findByScheduleIdAndTenant(final Long scheduleId, final Long tenantId) {
        return new JPAQuery<FtdiDispatch>(entityManager)
                .from(ftdiDispatch)
                .join(activationSchedule).on(ftdiDispatch.scheduleId.eq(activationSchedule.id))
                .where(ftdiDispatch.scheduleId.eq(scheduleId)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .fetch();
    }

    /**
     * Returns an Activation Schedule.
     *
     * @param vendorId Dispatch Vendor ID.
     * @param tenantId Tenaan ID.
     * @return Activation Schedule Object.
     */
    public FtdiDispatch findByTopDispatchByVendorIdAndTenant(final String vendorId, final Long tenantId) {
        return new JPAQuery<FtdiDispatch>(entityManager)
                .from(ftdiDispatch)
                .join(activationSchedule).on(ftdiDispatch.scheduleId.eq(activationSchedule.id))
                .where(ftdiDispatch.vendorDispatchId.eq(vendorId)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .orderBy(new OrderSpecifier<Long>(Order.DESC, ftdiDispatch.id))
                .fetchOne();
    }

     /**
     * Returns an Activation Schedule.
     *
     * @param id Dispatch ID.
     * @return Activation Schedule Object.
     */
    public FtdiDispatch findByLegacyId(final Long id) {
        return new JPAQuery<FtdiDispatch>(entityManager)
                .from(ftdiDispatch)
                .where(ftdiDispatch.legacyId.eq(id))
                .orderBy(new OrderSpecifier<Long>(Order.DESC, ftdiDispatch.id))
                .fetchOne();
    }

    /**
     * Returns dispatches for the Mass Update job
     * @param tenantId
     * @return
     */
    public List<String> findOpenDispatchesForMassUpdate(final Long tenantId) {
        return new JPAQuery<FtdiDispatch>(entityManager)
                .select(ftdiDispatch.vendorDispatchId)
                .from(ftdiDispatch)
                .join(activationSchedule).on(ftdiDispatch.scheduleId.eq(activationSchedule.id))
                .join(service).on(activationSchedule.serviceId.eq(service.id))
                .where(ftdiDispatch.status.ne("Cancelled")
                        .and(ftdiDispatch.status.ne("Closed"))
                        .and(ftdiDispatch.status.ne("Pending"))
                        .and(ftdiDispatch.vendorDispatchId.isNotNull())
                        .and(service.status.ne("Service Cancelled"))
                        .and(service.status.ne("Change In Assignment"))
                        .and(service.status.ne("Service Complete"))
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .orderBy(new OrderSpecifier<Long>(Order.DESC, ftdiDispatch.id))
                .fetch();
    }

    /**
     * Returns list of existing Vendor IDs for the Mass Update job
     * @param tenantId
     * @return
     */
    public List<String> findAllCtns(final Long tenantId) {
         return new JPAQuery<FtdiDispatch>(entityManager)
                 .select(ftdiDispatch.vendorDispatchId)
                 .from(ftdiDispatch)
                 .fetch();


    }
}
