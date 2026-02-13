package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySet;
import com.endeavorms.velocity.qto.milestone.MilestoneDisplaySetManager;
import org.springframework.security.access.prepost.PreAuthorize;

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
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getDisplaySet(@QueryParam("displayGroup") final String displayGroup) {
        MilestoneDisplaySet displaySet = manager.getByDisplayGroup(displayGroup);
        return Response.ok(displaySet).build();
    }
}
