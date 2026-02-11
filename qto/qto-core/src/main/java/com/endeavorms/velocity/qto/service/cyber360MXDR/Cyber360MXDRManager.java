package com.endeavorms.velocity.qto.service.cyber360MXDR;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class Cyber360MXDRManager extends AbstractServiceManager<Cyber360MXDRService> {

    @Inject
    private Cyber360MXDRJpaDao dao;

    @Override
    protected Cyber360MXDRJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final Cyber360MXDRService source, final Cyber360MXDRService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
