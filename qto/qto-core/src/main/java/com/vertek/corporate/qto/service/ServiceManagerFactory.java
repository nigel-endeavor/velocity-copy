package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.service._4g5g.GServiceManager;
import com.vertek.corporate.qto.service.broadband.BroadbandServiceManager;
import com.vertek.corporate.qto.service.crossconnect.CrossConnectServiceManager;
import com.vertek.corporate.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingManager;
import com.vertek.corporate.qto.service.engineeringIAM.EngineeringIAMServiceManager;
import com.vertek.corporate.qto.service.engineeringMDM.EngineeringMDMManager;
import com.vertek.corporate.qto.service.cyber360MXDR.Cyber360MXDRManager;
import com.vertek.corporate.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.vertek.corporate.qto.service.engineeringInfoProtection.EngineeringInfoProtectionManager;
import com.vertek.corporate.qto.service.dia.DiaServiceManager;
import com.vertek.corporate.qto.service.ethernet.EthernetServiceManager;
import com.vertek.corporate.qto.service.microsoftLicenses.MicrosoftLicensesManager;
import com.vertek.corporate.qto.service.mpls.MplsServiceManager;
import com.vertek.corporate.qto.service.ransomMDR.RansomMDRServiceManager;
import com.vertek.corporate.qto.service.riskMDR.RiskMDRServiceManager;
import com.vertek.corporate.qto.service.television.TelevisionServiceManager;
import com.vertek.corporate.qto.service.threatMDR.ThreatMDRServiceManager;
import com.vertek.corporate.qto.service.ucaas.UcaasServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.EnumMap;

/**
 * Simple factory for getting the correctly injected manager for a given service type.
 */
public class ServiceManagerFactory {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManagerFactory.class);

    /** Factory instance. */
    @Inject
    @Any
    private Instance<AbstractServiceManager<? extends Service>> factoryInstance;

    /** Known managers. */
    private static final EnumMap<ServiceType, Class<? extends AbstractServiceManager<? extends Service>>> managers
            = new EnumMap<>(ServiceType.class);

    /* Initialize the known managers. */
    static {
        managers.put(ServiceType.DIA, DiaServiceManager.class);
        managers.put(ServiceType.BROADBAND, BroadbandServiceManager.class);
        managers.put(ServiceType.UCAAS, UcaasServiceManager.class);
        managers.put(ServiceType.G, GServiceManager.class);
        managers.put(ServiceType.CROSSCONNECT, CrossConnectServiceManager.class);
        managers.put(ServiceType.ETHERNET, EthernetServiceManager.class);
        managers.put(ServiceType.TELEVISION, TelevisionServiceManager.class);
        managers.put(ServiceType.MPLS, MplsServiceManager.class);
        managers.put(ServiceType.THREATMDR, ThreatMDRServiceManager.class);
        managers.put(ServiceType.RANSOMMDR, RansomMDRServiceManager.class);
        managers.put(ServiceType.RISKMDR, RiskMDRServiceManager.class);
        managers.put(ServiceType.ENGINEERING_IAM, EngineeringIAMServiceManager.class);
        managers.put(ServiceType.ENGINEERING_MDM, EngineeringMDMManager.class);
        managers.put(ServiceType.ENGINEERING_ENDPOINT, EngineeringEndpointManager.class);
        managers.put(ServiceType.ENGINEERING_INFO_PROTECTION, EngineeringInfoProtectionManager.class);
        managers.put(ServiceType.ENGINEERING_EMAIL_MESSAGING, EngineeringEmailMessagingManager.class );
        managers.put(ServiceType.CYBER360MXDR, Cyber360MXDRManager.class);
        managers.put(ServiceType.MICROSOFTLICENSES, MicrosoftLicensesManager.class);
    }

    /**
     * Gets an injected manager for a given service type.
     * @param serviceType the type of service used to determine the correct manager to inject.
     * @return the proper injected manager.
     */
    public AbstractServiceManager<? extends Service> getManager(final ServiceType serviceType) {
        if (factoryInstance.isUnsatisfied()) {
            LOGGER.debug("issue with managers injected into factory:  unsatisfied = {}",
                    factoryInstance.isUnsatisfied());
        }

        Class<? extends AbstractServiceManager<? extends Service>> managerClass = managers.get(serviceType);

        for (AbstractServiceManager<? extends Service> manager : factoryInstance) {
            if (managerClass.isAssignableFrom(manager.getClass())) {
                return manager;
            } else {
                LOGGER.debug("manager {} not found", manager.getClass().getName());
            }
        }

        throw new RuntimeException("Cannot create manager of type " + managerClass.getName());
    }
}
