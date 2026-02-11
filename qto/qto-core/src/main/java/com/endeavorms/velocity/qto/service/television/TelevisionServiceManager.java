package com.endeavorms.velocity.qto.service.television;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class TelevisionServiceManager extends AbstractServiceManager<TelevisionService> {
    /**
     * Persistence tier.
     */
    @Inject
    private TelevisionServiceJpaDao dao;

    @Override
    protected TelevisionServiceJpaDao getDao() { return dao;}


    @Override
    protected void macdServiceMapping(final TelevisionService source, final TelevisionService target,
                                      final List<String> orderType, final List<String> subOrderType) {
        target.setDvr(source.getDvr());
        target.setPlan(source.getPlan());
        target.setReceiver(source.getReceiver());
        target.setReceiverMac(source.getReceiverMac());
        target.setDvrMac(source.getDvrMac());
        target.setDvrIncluded(source.getDvrIncluded());
    }
}
