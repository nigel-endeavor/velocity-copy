package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySet;
import com.vertek.corporate.qto.milestone.MilestoneDisplaySetManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/milestoneDisplaySets")
@Consumes("application/json")
@Produces("application/json")
public class MilestoneDisplaySetResource {

    @Inject
    private MilestoneDisplaySetManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getDisplaySet(@QueryParam("displayGroup") final String displayGroup) {
        MilestoneDisplaySet displaySet = manager.getByDisplayGroup(displayGroup);
        return Response.ok(displaySet).build();
    }
}
