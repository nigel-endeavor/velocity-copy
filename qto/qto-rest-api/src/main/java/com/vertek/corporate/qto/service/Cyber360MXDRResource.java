package com.vertek.corporate.qto.service;


import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.cyber360MXDR.Cyber360MXDRManager;
import com.vertek.corporate.qto.service.cyber360MXDR.Cyber360MXDRService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/cyber360MXDRService")
@Consumes("application/json")
@Produces("application/json")
public class Cyber360MXDRResource extends AbstractServiceResource<Cyber360MXDRService> {

    @Inject
    private Cyber360MXDRManager manager;

    @Override
    protected Cyber360MXDRManager getManager() { return manager; }
}
