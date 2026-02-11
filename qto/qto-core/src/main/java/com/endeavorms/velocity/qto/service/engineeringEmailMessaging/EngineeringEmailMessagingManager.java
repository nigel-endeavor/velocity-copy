package com.endeavorms.velocity.qto.service.engineeringEmailMessaging;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
