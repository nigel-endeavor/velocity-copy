package com.vertek.corporate.qto.company.jms;

import com.google.common.collect.Maps;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.company.CompanyManager;
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
import jakarta.jms.ObjectMessage;

import java.util.List;
import java.util.Map;

import static com.vertek.corporate.qto.company.jms.CompanyMessageHandler.COMPANY_QUEUE;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = COMPANY_QUEUE
        ),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
})
public class CompanyMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMessageListener.class);

    @Inject
    private CompanyManager companyManager;

    @Override
    public void onMessage(final Message message) {
        CompanyMessage companyMessage = null;
        try {
            createSchedulerSubjectThreadState();
            companyMessage = (CompanyMessage) ((ObjectMessage) message).getObject();
            if (companyMessage.getMessageType().equals("updateCounts")) {
                companyManager.updateInventoryCounts(companyMessage.getCompanyId());
            } else {
                LOGGER.error("Unknown message type: {}", companyMessage.getMessageType());
            }
        } catch (Exception e) {
            LOGGER.error("Error processing message: {} {} ", companyMessage.getCompanyId(), companyMessage.getMessageType());
        }
    }

    /**
     * Binds the scheduler subject to the currently executing Thread.
     *
     * @return a bound ThreadState.
     */
    protected ThreadState createSchedulerSubjectThreadState() {
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
