package com.vertek.corporate.qto.activation.schedule;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.activation.schedule.QActivationSchedule.activationSchedule;

/**
 * @author rcasey
 * @since 3/22/2023
 */
@Stateless
public class ActivationScheduleJpaDao extends AbstractMasterCustomerJpaDao<ActivationSchedule> {
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

    public List<ActivationSchedule> findByServiceId(final Long serviceId) {
        BooleanExpression expression = activationSchedule.serviceId.eq(serviceId);
        expression = addMasterCustomerAndTenantFilter(expression, activationSchedule.masterCustomerId, activationSchedule.tenantId, true);
        return new JPAQuery<ActivationSchedule>(entityManager)
                .from(activationSchedule)
                .where(expression)
                .orderBy(new OrderSpecifier<>(Order.DESC, activationSchedule.requestedDate))
                .fetch();
    }

    /**
     * Returns an Activation Schedule.
     *
     * @param scheduleId Schedule ID.
     * @param tenantId   Tenaan ID.
     * @return Activation Schedule Object.
     */
    public ActivationSchedule findByIdAndTenant(final Long scheduleId, final Long tenantId) {
        return new JPAQuery<ActivationSchedule>(entityManager)
                .from(activationSchedule)
                .where(activationSchedule.id.eq(scheduleId)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .fetchOne();
    }

    /**
     * Returns Activation Schedules.
     *
     * @param serviceId Service ID.
     * @param tenantId  Tenaan ID.
     * @return Activation Schedule Object.
     */
    public List<ActivationSchedule> findByServiceIdAndTenant(final Long serviceId, final Long tenantId) {

                return new JPAQuery<ActivationSchedule>(entityManager)
                .from(activationSchedule)
                .where(activationSchedule.serviceId.eq(serviceId)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .fetch();
    }

    /**
     * Returns Activation Schedules.
     *
     * @param legacyId Legacy ID.
     * @param tenantId  Tenaan ID.
     * @return Activation Schedule Object.
     */
    public ActivationSchedule findByLegacyAndTenant(final Long legacyId, final Long tenantId) {

                return new JPAQuery<ActivationSchedule>(entityManager)
                .from(activationSchedule)
                .where(activationSchedule.legacyId.eq(legacyId)
                        .and(activationSchedule.tenantId.eq(tenantId)))
                .fetchOne();
    }


}
