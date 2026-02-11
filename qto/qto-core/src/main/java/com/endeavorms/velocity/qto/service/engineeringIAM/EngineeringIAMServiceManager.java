package com.endeavorms.velocity.qto.service.engineeringIAM;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class EngineeringIAMServiceManager extends AbstractServiceManager<EngineeringIAMService> {
    @Inject
    private EngineeringIAMServiceJpaDao dao;

    @Override
    protected EngineeringIAMServiceJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final EngineeringIAMService source, final EngineeringIAMService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
