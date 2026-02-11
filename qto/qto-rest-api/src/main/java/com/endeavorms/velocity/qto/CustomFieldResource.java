package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.customfield.field.CustomField;
import com.endeavorms.velocity.qto.customfield.field.CustomFieldManager;
import com.endeavorms.velocity.qto.customfield.field.CustomFieldTabValue;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customfields")
@Consumes("application/json")
@Produces("application/json")
public class CustomFieldResource extends AbstractResource<CustomField> {

    @Override
    protected String getResourcePath() {
        return "/customfields";
    }

    @Inject
    private CustomFieldManager manager;

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getCustomFields(@QueryParam("tab") final CustomFieldTabValue tab) {
        try {
            PreconditionsUtil.checkArgument(tab, "tab is required");
            List<CustomField> customFields = manager.findActiveByTab(tab);
            return Response.ok().entity(customFields).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
