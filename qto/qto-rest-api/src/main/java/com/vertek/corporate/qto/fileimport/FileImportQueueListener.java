package com.vertek.corporate.qto.fileimport;

import com.google.common.collect.Maps;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.fileimport.broadband.BroadbandImporter;
import com.vertek.corporate.qto.fileimport.crossconnect.CrossConnectImporter;
import com.vertek.corporate.qto.fileimport.customer.CustomerImporter;
import com.vertek.corporate.qto.fileimport.customer.EndCustomerImporter;
import com.vertek.corporate.qto.fileimport.dia.DiaImporter;
import com.vertek.corporate.qto.fileimport.ethernet.EthernetImporter;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivityManager;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivityWebsocket;
import com.vertek.corporate.qto.fileimport.mpls.MplsImporter;
import com.vertek.corporate.qto.fileimport.television.TelevisionImporter;
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
import javax.ejb.DependsOn;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import java.util.List;
import java.util.Map;

import static com.vertek.corporate.qto.fileimport.FileImportQueueHandler.FILE_IMPORT_QUEUE;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@DependsOn("LiquibaseStartupBean")
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = FILE_IMPORT_QUEUE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1"),
        @ActivationConfigProperty(propertyName = "transactionTimeout", propertyValue = "600")
})
public class FileImportQueueListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportQueueListener.class);

    @Inject
    private CustomerImporter masterCustomerImporter;

    @Inject
    private EndCustomerImporter endCustomerImporter;

    @Inject
    private BroadbandImporter broadbandImporter;

    @Inject
    private DiaImporter diaImporter;

    @Inject
    private CrossConnectImporter crossConnectImporter;

    @Inject
    private EthernetImporter ethernetImporter;

    @Inject
    private TelevisionImporter televisionImporter;

    @Inject
    private MplsImporter mplsImporter;

    @Inject
    private ImportActivityManager importActivityManager;

    @Inject
    private ImportActivityWebsocket importActivityWebsocket;

    @Override
    public void onMessage(final Message message) {
        createSchedulerSubjectThreadState();
        String jmsMessageId = "";

        try {
            jmsMessageId = message.getJMSMessageID();
            Long id = ((MapMessage) message).getLong("id");
            String type = ((MapMessage) message).getString("type");
            LOGGER.debug("Got message JMSMessageId: {}. For ImportActivity: {}, type: {}", message.getJMSMessageID(), id, type);

            switch (type) {
                case "Master Customer":
                    masterCustomerImporter.importFile(id);
                    break;
                case "End Customer":
                    endCustomerImporter.importFile(id);
                    break;
                case "Broadband":
                    broadbandImporter.importFile(id);
                    break;
                case "DIA":
                    diaImporter.importFile(id);
                    break;
                case "Cross Connect":
                    crossConnectImporter.importFile(id);
                    break;
                case "Ethernet":
                    ethernetImporter.importFile(id);
                    break;
                case "Television":
                    televisionImporter.importFile(id);
                    break;
                case "MPLS":
                    mplsImporter.importFile(id);
                    break;
                default:
                    ImportActivity activity = importActivityManager.retrieve(id);
                    activity.setStatus(ImportActivityStatus.SYSTEM_ERROR);
                    activity.setStatusDetails(type + " is not a supported import type");
                    importActivityManager.edit(activity);
                    importActivityWebsocket.sendRefreshMessage();
            }

        } catch (JMSException e) {
            throw new EJBException("Error processing message " + jmsMessageId, e);
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
