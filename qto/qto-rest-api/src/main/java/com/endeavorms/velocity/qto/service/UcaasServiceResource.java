package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.ucaas.UcaasService;
import com.endeavorms.velocity.qto.service.ucaas.UcaasServiceManager;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@Path("/ucaasServices")
@Consumes("application/json")
@Produces("application/json")
public class UcaasServiceResource extends AbstractServiceResource<UcaasService> {

    @Inject
    private UcaasServiceManager manager;

    @Override
    protected UcaasServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/ucaasServices";
    }
}
