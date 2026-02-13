package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.microsoftLicenses.MicrosoftLicensesManager;
import com.endeavorms.velocity.qto.service.microsoftLicenses.MicrosoftLicensesService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/microsoftLicenses")
public class MicrosoftLicensesResource extends AbstractServiceResource<MicrosoftLicensesService> {

    @Inject
    private MicrosoftLicensesManager manager;

    @Override
    protected MicrosoftLicensesManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/microsoftLicenses";
    }
}
