package com.vertek.corporate.qto.contact.location;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Endpoints related to location contacts.
 * @author fcurran
 * @since 3/31/2023
 */
@Path("/locationContacts")
@Consumes("application/json")
@Produces("application/json")
public class LocationContactResource extends AbstractResource<LocationContact> {

    /** Business methods for location related contacts. */
    @Inject
    private LocationContactManager manager;

    /**
     * Returns location contacts that match the provided search criteria. A location ID is required.
     * @param criteria what to match location contacts on.
     * @return the matching location contacts, if any.
     */
    @GET
    public Response getLocationContacts(@Form final LocationContactSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getLocationId(), "A locationId is required");
        PaginatedResult<LocationContact> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationContactResource.class));
    }

    /**
     * Attempts to persist the provided location contact.
     * @param locationContact the contact to persist.
     * @return the persisted contact.
     */
    @POST
    public Response create(final LocationContact locationContact) {
        try {
            PreconditionsUtil.checkArgument(locationContact.getLocationId(), "A locationId is required");
            LocationContact createLocationContact = manager.create(locationContact);
            return Response.ok(createLocationContact).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Attempts to update an existing location contact.
     * @param id the ID of the contact to update.
     * @param locationContact the contact with its updates.
     * @return the updated contact.
     */
    @PUT
    @Path("/{id: \\d+}")
    public Response edit(@PathParam("id") final Long id, final LocationContact locationContact) {
        try {
            if (!id.equals(locationContact.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            PreconditionsUtil.checkArgument(locationContact.getLocationId(), "A locationId is required");
            LocationContact updatedLocationContact = manager.edit(locationContact);
            return Response.ok(updatedLocationContact).build();
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
    public Response remove(@PathParam("id") final Long id) {
        manager.remove(id);
        return Response.noContent().build();
    }
}
