package com.vertek.corporate.qto.dispute.jms;

import com.google.common.collect.Maps;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.dispute.DisputeManager;
import com.vertek.corporate.qto.dispute.multidispute.MultiDisputeRequestDto;
import com.vertek.corporate.qto.notification.NotificationManager;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.env.BasicIniEnvironment;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.ThreadState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

import java.util.List;
import java.util.Map;

import static com.vertek.corporate.qto.dispute.multidispute.MultiDisputeQueueHandler.MULTI_DISPUTE_QUEUE;

/**
 * MDB for multi dispute creation.
 * @author rcasey
 * @since 6/6/2024
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = MULTI_DISPUTE_QUEUE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
})
public class MultiDisputeListener implements MessageListener {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDisputeListener.class);

    /** Dispute manager. */
    @Inject
    private DisputeManager disputeManager;

    /** Notification manager. */
    @Inject
    private NotificationManager notificationManager;

    @Override
    public void onMessage(final Message message) {
        String jmsMessageId = "";
        ThreadState threadState = null;
        MultiDisputeRequestDto dto = null;
        try {
            jmsMessageId = message.getJMSMessageID();
            LOGGER.debug("Got message with JMSMessageId = {}, redelivered = {}", jmsMessageId, message.getJMSRedelivered());
            dto = (MultiDisputeRequestDto) ((jakarta.jms.ObjectMessage) message).getObject();
            threadState = createSchedulerSubjectThreadState();

            Long numDisputesCreated = disputeManager.multiCreate(dto);

            String body = numDisputesCreated + (numDisputesCreated > 1 ? " disputes" : " dispute") + " created";
            notificationManager.create(dto.getSubjectId(), "Multi Dispute Creation Completed Successfully", body, "done", null);
        } catch (Exception e) {
            LOGGER.error("Error processing message {} - {}", jmsMessageId, e.getMessage());
            if (dto != null && dto.getSubjectId() != null) {
                notificationManager.create(dto.getSubjectId(), "Multi Dispute Creation Failed", e.getMessage(), "error", null);
            }
        } finally {
            LOGGER.debug("Done with message {}", jmsMessageId);
        }
    }


    /**
     * Binds the scheduler subject to the currently executing Thread.
     *
     * @return a bound ThreadState.
     */
    protected ThreadState createSchedulerSubjectThreadState() {
        // The implementation below is likely a temporary solution.  During application deployment, EJBs start up before
        // Shiro security is initialized.  MDBs will attempt to run and fail if there is no initialized Shiro security
        // manager.  Below, we call getSecurityManager and if UnavailableSecurityManagerException is thrown
        // then we do minimal initialization so that the subject specified by SecurityUtils.SCHEDULER will be seen
        // as the current logged-in user by code that verifies the current tenant.
        // (e.g. AbstractMultitenantJpaDao.verifyTenant)
        // The subject is defined in a shiro ini file in the class path. (shiro-ejbstartup.ini)
        Subject subject;
        try {
            org.apache.shiro.SecurityUtils.getSecurityManager();
            LOGGER.trace("Got SecurityManager...");
            Map<String, Object> attributes = Maps.newHashMap();
            // create simple authentication info
            List<Object> principals = CollectionUtils.asList(SecurityUtils.SCHEDULER, attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, "casRealm");
            LOGGER.trace("Building subject...");
            subject = new Subject.Builder()
                    .principals(principalCollection)
                    .buildSubject();
        } catch (UnavailableSecurityManagerException ex) {
            LOGGER.trace("Creating IniSecurityManagerFactory...");
//            IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-ejbstartup.ini");
            BasicIniEnvironment factory = new BasicIniEnvironment("classpath:shiro-ejbstartup.ini");
            LOGGER.trace("Getting SecurityManager instance...");
            org.apache.shiro.mgt.SecurityManager securityManager = factory.getSecurityManager();
            LOGGER.trace("Setting SecurityManager...");
            org.apache.shiro.SecurityUtils.setSecurityManager(securityManager);
            subject = org.apache.shiro.SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(SecurityUtils.SCHEDULER, "schedpassword"));
        }

        LOGGER.trace("Creating thread state...");
        ThreadState state = new SubjectThreadState(subject);
        LOGGER.trace("Binding thread state...");
        state.bind();
        return state;
    }
}
