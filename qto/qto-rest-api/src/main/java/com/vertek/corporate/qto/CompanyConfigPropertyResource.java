package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.config.CompanyConfigKey;
import com.vertek.corporate.qto.config.CompanyConfigPropertyManager;
import com.vertek.corporate.qto.config.CompanyConfigurationProperty;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("/companyConfigProperties")
@Consumes("application/json")
@Produces("application/json")
public class CompanyConfigPropertyResource extends AbstractResource<CompanyConfigurationProperty> {

    @Inject
    private CompanyConfigPropertyManager<CompanyConfigKey> manager;

    @Inject
    private CompanyManager companyManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @GET
    @Path("/{key: .+}")
    public Response getValue(@PathParam("key") final CompanyConfigKey key) {
        com.vertek.corporate.qto.common.Tenant currentTenant = tenantSubjectManager.getCurrentTenant();
        if (currentTenant == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No active tenant found for the current user.").build();
        }
        Company tenantCompany = companyManager.findTenantByName(currentTenant.getName());
        return Response.ok(wrapResource(manager.findByKey(tenantCompany.getId(), key))).build();
    }

    @GET
    @RequiresPermissions(Permissions.TENANT_ADMIN)
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
    @RequiresPermissions(Permissions.TENANT_ADMIN)
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
