package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.microsoftLicenses.MicrosoftLicensesManager;
import com.vertek.corporate.qto.service.microsoftLicenses.MicrosoftLicensesService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/microsoftLicenses")
@Consumes("application/json")
@Produces("application/json")
public class MicrosoftLicensesResource extends AbstractServiceResource<MicrosoftLicensesService> {

    @Inject
    private MicrosoftLicensesManager manager;

    @Override
    protected MicrosoftLicensesManager getManager() { return manager; }
}
