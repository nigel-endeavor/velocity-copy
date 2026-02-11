package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.dia.DiaService;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Path("/diaServices")
@Consumes("application/json")
@Produces("application/json")
public class DiaServiceResource extends AbstractServiceResource<DiaService> {

    @Inject
    private DiaServiceManager manager;

    @Override
    protected DiaServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/diaServices";
    }
}
