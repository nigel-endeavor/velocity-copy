package com.vertek.corporate.qto.service.engineeringIAM;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class EngineeringIAMServiceManager extends AbstractServiceManager<EngineeringIAMService> {
    @Inject
    private EngineeringIAMServiceJpaDao dao;

    @Override
    protected EngineeringIAMServiceJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final EngineeringIAMService source, final EngineeringIAMService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
