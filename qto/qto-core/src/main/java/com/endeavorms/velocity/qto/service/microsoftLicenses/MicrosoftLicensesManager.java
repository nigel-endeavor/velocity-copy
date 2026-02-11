package com.endeavorms.velocity.qto.service.microsoftLicenses;

import com.endeavorms.velocity.qto.service.AbstractServiceManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class MicrosoftLicensesManager extends AbstractServiceManager<MicrosoftLicensesService> {
    @Inject
    private MicrosoftLicensesJpaDao dao;

    @Override
    protected MicrosoftLicensesJpaDao getDao() { return dao; }

    @Override
    protected void macdServiceMapping(final MicrosoftLicensesService source, final MicrosoftLicensesService target,
                                      final List<String> orderType, final List<String> subOrderType) {
    }
}
