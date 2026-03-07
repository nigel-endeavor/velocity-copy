package com.vertek.corporate.qto;

import com.vertek.corporate.qto.address.AddressView;
import com.vertek.corporate.qto.address.AddressViewManager;
import com.vertek.corporate.qto.address.AddressViewSearchCriteria;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    /** Business methods for AddressViews. */
    @Inject
    private AddressViewManager manager;

    /**
     * Retrieves all addresses matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching addresses.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getAddresses(@Form final AddressViewSearchCriteria criteria) {
        PaginatedResult<AddressView> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(AddressResource.class));
    }

}
