package com.vertek.corporate.qto.activation.schedule;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.activation.attempt.ActivationAttempt;
import com.vertek.corporate.qto.activation.attempt.ActivationAttemptManager;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.ftdi.FtdiDispatch;
import com.vertek.corporate.qto.ftdi.FtdiDispatchManager;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueSender;
import jakarta.jms.QueueSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 3/22/2023
 */
@Stateless
public class ActivationScheduleManager extends StandardManager<ActivationSchedule> {

    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationScheduleManager.class);

    /**
     * Persistence tier for ActivationSchedules.
     */
    @Inject
    private ActivationScheduleJpaDao dao;

    @Inject
    private ServiceMilestoneInstanceManager serviceMilestoneInstanceManager;

    @Inject
    private ActivationAttemptManager activationAttemptManager;

    @Inject
    private ServiceManager serviceManager;

    /**
     * Manager for FTDI Dispatches.
     */
    @Inject
    private FtdiDispatchManager ftdiDispatchManager;

    /**
     * JMS Queue.
     */
    @Resource(mappedName = "java:/queue/qto.FtdiProcessingQueue")
    private Queue queue;

    /**
     * Connection factory name.
     */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /**
     * JMS connection factory.
     */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    @Override
    protected ActivationScheduleJpaDao getDao() {
        return dao;
    }

    public ActivationSchedule superCreate(final ActivationSchedule activationSchedule) {
        return super.create(activationSchedule);
    }


    public ActivationSchedule createForInventory(final ActivationSchedule entity) {

        List<ActivationScheduleEquipment> equipment = entity.getEquipment();
        List<ActivationScheduleCustom> customFields = entity.getCustomFields();
        entity.setEquipment(new ArrayList<>());
        entity.setCustomFields(new ArrayList<>());
        ActivationSchedule created = super.create(entity);

        equipment.forEach(e -> e.setActivationScheduleId(created.getId()));
        customFields.forEach(cf -> cf.setActivationScheduleId(created.getId()));
        created.setEquipment(equipment);
        created.setCustomFields(customFields);
        super.edit(created);

        return created;
    }
    @Override
    public ActivationSchedule create(final ActivationSchedule entity) {
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        entity.setMasterCustomerId(service.getMasterCustomerId());
        List<ActivationScheduleEquipment> equipment = entity.getEquipment();
        List<ActivationScheduleCustom> customFields = entity.getCustomFields();
        entity.setEquipment(new ArrayList<>());
        entity.setCustomFields(new ArrayList<>());
        ActivationSchedule created = super.create(entity);
        equipment.forEach(e -> e.setActivationScheduleId(created.getId()));
        customFields.forEach(cf -> cf.setActivationScheduleId(created.getId()));
        created.setEquipment(equipment);
        created.setCustomFields(customFields);

        if (!created.getVendor().equals("Endeavor")) {
            List<ActivationAttempt> existingAttempts = activationAttemptManager.findByServiceId(created.getServiceId());
            ActivationAttempt activationAttempt = new ActivationAttempt();
            activationAttempt.setServiceId(created.getServiceId());
            activationAttempt.setActivationScheduleId(created.getId());
            activationAttempt.setScheduledCheckInTime(created.getRequestedDate());
            activationAttempt.setScheduledAttemptStatus("Schedule Date Confirmed");
            activationAttempt.setAttemptNumber(Long.valueOf(existingAttempts.size() + 1));
            if (!Strings.isNullOrEmpty(entity.getTechnicalNote())) {
                activationAttempt.setIssueNotes(entity.getTechnicalNote());
            }
            activationAttempt.setSameDaySchedule(entity.isApplySameDayTurnUpSurcharge());
            activationAttemptManager.create(activationAttempt);
        }
        edit(created);

        if (created.getVendor().equals("Endeavor")) {
            setActivationMilestones(created, false);
            createDispatch(created);
        } else {
            setActivationMilestones(created, true);
        }


        //surcharges
//        handleTurnUpExpediteSurcharge(created, service);
        return created;
    }

    public ActivationSchedule editForInventory(final ActivationSchedule entity) {
        return super.edit(entity);
    }

    @Override
    public ActivationSchedule edit(final ActivationSchedule entity) {
        ActivationSchedule schedule = retrieve(entity.getId());
        if (!entity.getRequestedDate().equals(schedule.getRequestedDate())) {
            ActivationAttempt attempt = activationAttemptManager.findByActivationScheduleId(entity.getId());
            attempt.setScheduledCheckInTime(entity.getRequestedDate());
        }
        entity.getEquipment().forEach(e -> e.setActivationScheduleId(entity.getId()));
        entity.getCustomFields().forEach(cf -> cf.setActivationScheduleId(entity.getId()));
        entity.setTenantId(schedule.getTenantId());
        entity.setMasterCustomerId(schedule.getMasterCustomerId());
        ActivationSchedule updated = super.edit(entity);
        editDispatches(updated);
        return updated;
    }

    private void createDispatch(final ActivationSchedule schedule) {
        FtdiDispatch ftdiDispatch = new FtdiDispatch();
        ftdiDispatch.setScheduleId(schedule.getId());
        ftdiDispatch.setOrderCreateDate(new Date());
        ftdiDispatch.setOrderTypeId(schedule.getFtdiOrderTypeId());
        ftdiDispatch.setVendor(schedule.getVendor());
        ftdiDispatch.setStatus("Order Created");
        ftdiDispatch = ftdiDispatchManager.create(ftdiDispatch);

    }

    private void editDispatches(final ActivationSchedule schedule) {
        for (FtdiDispatch ftdiDispatch : schedule.getDispatches()) {
            if (ftdiDispatch.getVendorDispatchId() == null) {
                ftdiDispatch = schedule.getDispatches().get(0);
                ftdiDispatch.setOrderCreateDate(new Date());
                ftdiDispatch.setOrderTypeId(schedule.getFtdiOrderTypeId());
                ftdiDispatch.setVendor(schedule.getVendor());
                ftdiDispatch.setStatus("Order Created");
                ftdiDispatch = ftdiDispatchManager.edit(ftdiDispatch);
            }
        }
    }

    public void sendDispatchToQueue(final ActivationSchedule schedule, final FtdiDispatch ftdiDispatch) {

        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
             QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE)) {

            jmsQueueName = queue.getQueueName();
            ObjectMessage message = session.createObjectMessage();
            message.setLongProperty("dispatchId", ftdiDispatch.getId());
            message.setLongProperty("tenantId", schedule.getTenantId());
            message.setStringProperty("action", "CREATE");
            message.setStringProperty("vendor", "ENDEAVOR");

            LOGGER.debug("About to send: {}, scheduleId = {}",
                    jmsQueueName, schedule.getId());

            try (QueueSender sender = session.createSender(queue)) {
                sender.send(message);

                LOGGER.debug("Sent: {}, scheduleId = {}",
                        jmsQueueName, schedule.getId());
            }

        } catch (Exception e) {
            LOGGER.error("Error closing connection: {}, scheduleId = {}",
                    jmsQueueName, schedule.getId());
        }

    }

    public void setActivationMilestones(final ActivationSchedule schedule, final Boolean both) {
        String activationRequestedCode = "ACTIVATION_REQUESTED";
        ServiceMilestoneInstance activationRequested = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(schedule.getServiceId(), activationRequestedCode);
        if (activationRequested == null) {
            serviceMilestoneInstanceManager.create(schedule.getServiceId(), activationRequestedCode, new Date());
        } else {
            activationRequested.setMilestoneDate(new Date());
            serviceMilestoneInstanceManager.edit(activationRequested);
        }

        if (both) {
            String activationScheduledCode = "ACTIVATION_SCHEDULED";
            ServiceMilestoneInstance activationScheduled = serviceMilestoneInstanceManager.retrieveCurrentMilestoneByCode(schedule.getServiceId(), activationScheduledCode);
            if (activationScheduled == null) {
                serviceMilestoneInstanceManager.create(schedule.getServiceId(), activationScheduledCode, schedule.getRequestedDate());
            } else {
                activationScheduled.setMilestoneDate(schedule.getRequestedDate());
                serviceMilestoneInstanceManager.edit(activationScheduled);
            }
        }
    }

    public List<ActivationSchedule> findByServiceId(final Long serviceId) {
        return dao.findByServiceId(serviceId);
    }


    /**
     * Returns an Activation Schedule.
     *
     * @param scheduleId Schedule ID.
     * @param tenantId   Tenant ID.
     * @return Activation Schedule Object.
     */
    public ActivationSchedule findByIdAndTenant(final Long scheduleId, final Long tenantId) {
        return dao.findByIdAndTenant(scheduleId, tenantId);
    }

    /**
     * Returns Activation Schedules.
     *
     * @param serviceId Service ID.
     * @param tenantId  Tenaan ID.
     * @return Activation Schedule Object.
     */
    public List<ActivationSchedule> findByServiceIdAndTenant(final Long serviceId, final Long tenantId) {

        return dao.findByServiceIdAndTenant(serviceId, tenantId);
    }

    /**
     * Returns Activation Schedules.
     *
     * @param legacyId Legacy ID.
     * @param tenantId  Tenaan ID.
     * @return Activation Schedule Object.
     */
    public ActivationSchedule findByLegacyAndTenant(final Long legacyId, final Long tenantId) {
            return dao.findByLegacyAndTenant(legacyId, tenantId);
    }


}
