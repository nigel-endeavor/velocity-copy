package com.vertek.corporate.qto.interval.jms;

import com.google.common.collect.Maps;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.events.handlers.BaseEventHandler;
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

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.List;
import java.util.Map;

import static com.vertek.corporate.qto.interval.jms.IntervalQueueHandler.INTERVALS_QUEUE;

/**
 * @author rcasey
 * @since 3/10/2023
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = INTERVALS_QUEUE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
})
public class IntervalQueueListener implements MessageListener {

    /**
     * Private Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IntervalQueueListener.class);

    /**
     * Event handler.
     */
    @Inject
    private BaseEventHandler eventHandler;

    @Override
    public void onMessage(final Message message) {
        String jmsMessageId = "";
        ThreadState threadState = null;
        try {
            jmsMessageId = message.getJMSMessageID();
            LOGGER.debug("Got message with JMSMessageId = {}, redelivered = {}", jmsMessageId, message.getJMSRedelivered());

            IntervalMessage intervalMessage = (IntervalMessage) ((ObjectMessage) message).getObject();

            threadState = createSchedulerSubjectThreadState();

            LOGGER.debug("Interval message: {}", intervalMessage);

            if ("service".equalsIgnoreCase(intervalMessage.getMessageType())) {
                eventHandler.createServiceIntervals(intervalMessage.getMilestoneInstanceId(), intervalMessage.getEntityId());
            } else if ("location".equalsIgnoreCase(intervalMessage.getMessageType())) {
                //todo
            } else {
                LOGGER.error("Unrecognized message type: {}", intervalMessage.getMessageType());
            }
        } catch (Exception e) {
            LOGGER.error("Error processing message {} - {}", jmsMessageId, e.getMessage());
            throw new EJBException(e);
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
