package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.lookup.LookupValue;
import com.endeavorms.velocity.qto.common.lookup.LookupValueManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Component
@Path("/lookupValues")
@Consumes("application/json")
@Produces("application/json")
public class LookupValueResource extends AbstractLookupValueResource<LookupValue> {

    /** Business logic for manage lookupvalue. */
    @Inject
    private LookupValueManager manager;

    @Override
    protected LookupValueManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/lookupValues";
    }
}
