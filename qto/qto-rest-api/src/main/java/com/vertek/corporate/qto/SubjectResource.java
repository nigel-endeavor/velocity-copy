package com.vertek.corporate.qto;


import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.TenantSubjectManager;
import com.vertek.corporate.qto.common.TenantView;
import com.vertek.corporate.qto.common.TenantViewManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import com.vertek.corporate.qto.subject.SubjectSearchCriteria;
import com.vertek.corporate.qto.subject.TenantSubject;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/subjects")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(value = {
        Permissions.INVENTORY_READ,
        Permissions.ORDER_READ}, logical = Logical.OR)
public class SubjectResource extends AbstractResource<TenantSubject> {

    @Inject
    private SubjectManager manager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private TenantViewManager tenantViewManager;

    @GET
    public Response getSubjects(@Form final SubjectSearchCriteria criteria) {
        List<Subject> subjects = manager.findAll(criteria);
        //TODO: consider using a paginated result instead of returning the entire list - UI will need to be updated
        return Response.ok(subjects).build();
    }

    /**
     * Gets the subject record for the current user.
     * @return the subject record for the current user.
     */
    @GET
    @Path("/me")
    public Response me() {
        Subject me = manager.findByUsername(SecurityUtils.getLoggedInUser());
        return Response.ok(me).build();
    }

    /**
     * @return true if the current user has tenant-level access, false if not.
     */
    @GET
    @Path("/me/tenantAccess")
    public Response myTenantAccess() {
        List<Long> tenantIds = tenantSubjectManager.getAllowedTenantIds();
        return Response.ok(!tenantIds.isEmpty()).build();
    }

    @POST
    @Path("/setTenant/{tenant: .+}")
    @RequiresPermissions(Permissions.CHANGE_TENANT)
    public Response setTenant(@PathParam("tenant") final String tenant) {
        tenantSubjectManager.updateSelectedTenant(tenant);
        return Response.ok().build();
    }

    //This doesn't really belong in this resource, but it only exists to support the above setTenant feature.
    @GET
    @Path("/tenants")
    @RequiresPermissions(Permissions.CHANGE_TENANT)
    public Response getTenants() {
        List<String> tenants = tenantViewManager.getAllTenants().stream().map(TenantView::getName).collect(Collectors.toList());
        return Response.ok(tenants).build();
    }
}
