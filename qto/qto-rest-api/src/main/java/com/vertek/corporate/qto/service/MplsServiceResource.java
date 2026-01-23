package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.mpls.MplsService;
import com.vertek.corporate.qto.service.mpls.MplsServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author rcasey
 * @since 5/17/2024
 */
@Path("/mplsServices")
@Consumes("application/json")
@Produces("application/json")
public class MplsServiceResource extends AbstractServiceResource<MplsService> {

        @Inject
        private MplsServiceManager manager;

        @Override
        protected MplsServiceManager getManager() {
            return manager;
        }

}
