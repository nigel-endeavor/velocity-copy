package com.endeavorms.velocity.qto.activation.attempt;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerJpaDao;
import com.endeavorms.velocity.qto.common.PlatformDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;

import static com.endeavorms.velocity.qto.activation.attempt.QActivationAttempt.activationAttempt;


/**
 * @author rcasey
 * @since 3/23/2023
 */
@Component
public class ActivationAttemptJpaDao extends AbstractMasterCustomerJpaDao<ActivationAttempt> {
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
     * Finds all ActivationAttempts with the given serviceId, ordered by id desc.
     *
     * @param serviceId service id
     * @return list of corresponding ActivationAttempts
     */
    public List<ActivationAttempt> findByServiceId(final Long serviceId) {
        BooleanExpression expression = activationAttempt.serviceId.eq(serviceId);
        expression = addMasterCustomerAndTenantFilter(expression, activationAttempt.masterCustomerId, activationAttempt.tenantId, true);
        return new JPAQuery<ActivationAttempt>(entityManager)
                .from(activationAttempt)
                .where(expression)
                .orderBy(new OrderSpecifier<>(Order.DESC, activationAttempt.scheduledCheckInTime))
                .fetch();
    }

    /**
     * Finds all ActivationAttempts with the given serviceId.
     *
     * @param serviceId service id
     * @param tenantId  tenant id
     * @return list of corresponding ActivationAttempts
     */
    public List<ActivationAttempt> findByServiceIdAndTenant(final Long serviceId, final Long tenantId) {
        return new JPAQuery<ActivationAttempt>(entityManager)
                .from(activationAttempt)
                .where(activationAttempt.serviceId.eq(serviceId)
                        .and(activationAttempt.tenantId.eq(tenantId)))
                .fetch();
    }

    /**
     * Finds an ActivationAttempt with the given ftdiAppointmentId.
     *
     * @param ftdiAppointmentId Vendor appointment id
     * @return list of corresponding ActivationAttempts
     */
    public ActivationAttempt findByFtdiAppointmentId(final Long ftdiAppointmentId) {
        return new JPAQuery<ActivationAttempt>(entityManager)
                .from(activationAttempt)
                .where(activationAttempt.ftdiAppointmentId.eq(ftdiAppointmentId))
                .fetchOne();
    }

    /**
     * Finds the ActivationAttempt with the given activationScheduleId.
     *
     * @param activationScheduleId activation schedule id
     * @return ActivationAttempt
     */
    public ActivationAttempt findByActivationScheduleId(final Long activationScheduleId) {
        return new JPAQuery<ActivationAttempt>(entityManager)
                .from(activationAttempt)
                .where(activationAttempt.activationScheduleId.eq(activationScheduleId))
                .fetchOne();
    }

    /**
     * Finds all Activation Attempts scheduled today for the FTDI Checkin Quartz job.
     * @param tenantId
     * @return
     */
    public List<ActivationAttempt> findTodaysUnstartedAppointments(Long tenantId) {
        Calendar calendar = Calendar.getInstance();
         return new JPAQuery<ActivationAttempt>(entityManager)
                .from(activationAttempt)
                .where(activationAttempt.scheduledCheckInTime.year().eq(calendar.get(Calendar.YEAR))
                        .and(activationAttempt.scheduledCheckInTime.month().eq(calendar.get(Calendar.MONTH) + 1))
                        .and(activationAttempt.scheduledCheckInTime.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH)+3))
                        .and(activationAttempt.fieldTechCheckIn.isNull())
                        .and(activationAttempt.fieldTechName.isNull().or(activationAttempt.fieldTechName.eq("")))
                        .and(activationAttempt.ftdiVendorId.isNotNull())
                        .and(activationAttempt.tenantId.eq(tenantId)))
                .fetch();
    }

    public List<ActivationAttempt> findByAllByScheduleId(Long scheduleId) {

        return new JPAQuery<ActivationAttempt>(entityManager)
                .from(activationAttempt)
                .where(activationAttempt.activationScheduleId.eq(scheduleId))
                .fetch();
    }
}
