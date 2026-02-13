package com.endeavorms.velocity.qto;


import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.cyberView.ServiceCyberView;
import com.endeavorms.velocity.qto.service.cyberView.ServiceCyberViewManager;
import com.endeavorms.velocity.qto.service.cyberView.ServiceCyberViewSearchCriteria;
import com.endeavorms.velocity.qto.service.multiedit.ServiceMultiEditQueueHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@Path("/serviceCyberViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})

public class ServiceCyberViewResource  extends AbstractResource<ServiceCyberView> {

    @Override
    protected String getResourcePath() {
        return "/serviceCyberViews";
    }

/** Logging Facade.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceCyberViewResource.class);

    @Inject
    private ServiceCyberViewManager manager;

    @Inject
    private ServiceManager serviceManager;

    /** MultiEdit Message Queue Handler. */
    @Inject
    private ServiceMultiEditQueueHandler multiEditQueueHandler;

    @GET
    @PreAuthorize("hasAuthority('order:read')")
    public Response getServiceCyberViews(@Form final ServiceCyberViewSearchCriteria criteria) {
        ServiceCyberViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceCyberView> result = manager.findBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, crit, getLocation(ServiceViewResource.class)));
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAuthority('order:write')")
    public Response edit(@PathParam("id") final Long id, final ServiceCyberView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceCyberView updated = manager.edit(serviceView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/link")
    public Response getServiceViewsForLink(@Form final ServiceCyberViewSearchCriteria criteria) {
        PaginatedResult<ServiceCyberView> result = manager.getServiceViewsForLink(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceViewResource.class)));
    }

    @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/bundle")
    public Response getServiceViewsForBundle(@Form final ServiceCyberViewSearchCriteria criteria) {
        PaginatedResult<ServiceCyberView> result = manager.getServiceViewsForBundle(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceViewResource.class)));
    }

    @PUT
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/link")
    public Response link(@QueryParam("incomingServiceId") final Long incomingServiceId,
                         @QueryParam("selectedItems") final String selectedItems,
                         @QueryParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItems, linkType);
        return Response.ok().build();
    }

    /**
     * Given a collection of Cyber IDs and fields to process, loops through the servics and makes the appropriate
     * changes/additions.
     * @param multiEditRequest the request.
     * @return a response indicating any failures.
     */
    @POST
    @Path("/multiEdit")
    @PreAuthorize("hasAuthority('order:write')")
    public Response multiEdit(final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
