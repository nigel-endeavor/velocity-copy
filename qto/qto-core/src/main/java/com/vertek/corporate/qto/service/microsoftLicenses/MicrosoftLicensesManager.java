package com.vertek.corporate.qto.service.microsoftLicenses;

import com.vertek.corporate.qto.service.AbstractServiceManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
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
