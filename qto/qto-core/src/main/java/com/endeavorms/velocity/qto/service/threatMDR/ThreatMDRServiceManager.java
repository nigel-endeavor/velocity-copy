package com.endeavorms.velocity.qto.service.threatMDR;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic tier for threatMDR service.
 * @author bmccormick
 * @since 9/4/2024
 */
@Component
public class ThreatMDRServiceManager extends AbstractServiceManager<ThreatMDRService> {

    @Inject
    private ThreatMDRServiceJpaDao dao;

    @Override
    protected ThreatMDRServiceJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final ThreatMDRService source, final ThreatMDRService target, final List<String> orderType, final List<String> subOrderType) {
    }
}
