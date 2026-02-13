package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.address.AddressView;
import com.endeavorms.velocity.qto.address.AddressViewManager;
import com.endeavorms.velocity.qto.address.AddressViewSearchCriteria;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 2/6/2023
 */
@Path("/addresses")
@Consumes("application/json")
@Produces("application/json")
public class AddressResource extends AbstractResource<AddressView> {

    @Override
    protected String getResourcePath() {
        return "/addresses";
    }

    /** Business methods for AddressViews. */
    @Inject
    private AddressViewManager manager;

    /**
     * Retrieves all addresses matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching addresses.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getAddresses(@Form final AddressViewSearchCriteria criteria) {
        PaginatedResult<AddressView> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(AddressResource.class)));
    }

}
