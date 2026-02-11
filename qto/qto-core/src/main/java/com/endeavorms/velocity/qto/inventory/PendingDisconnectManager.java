package com.endeavorms.velocity.qto.inventory;

import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertiesDto;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.service.AbstractServiceManager;
import com.endeavorms.velocity.qto.service.OrderType;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.ServiceManagerFactory;
import com.endeavorms.velocity.qto.service.ServiceType;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Business logic for pending disconnects.
 */
@Component
public class PendingDisconnectManager extends StandardManager<PendingDisconnect> {

    @Inject
    private PendingDisconnectJpaDao dao;

    @Override
    public PendingDisconnectJpaDao getDao() {
        return dao;
    }

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> configPropertyManager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private ServiceManager serviceManager;

    /**
     * Manager for getting the properly injected manager for a given service type.
     */
    @Inject
    private ServiceManagerFactory serviceManagerFactory;

    /**
     * Retrieves all pending disconnects matching the given criteria.
     *
     * @param parentId ID.
     * @return PendingDisconnect.
     */
    public PendingDisconnect findByParent(final Long parentId) {
        return dao.findByParent(parentId);
    }

    /**
     * Retrieves all pending disconnects matching the given criteria.
     *
     * @param newId ID.
     * @return PendingDisconnect.
     */
    public PendingDisconnect findByNew(final Long newId) {
        return dao.findByNew(newId);
    }

    /**
     * Retrieves all pending disconnects matching the given criteria.
     *
     * @param childId ID.
     * @return matching pending disconnects.
     */
    public PendingDisconnect findByChild(final Long childId) {
        return dao.findByChild(childId);
    }

    public <X extends Service> void processPendingDisconnects() {
        List<PendingDisconnect> pendingDisconnects = dao.findOpen();
          for (PendingDisconnect pendingDisconnect : pendingDisconnects) {
            X parent = (X) pendingDisconnect.getParentService();
            Service existingDisconnect = serviceManager.findDisconnectByParentId(parent.getId());
            if (existingDisconnect != null) {
                pendingDisconnect.setChildService(existingDisconnect);
                edit(pendingDisconnect);
            } else {
                CompanyConfigPropertiesDto config = companyManager.getConfigDto(parent.getTenantId());
                if ((config.getDisconnectDelay() != null && config.getDisconnectDelay() > 0)
                || (config.getDisconnectDelay() != null && config.getDisconnectDelay() == 0 && pendingDisconnect.getChildService() == null)) {
                    AbstractServiceManager<X> svcManager = (AbstractServiceManager<X>) serviceManagerFactory
                            .getManager(ServiceType.fromServiceName(parent.getType()));

                    Integer delay = config.getDisconnectDelay();
                    Date completeDate = pendingDisconnect.getNewServiceCompleteDate();
                    LocalDate completeLocal = completeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate today = LocalDate.now();
                    long diff = ChronoUnit.DAYS.between(completeLocal, today);
                    if (diff >= delay) {
                        // create the disconnect and update the pending disconnect record.
                        List<String> orderTypes = new ArrayList<>();
                        orderTypes.add(OrderType.DISCONNECT.getOrderType());
                        List<String> disconnectReasons = new ArrayList<>();
                        disconnectReasons.add(pendingDisconnect.getDisconnectReason());
                        List<String> subOrderTypes = new ArrayList<>();
                        subOrderTypes.add(pendingDisconnect.getDisconnectReason());
                        X disconnect = svcManager.createMacd(parent, orderTypes,
                                subOrderTypes, disconnectReasons, null, null, null);
                        pendingDisconnect.setChildService(disconnect);
                        edit(pendingDisconnect);
                    }
                }
            }
        }
    }

}
