package com.endeavorms.velocity.qto.service.engineeringMDM;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
