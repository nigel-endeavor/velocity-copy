package com.vertek.corporate.qto.brokerage;

import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author fcurran
 * @since 6/19/2024
 */
@Stateless
public class ServiceBrokerageManager extends StandardManager<ServiceBrokerage> {

    @Inject
    private ServiceBrokerageJpaDao dao;

    @Inject
    private ServiceManager serviceManager;

    @Override
    protected ServiceBrokerageJpaDao getDao() {
        return dao;
    }

    @Override
    public ServiceBrokerage edit(final ServiceBrokerage serviceBrokerage) {
        applyFieldLogic(serviceBrokerage);
        return super.edit(serviceBrokerage);
    }

    /**
     * Applies common field logic to the incoming service brokerage info.
     * @param serviceBrokerage the entity to apply logic to.
     */
    private void applyFieldLogic(final ServiceBrokerage serviceBrokerage) {
        Service service = serviceManager.retrieve(serviceBrokerage.getServiceId());
        serviceBrokerage.setService(service);
        serviceBrokerage.setMasterCustomerId(service.getMasterCustomerId());
        serviceBrokerage.setTenantId(service.getTenantId());
    }

    @Override
    public ServiceBrokerage create(final ServiceBrokerage serviceBrokerage) {
        applyFieldLogic(serviceBrokerage);
        return super.create(serviceBrokerage);
    }

    /**
     * Retrieves the ServiceBrokerage by serviceId.
     * @param serviceId the serviceId of the desired entity.
     * @return the matching entity if it exists.
     */
    public ServiceBrokerage findByServiceId(final Long serviceId) {
        return dao.findByServiceId(serviceId);
    }

}
