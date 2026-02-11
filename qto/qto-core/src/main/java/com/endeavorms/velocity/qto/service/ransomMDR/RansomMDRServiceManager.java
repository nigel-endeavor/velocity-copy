package com.endeavorms.velocity.qto.service.ransomMDR;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic tier for ransomMDR service.
 * @author bmccormick
 * @since 10/23/24
 */

@Component
public class RansomMDRServiceManager extends AbstractServiceManager<RansomMDRService> {
    @Inject
    private RansomMDRServiceJpaDao dao;

    @Override
    protected RansomMDRServiceJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final RansomMDRService source, final RansomMDRService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
