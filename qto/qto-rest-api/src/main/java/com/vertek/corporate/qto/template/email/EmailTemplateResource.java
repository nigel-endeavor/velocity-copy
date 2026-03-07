package com.vertek.corporate.qto.template.email;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.contact.location.LocationContact;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/emailTemplates")
@Consumes("application/json")
@Produces("application/json")
public class EmailTemplateResource extends AbstractResource<EmailTemplate> {
    /** Business logic layer for Email Templates. */
    @Inject
    private EmailTemplateManager manager;

    /**
     * API endpoint that returns all email templates for a given template type and either entity ID or company ID.
     * @param templateType the type of email templates to return.
     * @param criteria the search criteria to use.
     * @return a response containing the matching email templates.
     */
    @GET
    @Path("/{templateType}")
    public Response getEmailTemplates(@PathParam("templateType") final String templateType,
                                      @Form final EmailTemplateSearchCriteria criteria) {
        if (criteria.getEntityId() == null && criteria.getCompanyId() == null) {
            throw new IllegalArgumentException("Either a company ID or an entity ID is required");
        }
        PaginatedResult<EmailTemplate> result = manager.getEmailTemplatesByType(templateType, criteria);
        return getCollectionResource(result, criteria, getLocation(EmailTemplateResource.class));
    }

    /**
     * Attempts to persist the provided email template.
     * @param emailTemplate the email template to persist.
     * @return the persisted contact.
     */
    @POST
    @RequiresPermissions(Permissions.ORDER_WRITE_TERMINAL)
    public Response create(final EmailTemplate emailTemplate) {
        try {
            PreconditionsUtil.checkArgument(emailTemplate.getTemplateType(), "An email template type is required");
            PreconditionsUtil.checkArgument(emailTemplate.getCompanyId(), "A related company ID is required");
            EmailTemplate createdEmailTemplate = manager.create(emailTemplate);
            return Response.ok(createdEmailTemplate).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Attempts to update an existing email template.
     * @param id the ID of the email template to update.
     * @param emailTemplate the email template with its updates.
     * @return the updated email template.
     */
    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(Permissions.ORDER_WRITE_TERMINAL)
    public Response edit(@PathParam("id") final Long id, final EmailTemplate emailTemplate) {
        try {
            if (!id.equals(emailTemplate.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            PreconditionsUtil.checkArgument(emailTemplate.getTemplateType(), "An email template type is required");
            PreconditionsUtil.checkArgument(emailTemplate.getCompanyId(), "A related company ID is required");
            EmailTemplate updatedEmailTemplate = manager.edit(emailTemplate);
            return Response.ok(updatedEmailTemplate).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Deletes a resource.
     * @param id resource identifier.
     * @return a Response.
     */
    @DELETE
    @Path("/{id : \\d+}")
    @RequiresPermissions(Permissions.ORDER_WRITE_TERMINAL)
    public Response remove(@PathParam("id") final Long id) {
        manager.remove(id);
        return Response.noContent().build();
    }
}
