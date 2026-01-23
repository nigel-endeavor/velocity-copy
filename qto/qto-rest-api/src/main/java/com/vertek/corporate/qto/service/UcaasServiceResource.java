package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.service.ucaas.UcaasService;
import com.vertek.corporate.qto.service.ucaas.UcaasServiceManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
}
