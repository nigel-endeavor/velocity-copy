package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.service._4g5g.GServiceManager;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectServiceManager;
import com.endeavorms.velocity.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingManager;
import com.endeavorms.velocity.qto.service.engineeringIAM.EngineeringIAMServiceManager;
import com.endeavorms.velocity.qto.service.engineeringMDM.EngineeringMDMManager;
import com.endeavorms.velocity.qto.service.cyber360MXDR.Cyber360MXDRManager;
import com.endeavorms.velocity.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.endeavorms.velocity.qto.service.engineeringInfoProtection.EngineeringInfoProtectionManager;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;
import com.endeavorms.velocity.qto.service.ethernet.EthernetServiceManager;
import com.endeavorms.velocity.qto.service.microsoftLicenses.MicrosoftLicensesManager;
import com.endeavorms.velocity.qto.service.mpls.MplsServiceManager;
import com.endeavorms.velocity.qto.service.ransomMDR.RansomMDRServiceManager;
import com.endeavorms.velocity.qto.service.riskMDR.RiskMDRServiceManager;
import com.endeavorms.velocity.qto.service.television.TelevisionServiceManager;
import com.endeavorms.velocity.qto.service.threatMDR.ThreatMDRServiceManager;
import com.endeavorms.velocity.qto.service.ucaas.UcaasServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

/**
 * Spring Boot factory for getting the correctly injected manager for a given service type.
 */
@Component
public class ServiceManagerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManagerFactory.class);

    private final ApplicationContext applicationContext;

    private static final EnumMap<ServiceType, Class<? extends AbstractServiceManager<? extends Service>>> managers
            = new EnumMap<>(ServiceType.class);

    public ServiceManagerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
        managers.put(ServiceType.ENGINEERING_EMAIL_MESSAGING, EngineeringEmailMessagingManager.class);
        managers.put(ServiceType.CYBER360MXDR, Cyber360MXDRManager.class);
        managers.put(ServiceType.MICROSOFTLICENSES, MicrosoftLicensesManager.class);
    }

    /**
     * Gets an injected manager for a given service type.
     * @param serviceType the type of service used to determine the correct manager to inject.
     * @return the proper injected manager.
     */
    public AbstractServiceManager<? extends Service> getManager(final ServiceType serviceType) {
        Class<? extends AbstractServiceManager<? extends Service>> managerClass = managers.get(serviceType);

        if (managerClass == null) {
            throw new IllegalArgumentException("No manager registered for service type: " + serviceType);
        }

        try {
            return applicationContext.getBean(managerClass);
        } catch (Exception e) {
            LOGGER.error("Cannot create manager of type {}", managerClass.getName(), e);
            throw new RuntimeException("Cannot create manager of type " + managerClass.getName(), e);
        }
    }
}
