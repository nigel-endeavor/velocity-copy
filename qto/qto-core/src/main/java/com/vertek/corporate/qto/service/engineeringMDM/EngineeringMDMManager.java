package com.vertek.corporate.qto.service.engineeringMDM;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class EngineeringMDMManager extends AbstractServiceManager<EngineeringMDMService> {

    @Inject
    private EngineeringMDMJpaDao dao;

    @Override
    protected EngineeringMDMJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final EngineeringMDMService source, final EngineeringMDMService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
