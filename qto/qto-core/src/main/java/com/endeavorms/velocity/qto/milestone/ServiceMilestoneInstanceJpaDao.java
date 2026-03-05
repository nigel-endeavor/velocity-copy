package com.endeavorms.velocity.qto.milestone;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.endeavorms.velocity.qto.milestone.QServiceMilestoneInstance.serviceMilestoneInstance;

/**
 * Persistence tier for ServiceMilestoneInstances.
 *
 * @author fcurran
 */
@Repository
public class ServiceMilestoneInstanceJpaDao extends AbstractMilestoneInstanceJpaDao<ServiceMilestoneInstance> {
    @Override
    public List<ServiceMilestoneInstance> listByRecord(final Long serviceId) {
        PreconditionsUtil.checkArgument(serviceId, "A serviceId is required");
        BooleanExpression expression = serviceMilestoneInstance.serviceId.eq(serviceId);
        expression = addMasterCustomerAndTenantFilter(expression, serviceMilestoneInstance.masterCustomerId, serviceMilestoneInstance.tenantId, true);
        JPQLQuery<ServiceMilestoneInstance> query = new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(expression)
                .orderBy(serviceMilestoneInstance.count.desc(),
                        serviceMilestoneInstance.milestoneDate.desc());
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
     * Checks if an instance exists for the given site and milestone code.
     *
     * @param serviceId     order id.
     * @param milestoneCode milestone code.
     * @return True if an instance already exists, false otherwise.
     */
    public boolean doesMilestoneExist(final Long serviceId, final String milestoneCode) {
        PreconditionsUtil.checkArgument(serviceId, "An orderId is required");
        PreconditionsUtil.checkArgument(milestoneCode, "A milestoneCode is required");
        BooleanExpression expression = serviceMilestoneInstance.serviceId.eq(serviceId)
                .and(serviceMilestoneInstance.milestone.code.eq(milestoneCode));

        JPQLQuery<ServiceMilestoneInstance> query = new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(expression)
                .limit(1);

        ServiceMilestoneInstance mi = query.fetchOne();

        return mi != null;

    }

    /**
     * Retrieves current milestone instance by identifiers.
     *
     * @param serviceId   service id
     * @param milestoneId milestone id
     * @return current service milestone instance
     */
    public ServiceMilestoneInstance retrieveCurrentMilestone(final Long serviceId, final Long milestoneId) {
        return new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(serviceMilestoneInstance.serviceId.eq(serviceId)
                        .and(serviceMilestoneInstance.milestone.id.eq(milestoneId)))
                .fetchOne();
    }

    /**
     * Retrieves current milestone by Code.
     *
     * @param id            service id.
     * @param milestoneCode milestone code.
     * @return current order milestone instance.
     */
    public ServiceMilestoneInstance retrieveCurrentMilestoneByCode(final Long id, final String milestoneCode) {
        BooleanExpression expression = serviceMilestoneInstance.serviceId.eq(id)
                .and(serviceMilestoneInstance.milestone.code.eq(milestoneCode));

        JPQLQuery<ServiceMilestoneInstance> query = new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(expression)
                .limit(1);

        ServiceMilestoneInstance mi = query.fetchOne();
        return mi;
    }

    /**
     * Retrieves current Billable Milestones.
     *
     * @param tenantId      Tenant id.
     * @param milestoneCode milestone code.
     * @param endDate       end date.
     * @return current order milestone instance.
     */
    public List<ServiceMilestoneInstance> retrieveBillableMilestones(final Long tenantId, final String milestoneCode,
                                                                     final Date endDate) {


        //with a date limitation of only milestones after 2023-08-301
         String strDate = "2023-08-01";
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = dateFormat.parse(strDate);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
        BooleanExpression expression = serviceMilestoneInstance.tenantId.eq(tenantId)
                .and(serviceMilestoneInstance.milestone.code.eq(milestoneCode)
                        .and(serviceMilestoneInstance.invoiceId.isNull())
                .and(serviceMilestoneInstance.milestoneDate.before(endDate))
                        .and(serviceMilestoneInstance.milestoneDate.after(date)));

        List<ServiceMilestoneInstance> milestones = new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(expression)
                .orderBy(serviceMilestoneInstance.serviceId.asc())
                .fetch();

        return milestones;
    }

    /**
     * Retrieves current milestone by Invoice ID.
     *
     * @param invoiceId invoice id.
     * @return milestone instance.
     */
    public List<ServiceMilestoneInstance> findByInvoiceId(final Long invoiceId) {
        BooleanExpression expression = serviceMilestoneInstance.invoiceId.eq(invoiceId);
        return new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(expression)
                .fetch();
    }

    public List<ServiceMilestoneInstance> findByServiceId(Long serviceId) {
        BooleanExpression expression = serviceMilestoneInstance.serviceId.eq(serviceId);
        return new JPAQuery<ServiceMilestoneInstance>(entityManager)
                .from(serviceMilestoneInstance)
                .where(expression)
                .fetch();
    }
}

