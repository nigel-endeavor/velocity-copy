package com.vertek.corporate.qto.service.ransomMDR;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Business logic tier for ransomMDR service.
 * @author bmccormick
 * @since 10/23/24
 */

@Stateless
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
