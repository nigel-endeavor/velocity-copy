package com.endeavorms.velocity.qto.service.engineeringInfoProtection;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class EngineeringInfoProtectionManager extends AbstractServiceManager<EngineeringInfoProtectionService> {

    @Inject
    private EngineeringInfoProtectionJpaDao dao;

    @Override
    protected EngineeringInfoProtectionJpaDao getDao() { return dao;}

    @Override
    protected void macdServiceMapping(final EngineeringInfoProtectionService source, final EngineeringInfoProtectionService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
