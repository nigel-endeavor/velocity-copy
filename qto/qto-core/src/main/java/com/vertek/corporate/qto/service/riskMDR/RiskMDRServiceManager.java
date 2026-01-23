package com.vertek.corporate.qto.service.riskMDR;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class RiskMDRServiceManager extends AbstractServiceManager<RiskMDRService> {
    @Inject
    private RiskMDRServiceJpaDao dao;

    @Override
    protected RiskMDRServiceJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final RiskMDRService source, final RiskMDRService target, final List<String> orderType,
                                      final List<String> subOrderType) {
    }
}
