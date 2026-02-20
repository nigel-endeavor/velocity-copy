package com.endeavorms.velocity.qto.service;

import java.util.EnumMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.endeavorms.velocity.qto.service._4g5g.GServiceManager;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectServiceManager;
import com.endeavorms.velocity.qto.service.cyber360MXDR.Cyber360MXDRManager;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;
import com.endeavorms.velocity.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingManager;
import com.endeavorms.velocity.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.endeavorms.velocity.qto.service.engineeringIAM.EngineeringIAMServiceManager;
import com.endeavorms.velocity.qto.service.engineeringInfoProtection.EngineeringInfoProtectionManager;
import com.endeavorms.velocity.qto.service.engineeringMDM.EngineeringMDMManager;
import com.endeavorms.velocity.qto.service.ethernet.EthernetServiceManager;
import com.endeavorms.velocity.qto.service.microsoftLicenses.MicrosoftLicensesManager;
import com.endeavorms.velocity.qto.service.mpls.MplsServiceManager;
import com.endeavorms.velocity.qto.service.ransomMDR.RansomMDRServiceManager;
import com.endeavorms.velocity.qto.service.riskMDR.RiskMDRServiceManager;
import com.endeavorms.velocity.qto.service.television.TelevisionServiceManager;
import com.endeavorms.velocity.qto.service.threatMDR.ThreatMDRServiceManager;
import com.endeavorms.velocity.qto.service.ucaas.UcaasServiceManager;
/**
 * Simple factory for getting the correctly injected manager for a given service type.
 */
@Component
public class ServiceManagerFactory {
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManagerFactory.class);

    /** Factory instance. */
    @Autowired
    private ObjectProvider<AbstractServiceManager<? extends Service>> factoryInstance;

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
    public AbstractServiceManager<? extends Service> getManager(ServiceType serviceType) {
        Class<? extends AbstractServiceManager<? extends Service>> managerClass =
                managers.get(serviceType);

        for (AbstractServiceManager<? extends Service> manager : factoryInstance) {
            if (managerClass.isAssignableFrom(manager.getClass())) {
                return manager;
            }
        }

        throw new RuntimeException("Cannot create manager of type " + managerClass.getName());
    }
}
