package com.vertek.corporate.qto.service.engineeringEndpoint;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
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
