package com.endeavorms.velocity.qto.service.ucaas;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@Component
public class UcaasServiceManager extends AbstractServiceManager<UcaasService> {

    /**
     * Persistence tier.
     */
    @Inject
    private UcaasServiceJpaDao dao;

    @Override
    protected UcaasServiceJpaDao getDao() {
        return dao;
    }

    @Override
    protected void macdServiceMapping(UcaasService source, UcaasService target, List<String> orderType, List<String> subOrderType) {
        target.setPublishedTn(source.getPublishedTn());
        target.setTemporaryTn(source.getTemporaryTn());
        target.setNumberOfHandsets(source.getNumberOfHandsets());
    }
}
