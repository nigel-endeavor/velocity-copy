package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.microsoftLicenses.MicrosoftLicensesManager;
import com.vertek.corporate.qto.service.microsoftLicenses.MicrosoftLicensesService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/microsoftLicenses")
@Consumes("application/json")
@Produces("application/json")
public class MicrosoftLicensesResource extends AbstractServiceResource<MicrosoftLicensesService> {

    @Inject
    private MicrosoftLicensesManager manager;

    @Override
    protected MicrosoftLicensesManager getManager() { return manager; }
}
