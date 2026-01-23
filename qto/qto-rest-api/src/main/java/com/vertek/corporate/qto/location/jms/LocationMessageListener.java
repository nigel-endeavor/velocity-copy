package com.vertek.corporate.qto.location.jms;

import com.google.common.collect.Maps;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.contact.Contact;
import com.vertek.corporate.qto.contact.ContactType;
import com.vertek.corporate.qto.contact.location.LocationContact;
import com.vertek.corporate.qto.contact.location.LocationContactManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.vertek.corporate.qto.location.jms.LocationMessageHandler.LOCATION_QUEUE;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = LOCATION_QUEUE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
})
public class LocationMessageListener implements MessageListener {

    /**
     * Private Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMessageListener.class);

    /**
     * The manager for Location.
     */
    @Inject
    private LocationManager locationManager;

    /**
     * The manager for Location Contacts.
     */
    @Inject
    private LocationContactManager locationContactManager;


    @Override
    public void onMessage(final Message message) {
        String jmsMessageId = "";
        String messageType = null;
        Long locationId = null;
        Location existingLocation = null;
        Location newLocation = null;
        try {
            jmsMessageId = message.getJMSMessageID();
            LOGGER.debug("Got message with JMSMessageId = {}, redelivered = {}",
                    jmsMessageId, message.getJMSRedelivered());
            LocationMessage locationMessage = (LocationMessage) ((ObjectMessage) message).getObject();
            createSchedulerSubjectThreadState();
            LOGGER.debug("Location message: {}", locationMessage);
            locationId = locationMessage.getLocationId();
            existingLocation = locationMessage.getExistingLocation();
            newLocation = locationMessage.getNewLocation();
            messageType = locationMessage.getMessageType();
            switch (messageType) {
                case "disconnectContact":
                    break;
                default:
                    LOGGER.error("No Location message type matching: {}", messageType);
                    break;
            }

        } catch (Exception e) {
            LOGGER.error("Error processing message {} - {}", jmsMessageId, e.getMessage());
            throw new EJBException(e);
        } finally {
            LOGGER.debug("Done with message {}", jmsMessageId);
        }
    }

    private void processInventoryMerge(final Long locationId, final Location existingLocation,
                                       final Location newLocation, final Location updateLocation) {

        Field[] fields = Location.class.getDeclaredFields();
        Field[] fieldsToUpdate = new Field[0];
        List<String> fieldsToIgnore = Arrays.asList("lastUpdateDate", "lastUpdateBy",
                "services", "inventoryServices", "address", "lcon", "levelOfEffort",
                "mrc", "nrc", "icb", "osp", "status", "inventoryMrc", "inventoryNrc",
                "inventoryIcb", "inventoryOsp", "requirementTemplateId",
                "progressPercentage", "sortOrder", "active", "parentLocationId");

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object existingValue = field.get(existingLocation);
                Object newValue = field.get(newLocation);
                if (existingValue instanceof BigDecimal) {
                    if (((BigDecimal) existingValue).compareTo((BigDecimal) newValue) != 0) {
                        fieldsToUpdate = addFieldToArray(fieldsToUpdate, field);
                    }
                } else if (!fieldsToIgnore.contains(field.getName()) && !Objects.equals(existingValue, newValue)) {
                    fieldsToUpdate = addFieldToArray(fieldsToUpdate, field);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("Error merging location: {}", e.getMessage());
            }
        }

        if (fieldsToUpdate.length > 0) {
            LOGGER.debug("Updating location: {}", locationId);
            try {
                for (Field field : fieldsToUpdate) {
                   field.set(updateLocation, field.get(newLocation));
                }
                locationManager.edit(updateLocation);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (newLocation.getLcon() != null) {
            updateLocation.setLcon(locationContactManager.retrieveByTypeAndLocationId(
                    ContactType.LCON, updateLocation.getId()));
            if (updateLocation.getLcon() != null) {
                mergeLcon(existingLocation.getLcon(), newLocation.getLcon(), updateLocation.getLcon());
            }

        }
    }

    private void mergeLcon(LocationContact existingLcon, LocationContact newLcon, LocationContact updateLcon) {
        Field[] fields = Contact.class.getDeclaredFields();
        Field[] fieldsToUpdate = new Field[0];
        List<String> fieldsToIgnore = Arrays.asList("lastUpdateDate", "lastUpdateBy", "legacyId",
                "companyId", "address", "sortOrder");

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object existingValue = field.get(existingLcon);
                Object newValue = field.get(newLcon);
                if (!fieldsToIgnore.contains(field.getName()) && !Objects.equals(existingValue, newValue)) {
                    fieldsToUpdate = addFieldToArray(fieldsToUpdate, field);
                }
            } catch (IllegalAccessException e) {
                LOGGER.error("Error merging location: {}", e.getMessage());
            }
        }

        if (fieldsToUpdate.length > 0) {
            LOGGER.debug("Updating lcon: {}", existingLcon.getFirstName());
            try {
                for (Field field : fieldsToUpdate) {
                   field.set(updateLcon, field.get(newLcon));
                }
                locationContactManager.edit(updateLcon);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Field[] addFieldToArray(Field[] array, Field field) {
        int length = array.length;
        array = Arrays.copyOf(array, length + 1);
        array[length] = field;
        return array;
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

