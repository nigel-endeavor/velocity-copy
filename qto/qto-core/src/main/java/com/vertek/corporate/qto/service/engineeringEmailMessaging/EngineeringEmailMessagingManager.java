package com.vertek.corporate.qto.service.engineeringEmailMessaging;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class EngineeringEmailMessagingManager extends AbstractServiceManager<EngineeringEmailMessagingService> {

    @Inject
    private EngineeringEmailMessagingJpaDao dao;

    @Override
    protected EngineeringEmailMessagingJpaDao getDao() { return dao; }

    @Override
    protected void macdServiceMapping(final EngineeringEmailMessagingService source, final EngineeringEmailMessagingService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }

}
