package com.endeavorms.velocity.qto.service.riskMDR;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
