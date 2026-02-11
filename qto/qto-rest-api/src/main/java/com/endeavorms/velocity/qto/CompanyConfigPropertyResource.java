package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigKey;
import com.endeavorms.velocity.qto.config.CompanyConfigPropertyManager;
import com.endeavorms.velocity.qto.config.CompanyConfigurationProperty;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/companyConfigProperties")
@Consumes("application/json")
@Produces("application/json")
public class CompanyConfigPropertyResource extends AbstractResource<CompanyConfigurationProperty> {

    @Override
    protected String getResourcePath() {
        return "/companyConfigProperties";
    }

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> manager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @GET
    @Path("/{key: .+}")
    public Response getValue(@PathParam("key") final CompanyConfigKey key) {
        Company tenantCompany = companyManager.findTenantByName(tenantSubjectManager.getCurrentTenant().getName());
        return Response.ok(wrapResource(manager.findByKey(tenantCompany.getId(), key))).build();
    }

    @GET
    @PreAuthorize("hasAuthority('tenant-admin')")
    @Path("/modifiable/{id: \\d+}")
    public Response getModifiableProperties(@PathParam("id") final Long id) {
        List<CompanyConfigurationProperty> modifiableProperties = manager.getAllModifiableProperties(id);
        for (CompanyConfigurationProperty property : modifiableProperties) {
            if ("password".equalsIgnoreCase(property.getType())) {
                String decryptedValue = manager.decryptString(property.getValue());
                property.setDecryptedValue(decryptedValue);
            }
        }
        return Response.ok(modifiableProperties).build();
    }



    @PUT
    @PreAuthorize("hasAuthority('tenant-admin')")
    @Path("/{id: \\d+}")
    public Response edit(@PathParam("id") final Long id, final CompanyConfigurationProperty config) {

        try {
            if (!id.equals(config.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
        // Encrypt
            if ("password".equalsIgnoreCase(config.getType())) {
                String encryptedValue = manager.encryptString(config.getDecryptedValue());
                config.setValue(encryptedValue);
            }

            CompanyConfigurationProperty updated = manager.edit(config);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
