package com.vertek.corporate.qto;

import com.vertek.corporate.qto.activation.requirement.RequirementTemplate;
import com.vertek.corporate.qto.activation.requirement.RequirementTemplateManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 3/3/2023
 */
@Path("/requirementTemplates")
@Consumes("application/json")
@Produces("application/json")
public class RequirementTemplateResource extends AbstractResource {

    /** Business methods for RequirementTemplates. */
    @Inject
    private RequirementTemplateManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getTemplates(@QueryParam("companyId") final Long companyId, @QueryParam("showInactive") final boolean showInactive) {
        List<RequirementTemplate> templates = manager.findByCompanyId(companyId, showInactive);
        return Response.ok(templates).build();
    }

    @GET
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response retrieve(@PathParam("id") final Long id) {
        RequirementTemplate retrieved = manager.retrieve(id);
        return Response.ok(retrieved).build();
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final RequirementTemplate template) {
        try {
            RequirementTemplate created = manager.create(template);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final RequirementTemplate template) {
        try {
            if (!id.equals(template.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            RequirementTemplate updated = manager.edit(template);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
