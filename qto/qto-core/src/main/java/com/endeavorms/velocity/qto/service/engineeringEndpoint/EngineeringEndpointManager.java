package com.endeavorms.velocity.qto.service.engineeringEndpoint;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class EngineeringEndpointManager extends AbstractServiceManager<EngineeringEndpointService> {

    @Inject
    private EngineeringEndpointJpaDao dao;

    @Override
    protected EngineeringEndpointJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final EngineeringEndpointService source, final EngineeringEndpointService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
