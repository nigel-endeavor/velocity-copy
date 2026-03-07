package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.customfield.field.CustomField;
import com.vertek.corporate.qto.customfield.field.CustomFieldManager;
import com.vertek.corporate.qto.customfield.field.CustomFieldTabValue;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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

    @Inject
    private CustomFieldManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
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
