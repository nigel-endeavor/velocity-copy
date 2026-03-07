package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.dia.DiaService;
import com.vertek.corporate.qto.service.dia.DiaServiceManager;

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
}
