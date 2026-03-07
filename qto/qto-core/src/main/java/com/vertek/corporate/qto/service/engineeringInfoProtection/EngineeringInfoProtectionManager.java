package com.vertek.corporate.qto.service.engineeringInfoProtection;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class EngineeringInfoProtectionManager extends AbstractServiceManager<EngineeringInfoProtectionService> {

    @Inject
    private EngineeringInfoProtectionJpaDao dao;

    @Override
    protected EngineeringInfoProtectionJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final EngineeringInfoProtectionService source, final EngineeringInfoProtectionService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
